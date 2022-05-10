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

package eu.thevirtualcloud.wrapper.content

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.api.exceptions.EmptyInterfaceException
import eu.thevirtualcloud.api.network.IChannel
import eu.thevirtualcloud.api.network.handler.ICloudHandler
import eu.thevirtualcloud.api.packets.PacketInChannelHandshake
import eu.thevirtualcloud.wrapper.CloudWrapper
import eu.thevirtualcloud.wrapper.handler.PacketWrapperHandler
import java.util.*

/**
 *
 * this doc was created on 08.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class CloudNetworkHandler {

    private val channel: IChannel = CloudAPI
        .instance
        .getCloudChannelManager()
        .getCloudChannelFactory()
        .getChannelClient(CloudWrapper.instance
            .getCloudDocumentHandler()
            .cloudDocumentContentHandler
            .masterPort,
            CloudWrapper.instance
                .getCloudDocumentHandler()
                .cloudDocumentContentHandler.masterHost)

    init {
        try {

            channel
                .handler(ICloudHandler.getDefaultChannelHandler())
                .insertChannel()
            PacketWrapperHandler()
            channel.open()

            val handshake: PacketInChannelHandshake = PacketInChannelHandshake(CloudWrapper.instance
                    .getCloudDocumentHandler()
                    .cloudDocumentContentHandler.name, CloudWrapper.instance
                    .getCloudDocumentHandler()
                    .cloudDocumentContentHandler.key)
            this.channel.dispatchPacket(handshake)
            this.channel.dispatchPacket(handshake)
        } catch (exception: EmptyInterfaceException) {
            CloudAPI.instance.getCloudConsole().write(ConsoleColorPane.ANSI_BRIGHT_RED + exception.message)
        }

    }

}