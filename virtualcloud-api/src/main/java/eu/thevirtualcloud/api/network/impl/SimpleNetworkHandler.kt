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

package eu.thevirtualcloud.api.network.impl

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.api.exceptions.network.NotRegisteredRegistryException
import eu.thevirtualcloud.api.network.protocol.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleNetworkHandler: SimpleChannelInboundHandler<ByteBuf>() {

    override fun channelRead0(p0: ChannelHandlerContext?, buffer: ByteBuf?) {
        try {
            if (CloudAPI.instance.getCloudChannelManager().getCloudChannel().hasHandler()) {
                val packetID = buffer!!.readInt()
                val dispatchedPacket: Packet<*> = CloudAPI.instance.getCloudPacketRegistry().fromID(packetID, buffer)
                DispatcherInterface.dispatchPacket(CloudAPI.instance.getCloudChannelManager().getCloudChannel().handler().packetHandlers(dispatchedPacket.javaClass), dispatchedPacket)
            }
        } catch (exception: NotRegisteredRegistryException) {
            CloudAPI.instance.getCloudConsole().write(ConsoleColorPane.ANSI_BRIGHT_RED + exception.message)
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        super.exceptionCaught(ctx, cause)
        if (CloudAPI.instance.getCloudChannelManager().getCloudChannel().hasHandler()) {
            DispatcherInterface.dispatchExceptionHandle(ctx!!.channel(), cause)
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        super.channelActive(ctx)
        if (CloudAPI.instance.getCloudChannelManager().getCloudChannel().hasHandler()) {
            DispatcherInterface.dispatchInboundHandle(ctx!!.channel())
        }
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        super.channelInactive(ctx)
        if (CloudAPI.instance.getCloudChannelManager().getCloudChannel().hasHandler()) {
            DispatcherInterface.dispatchUnregisterHandle(ctx!!.channel())
        }
    }
}