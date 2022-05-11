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

package org.thevirtualcloud.wrapper.handler;

import org.thevirtualcloud.api.CloudAPI;
import org.thevirtualcloud.api.console.impl.ConsoleColorPane;
import org.thevirtualcloud.api.packets.PacketOutChannelHandshake;

/**
 * this doc was created on 08.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 */

public class PacketWrapperHandler {

    public PacketWrapperHandler() {

        CloudAPI.getInstance()
                .getCloudChannelManager()
                .getCloudChannel()
                .handler()
                .onPacketHandle(PacketOutChannelHandshake.class, raw -> {
                    System.out.println("TEST");
                    PacketOutChannelHandshake packetOutChannelHandshake = (PacketOutChannelHandshake) raw;
                    CloudAPI.instance
                            .getCloudConsole()
                            .write("The wrapper " +
                                    ConsoleColorPane.ANSI_BRIGHT_RED +
                                    "successfully " + ConsoleColorPane.ANSI_RESET +
                                    "connected to the master server  (" +
                                    ConsoleColorPane.ANSI_BRIGHT_GREEN +
                                    packetOutChannelHandshake.a() +
                                    ConsoleColorPane.ANSI_RESET + ")");
                });

    }
}
