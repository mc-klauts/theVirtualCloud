package org.thevirtualcloud.api.network.protocol

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.util.*

/**
 * java doc created on 02.05.2022
 * @author Alexa
 */

@Suppress("MemberVisibilityCanBePrivate")
class NetworkBuffer(private val byteBuf: ByteBuf = Unpooled.buffer()) {

    fun writeString(s: String) {
        val bArr = s.toByteArray()
        byteBuf.writeInt(bArr.size)
        byteBuf.writeBytes(bArr)
    }

    fun writeStringArray(arr: Array<String>) {
        byteBuf.writeInt(arr.size)
        for (s in arr) {
            writeString(s)
        }
    }

    fun readStringArray(): Array<String?> {
        val length = byteBuf.readInt()
        val array = arrayOfNulls<String>(length)
        for (i in 0 until length) {
            array[i] = readString()
        }
        return array
    }

    fun getRemoteBuffer(): ByteBuf {
        return this.byteBuf
    }

    fun readString(): String {
        val bArr = ByteArray(byteBuf.readInt())
        byteBuf.readBytes(bArr)
        return String(bArr)
    }

    fun<T> readEnum(enumClass: Class<T>): T {
        return enumClass.enumConstants[this.byteBuf.readInt()]
    }

    fun writeEnum(source: Enum<*>) {
        this.byteBuf.writeInt(source.ordinal)
    }

    fun readUUID(): UUID {
        return UUID(this.byteBuf.readLong(), this.byteBuf.readLong())
    }

    fun writeUUID(uuid: UUID) {
        this.byteBuf.writeLong(uuid.mostSignificantBits)
        this.byteBuf.writeLong(uuid.leastSignificantBits)
    }

}