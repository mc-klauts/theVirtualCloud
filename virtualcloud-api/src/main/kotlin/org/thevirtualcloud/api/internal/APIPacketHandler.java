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

package org.thevirtualcloud.api.internal;

import org.thevirtualcloud.api.CloudAPI;
import org.thevirtualcloud.api.packets.wrapper.PacketInWrapperCheck;

import java.util.ArrayList;

/**
 * this doc was created on 11.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 */

public class APIPacketHandler {

    private final ArrayList<CashedWrapper> cashedWrappers = new ArrayList<>();

    public APIPacketHandler() {
        CloudAPI.getInstance().getCloudChannelManager().getCloudChannel().handler().onChannelUnregister(channel -> {
            if (this.containsID(channel.id().asLongText())) this.cashedWrappers
                    .remove(byID(channel
                            .id()
                            .asLongText()));
        });
        CloudAPI.getInstance().getCloudChannelManager().getCloudChannel().handler().onPacketHandle(PacketInWrapperCheck.class, raw -> {
            PacketInWrapperCheck packet = (PacketInWrapperCheck) raw;
            if (!packet.b().equals(CloudAPI.getInstance().getUID().toString())) {
                if (!this.contains(packet.a())) this.cashedWrappers
                        .add(new CashedWrapper(packet.a(), packet.b()));
            }
        });
    }

    public boolean contains(String name) {
        for (CashedWrapper wrapper : this.cashedWrappers) {
            if (wrapper.getWrapperName().equals(name)) return true;
        }
        return false;
    }

    public boolean containsID(String id) {
        for (CashedWrapper wrapper : this.cashedWrappers) {
            if (wrapper.getChannelID().equals(id)) return true;
        }
        return false;
    }

    public CashedWrapper byID(String id) {
        for (CashedWrapper wrapper : this.cashedWrappers) {
            if (wrapper.getChannelID().equals(id)) return wrapper;
        }
        return null;
    }

}
