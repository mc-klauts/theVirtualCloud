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

package org.thevirtualcloud.api.commitment.impl

import com.google.gson.Gson
import org.thevirtualcloud.api.commitment.ICommitmentRegistry
import org.thevirtualcloud.api.packets.commitment.PacketCommitment
import java.lang.reflect.Type

/**
 *
 * this doc was created on 11.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class DefaultCommitmentManager {


    companion object {

        var gson = Gson()

        var registries = ArrayList<ICommitmentRegistry>()

        fun fire(packet: PacketCommitment, registry: ICommitmentRegistry) {
            for (channel in registry.channels()) {
                try {
                    val requested = Class.forName(packet.b())
                    if (channel.type().name == requested.name) {
                        for (commitment in channel.commitments()) {
                            commitment.handle(gson.fromJson(packet.a(), requested as Type))
                        }
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }


}