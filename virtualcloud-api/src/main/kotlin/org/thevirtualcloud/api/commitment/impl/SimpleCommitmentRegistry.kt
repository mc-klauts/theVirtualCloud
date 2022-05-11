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

import org.thevirtualcloud.api.commitment.CommitmentChannel
import org.thevirtualcloud.api.commitment.ICommitmentRegistry
import org.thevirtualcloud.api.packets.commitment.PacketCommitment
import java.io.Serializable

/**
 *
 * this doc was created on 11.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCommitmentRegistry: ICommitmentRegistry {

    private val registry: ArrayList<CommitmentChannel<*>> = ArrayList()

    init {
        DefaultCommitmentManager.registries.add(this)
    }

    override fun <T : Serializable> register(type: Class<out T>): CommitmentChannel<T> {
        val channel: CommitmentChannel<T> = SimpleCommitmentChannel(type)
        this.registry.add(channel)
        return channel
    }

    override fun <T : AbstractCommitmentChannel<*>> register(obj: T) {
        this.registry.add(obj)
    }

    override fun call(packet: PacketCommitment) {
        DefaultCommitmentManager.fire(packet, this)
    }

    override fun channels(): Collection<CommitmentChannel<*>> {
        return this.registry
    }

}