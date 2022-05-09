/*
 * MIT License
 *
 * Copyright (c) 2022 REPLACE_WITH_NAME
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eu.thevirtualcloud.api.network.protocol

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.exceptions.network.NotRegisteredRegistryException
import io.netty.buffer.ByteBuf
import java.util.*

/**
 *
 * this doc was created on 06.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

interface Packet<T> {

    fun toProtocolBuffer(): NetworkBuffer {
        if (CloudAPI.instance.getCloudPacketRegistry().contains(this.javaClass)) {
            val buffer = NetworkBuffer()
            val obj = this.javaClass
            val i = this.javaClass.getConstructor().newInstance()
            buffer.getRemoteBuffer().writeInt(CloudAPI.instance.getCloudPacketRegistry().packetTypeID(this.javaClass))
            for (field in this::class.java.declaredFields) {
                if (!field.isAccessible) field.isAccessible = true
                if (!field.isAnnotationPresent(ExceptEntryField::class.java)) {
                    when (field.type) {
                        String::class.java -> {
                            buffer.writeString(field.get(obj) as String)
                            println(field.get(i) as String)
                            println("ref")
                        }
                        UUID::class.java -> {
                            buffer.writeUUID(field.get(obj) as UUID)
                        }
                        Array<String>::class.java -> {
                            @Suppress("UNCHECKED_CAST")
                            buffer.writeStringArray(field.get(obj) as Array<String>)
                        }
                        Boolean::class.java -> {
                            buffer.getRemoteBuffer().writeBoolean(field.get(obj) as Boolean)
                        }
                        Double::class.java -> {
                            buffer.getRemoteBuffer().writeDouble(field.get(obj) as Double)
                        }
                        Float::class.java -> {
                            buffer.getRemoteBuffer().writeFloat(field.get(obj) as Float)
                        }
                        Int::class.java -> {
                            buffer.getRemoteBuffer().writeInt(field.get(obj) as Int)
                        }
                        Char::class.java -> {
                            buffer.writeString(field.get(obj) as String)
                        }
                        Long::class.java -> {
                            buffer.getRemoteBuffer().writeLong(field.get(obj) as Long)
                        }
                    }
                } else {
                    val exception: ExceptEntryField = field.declaredAnnotations[0] as ExceptEntryField
                    if (exception.value != "non.value") {
                        buffer.writeString(exception.value)
                    }
                }
            }
            return buffer;
        } else throw NotRegisteredRegistryException()
    }

    fun fromProtocolPuffer(buffer: ByteBuf): T {
        val protocolBuffer = NetworkBuffer(buffer)
        val obj = this.javaClass.getConstructor().newInstance()
        for (fields in obj.javaClass.declaredFields) {
            if (!fields.isAccessible) fields.isAccessible = true
            if (!fields.isAnnotationPresent(ExceptEntryField::class.java)) {
                when (fields.type) {
                    String::class.java -> {
                        fields.set(obj, protocolBuffer.readString())
                    }
                    UUID::class.java -> {
                        fields.set(obj, protocolBuffer.readUUID())
                    }
                    Array<String>::class.java -> {
                        fields.set(obj, protocolBuffer.readStringArray())
                    }
                    Boolean::class.java -> {
                        fields.set(obj, buffer.readBoolean())
                    }
                    Double::class.java -> {
                        fields.set(obj, buffer.readDouble())
                    }
                    Float::class.java -> {
                        fields.set(obj, buffer.readFloat())
                    }
                    Int::class.java -> {
                        fields.set(obj, buffer.readInt())
                    }
                    Char::class.java -> {
                        fields.set(obj, buffer.readChar())
                    }
                    Long::class.java -> {
                        fields.set(obj, buffer.readLong())
                    }
                }
            } else {
                val exception: ExceptEntryField = fields.declaredAnnotations[0] as ExceptEntryField
                if (exception.value != "non.value") {
                    fields.set(obj, protocolBuffer.readString())
                }
            }
        }
        for (field in obj.javaClass.declaredFields) {
            if (!field.isAccessible) field.isAccessible = true
            println(field.get(obj))
        }
        return obj as T;
    }

}