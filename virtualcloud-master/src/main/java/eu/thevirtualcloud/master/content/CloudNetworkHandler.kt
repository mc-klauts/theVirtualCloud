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

package eu.thevirtualcloud.master.content

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.api.network.IChannel
import eu.thevirtualcloud.api.network.handler.ICloudHandler
import eu.thevirtualcloud.api.network.handler.impl.ChannelHandler
import eu.thevirtualcloud.master.CloudLauncher
import io.netty.channel.Channel

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class CloudNetworkHandler {

    private val channel: IChannel = CloudAPI.instance.getCloudChannelManager().getCloudChannelFactory().getChannelServer(CloudLauncher.instance.getCloudDocumentHandler().cloudContentDocument.getString("cloud.server.port")!!.toInt())

    init {
        CloudAPI.instance.getCloudConsole().write("Try to " + ConsoleColorPane.ANSI_BRIGHT_YELLOW + "start " + ConsoleColorPane.ANSI_RESET + "Cloud server...")
        if (CloudLauncher.instance.getCloudDocumentHandler().cloudContentDocument.catcher().get("cloud.server.port") != null) {
            channel.handler(ICloudHandler.getDefaultChannelHandler()).insertChannel()
            channel.handler().onChannelInbound(object : ChannelHandler {
                override fun onHandle(connection: Channel) {
                    CloudAPI.instance.getCloudConsole().write("A new channel has connected")
                }
            })
            CloudAPI.instance.getCloudConsole().write("A cloud server has been bound to port " + ConsoleColorPane.ANSI_BRIGHT_GREEN + CloudLauncher.instance.getCloudDocumentHandler().cloudContentDocument.getInt("cloud.server.port") + ConsoleColorPane.ANSI_RESET)
        } else {
            CloudAPI.instance.getCloudConsole().write("an opening of the cloud server was " + ConsoleColorPane.ANSI_BRIGHT_RED + "denied" + ConsoleColorPane.ANSI_RESET)
        }
    }

}