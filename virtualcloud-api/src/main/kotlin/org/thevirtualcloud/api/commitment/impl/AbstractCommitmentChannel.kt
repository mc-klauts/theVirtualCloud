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
import org.thevirtualcloud.api.CloudAPI
import org.thevirtualcloud.api.commitment.CommitmentChannel
import org.thevirtualcloud.api.commitment.Commitment
import org.thevirtualcloud.api.packets.commitment.PacketCommitment
import kotlin.collections.ArrayList

/**
 *
 * this doc was created on 11.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

abstract class AbstractCommitmentChannel<T: java.io.Serializable>(private val type :Class<out T>): CommitmentChannel<T> {

    companion object {
        protected val gson = Gson()
    }

    private val commitments: ArrayList<Commitment<T>> = ArrayList()

    override fun commitment(handler: Commitment<T>): CommitmentChannel<T> {
        this.commitments.add(handler)
        return this
    }

    override fun remove(index: Int): CommitmentChannel<T> {
        this.commitments.removeAt(index)
        return this
    }

    override fun commitments(): Collection<Commitment<T>> {
        return this.commitments
    }

    override fun remove(index: Commitment<T>): CommitmentChannel<T> {
        this.commitments.remove(index)
        return this
    }

    override fun indexOf(handler: Commitment<T>): Int {
        return this.commitments.indexOf(handler)
    }

    override fun dispatch(element: T): CommitmentChannel<T> {
        val packet = PacketCommitment(gson.toJson(element), element.javaClass.name)
        CloudAPI.instance.getCloudChannelManager().getCloudChannel().dispatchPacket(packet)
        return this
    }

    override fun type(): Class<out T> {
        return this.type
    }

}