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

package eu.thevirtualcloud.api.network

import eu.thevirtualcloud.api.network.handler.ICloudHandler
import eu.thevirtualcloud.api.network.protocol.Packet
import io.netty.channel.Channel

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

interface IChannel {

    fun open(): IChannel

    fun isOpen(): Boolean

    fun closeFuture(): IChannel

    fun isCloseFuture(): Boolean

    fun insertChannel(): IChannel

    @Deprecated("ChannelException")
    fun write(raw: Any): IChannel

    fun maxThreads(index: Int): IChannel

    fun close(): IChannel

    fun hasHandler(): Boolean

    fun handler(): ICloudHandler

    fun dispatchPacket(packet: Packet<*>): IChannel

    fun handler(handler: ICloudHandler): IChannel

    fun connection(): Channel

}