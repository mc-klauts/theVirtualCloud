package org.thevirtualcloud.api.network.protocol

import io.netty.buffer.ByteBuf
import java.util.*

/**
 * java doc created on 02.05.2022
 * @author Alexa
 */

@Suppress("SENSELESS_COMPARISON")
abstract class PacketRegistry {

    private val registry: LinkedList<Class<out Packet<*>>> = LinkedList()

    fun registerPacket(type: Class<out Packet<*>>): PacketRegistry {
        if (!contains(type)) {
            this.registry.add(type)
        }
        return this
    }

    fun unregisterPacket(type: Class<out Packet<*>>): PacketRegistry {
        this.registry.remove(type)
        return this
    }

    fun clear(): PacketRegistry {
        this.registry.clear()
        return this
    }

    fun collect(): Collection<Class<out Packet<*>>> {
        return this.registry
    }

    fun packetTypeID(type: Class<out Packet<*>>): Int {
        return if (registry.contains(type)) {
            registry.indexOf(type)
        } else {
            -1
        }
    }

    fun contains(type: Class<out Packet<*>>): Boolean {
        if (registry.isEmpty()) return false
        for (clazz in registry) {
            if (clazz.name.equals(type.name)) {
                return true
            }
        }
        return false
    }

    fun fromID(id: Int, buffer: ByteBuf): Packet<*> {
        var obj = this.registry[id].getConstructor().newInstance()
        obj = obj.fromProtocolPuffer(buffer) as Packet<*>?
        return obj
    }

}