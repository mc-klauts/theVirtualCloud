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

package eu.thevirtualcloud.master.handler;

import eu.thevirtualcloud.api.CloudAPI;
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane;
import eu.thevirtualcloud.api.content.types.wrapper.CloudWrapper;
import eu.thevirtualcloud.api.packets.PacketInChannelHandshake;
import eu.thevirtualcloud.api.packets.PacketOutChannelHandshake;
import eu.thevirtualcloud.master.CloudLauncher;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * this doc was created on 08.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 */

public class PacketMasterHandler {

    public PacketMasterHandler() {

        CloudAPI.getInstance().getCloudChannelManager().getCloudChannel().handler().onPacketHandle(PacketInChannelHandshake.class, raw -> {
            PacketInChannelHandshake packetInChannelHandshake = (PacketInChannelHandshake) raw;
            for (CloudWrapper wrapper : CloudLauncher
                    .getInstance()
                    .getCloudDocumentHandler()
                    .readWrappers()
                    .getWrappers()) {
                if (wrapper.getName().equals(packetInChannelHandshake.a())) {
                    if (wrapper.getKey().equals(packetInChannelHandshake.b())) {
                        PacketOutChannelHandshake packetOutChannelHandshake = new PacketOutChannelHandshake(Objects
                                .requireNonNull(CloudLauncher
                                        .getInstance()
                                        .getCloudDocumentHandler()
                                        .getCloudContentDocument()
                                        .getString("cloud.server.host")));
                        CloudAPI
                                .getInstance()
                                .getCloudChannelManager()
                                .getCloudChannel()
                                .dispatchPacket(packetOutChannelHandshake);
                        CloudAPI.getInstance().getCloudConsole().write((wrapper.getName()) + " has " + ConsoleColorPane.ANSI_BRIGHT_GREEN + "successfully " + ConsoleColorPane.ANSI_RESET + "connected");
                    }
                }
            }
        });

    }
}
