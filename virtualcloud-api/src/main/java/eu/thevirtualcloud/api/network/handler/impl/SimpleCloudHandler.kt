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

@file:Suppress("ReplaceWithEnumMap")

package eu.thevirtualcloud.api.network.handler.impl

import eu.thevirtualcloud.api.network.handler.HandlerType
import eu.thevirtualcloud.api.network.handler.ICloudHandler
import eu.thevirtualcloud.api.network.protocol.Packet
import io.netty.channel.Channel
import java.util.*

/**
 *
 * this doc was created on 08.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCloudHandler: ICloudHandler {

    private val packetHandlers: HashMap<Class<out Packet<*>>, ArrayList<PacketHandler<*>>> = HashMap()
    private val channelHandlers: HashMap<HandlerType, ArrayList<Any>> = HashMap()

    override fun <T : Packet<*>> onPacketHandle(type: Class<T>, handler: PacketHandler<T>) {
        if (packetHandlers.containsKey(type)) {
            this.packetHandlers[type]!!.add(handler)
        } else this.packetHandlers[type] = ArrayList(listOf(handler))
    }

    override fun onChannelInbound(handler: ChannelHandler) {
        if (channelHandlers.containsKey(HandlerType.CHANNEL_INBOUND)) {
            this.channelHandlers[HandlerType.CHANNEL_INBOUND]!!.add(handler)
        } else this.channelHandlers[HandlerType.CHANNEL_INBOUND] = ArrayList(listOf(handler))
    }

    override fun onChannelUnregister(handler: ChannelHandler) {
        if (channelHandlers.containsKey(HandlerType.CHANNEL_UNREGISTER)) {
            this.channelHandlers[HandlerType.CHANNEL_UNREGISTER]!!.add(handler)
        } else this.channelHandlers[HandlerType.CHANNEL_UNREGISTER] = ArrayList(listOf(handler))
    }

    override fun onChannelCatchException(handler: ChannelExceptionHandler) {
        if (channelHandlers.containsKey(HandlerType.CHANNEL_CATCH_EXCEPTION)) {
            this.channelHandlers[HandlerType.CHANNEL_CATCH_EXCEPTION]!!.add(handler)
        } else this.channelHandlers[HandlerType.CHANNEL_CATCH_EXCEPTION] = ArrayList(listOf(handler))
    }

    override fun onOwnChannelMessageHandle(handler: InternalChannelHandler) {
        if (channelHandlers.containsKey(HandlerType.OWN_CHANNEL_MESSAGE)) {
            this.channelHandlers[HandlerType.OWN_CHANNEL_MESSAGE]!!.add(handler)
        } else this.channelHandlers[HandlerType.OWN_CHANNEL_MESSAGE] = ArrayList(listOf(handler))
    }

    override fun packetHandlers(type: Class<out Packet<*>>): ArrayList<PacketHandler<*>>? {
        return this.packetHandlers[type]
    }

    override fun channelHandlers(type: HandlerType): ArrayList<Any> {
        return this.channelHandlers(type)
    }


}