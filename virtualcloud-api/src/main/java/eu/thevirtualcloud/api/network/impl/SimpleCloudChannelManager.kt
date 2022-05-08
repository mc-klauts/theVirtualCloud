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

import eu.thevirtualcloud.api.network.IChannel
import eu.thevirtualcloud.api.network.IChannelFactory
import eu.thevirtualcloud.api.network.ICloudChannelManager
import io.netty.channel.epoll.Epoll
import io.netty.handler.ssl.ApplicationProtocolConfig.DISABLED
import io.netty.util.ResourceLeakDetector
import java.util.logging.Level.OFF
import java.util.logging.Logger

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCloudChannelManager: ICloudChannelManager {

    private val usingEpoll: Boolean = Epoll.isAvailable()
    private val channelFactory: IChannelFactory = SimpleChannelFactory()

    init {
        Logger.getLogger("io.netty").level = OFF;
    }

    private lateinit var cloudChannel: IChannel

    override fun isUsingEpoll(): Boolean = this.usingEpoll

    override fun getCloudChannel(): IChannel = this.cloudChannel

    override fun getCloudChannelFactory(): IChannelFactory = this.channelFactory

    override fun setCloudChannel(channel: IChannel) {
        this.cloudChannel = channel
    }

}