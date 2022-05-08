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

package eu.thevirtualcloud.api.network.server

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.exceptions.EmptyInterfaceException
import eu.thevirtualcloud.api.exceptions.network.AlreadyRunningException
import eu.thevirtualcloud.api.exceptions.network.ChannelThreadException
import eu.thevirtualcloud.api.exceptions.network.ParalyzedChannelException
import eu.thevirtualcloud.api.network.IChannel
import eu.thevirtualcloud.api.network.connection.IConnectionComponent
import eu.thevirtualcloud.api.network.handler.ICloudHandler
import eu.thevirtualcloud.api.network.impl.SimpleNetworkHandler
import eu.thevirtualcloud.api.network.protocol.Packet
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.util.internal.PlatformDependent

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCloudServer(private val connectionManagement: IConnectionComponent?): IChannel {

    lateinit var futureChannel: Channel
    lateinit var remoteBootstrap: ServerBootstrap
    lateinit var workerGroup: EventLoopGroup

    private var open: Boolean = false
    private var wait: Boolean = false
    private var threads: Int = -1
    private var handler: ICloudHandler? = null

    override fun open(): IChannel {
        when (this.wait) {
            true -> {
                if (open)
                    throw AlreadyRunningException()
                if (this.connectionManagement == null)
                    throw EmptyInterfaceException()
                this.open = true
                this.futureChannel = this.remoteBootstrap
                    .bind(connectionManagement.port())
                    .sync()
                    .channel()
                    .closeFuture()
                    .syncUninterruptibly()
                    .channel()
                return this
            }
            false -> {
                if (open)
                    throw AlreadyRunningException()
                if (this.connectionManagement == null)
                    throw EmptyInterfaceException()
                this.open = true
                this.futureChannel = this.remoteBootstrap
                    .bind(this.connectionManagement.port())
                    .sync()
                    .channel()
                return this
            }
        }
    }

    override fun isOpen(): Boolean {
        return this.open
    }

    override fun closeFuture(): IChannel {
        this.wait = !this.wait
        return this
    }

    override fun isCloseFuture(): Boolean {
        return this.wait
    }

    override fun insertChannel(): IChannel {
        CloudAPI.instance.getCloudChannelManager().setCloudChannel(this)
        if (open)
            throw AlreadyRunningException()
        when(CloudAPI.instance.getCloudChannelManager().isUsingEpoll()) {
            true -> {
                if (PlatformDependent.hasUnsafe()) {
                    if (this.threads == -1)
                        this.workerGroup = EpollEventLoopGroup() else
                        this.workerGroup = EpollEventLoopGroup(this.threads)
                    this.remoteBootstrap = ServerBootstrap()
                        .group(workerGroup)
                        .channel(EpollServerSocketChannel::class.java)
                        .childHandler(SimpleNetworkHandler())
                } else {
                    if (this.threads == -1)
                        this.workerGroup = NioEventLoopGroup() else
                        this.workerGroup = NioEventLoopGroup(this.threads)
                    this.remoteBootstrap = ServerBootstrap()
                        .group(workerGroup)
                        .channel(NioServerSocketChannel::class.java)
                        .childHandler(SimpleNetworkHandler())
                }
            }
            false -> {
                if (this.threads == -1)
                    this.workerGroup = NioEventLoopGroup() else
                        this.workerGroup = NioEventLoopGroup(this.threads)
                this.remoteBootstrap = ServerBootstrap()
                    .group(workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .childHandler(SimpleNetworkHandler())
            }
        }
        return this
    }

    @Suppress("OverridingDeprecatedMember")
    override fun write(raw: Any): IChannel {
        if (!open)
            throw ParalyzedChannelException()
        this.futureChannel.writeAndFlush(raw)
        return this
    }

    override fun maxThreads(index: Int): IChannel {
        if (open)
            throw AlreadyRunningException()
        if (threads == 0 || threads <= -2) throw ChannelThreadException()
        this.threads = index
        return this
    }

    override fun close(): IChannel {
        if (!open)
            throw ParalyzedChannelException()
        this.workerGroup.shutdownGracefully()
        this.futureChannel.close()
        return this
    }

    override fun hasHandler(): Boolean {
        return this.handler != null
    }

    override fun handler(): ICloudHandler {
        return this.handler!!
    }

    override fun handler(handler: ICloudHandler): IChannel {
        this.handler = handler
        return this
    }

    override fun dispatchPacket(packet: Packet<*>): IChannel {
        this.futureChannel.writeAndFlush(packet.toProtocolBuffer())
        return this
    }


}