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

package org.thevirtualcloud.api.manager.groups

import com.google.gson.GsonBuilder
import org.thevirtualcloud.api.CloudAPI
import org.thevirtualcloud.api.utilities.FileUtils
import java.io.File

/**
 *
 * this doc was created on 11.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SimpleCloudGroupManager: ICloudGroupManager {

    private val dirFile: File = File("storage//groups")

    init {
        when(CloudAPI.instance.getCloudChannelManager().isServerChannel()) {
            true -> if (!this.dirFile.exists()) this.dirFile.mkdirs()
        }
    }

    override fun createGroup(group: SimpleCloudGroup) {
        FileUtils.writeObject(File(this.dirFile.absolutePath + "//" + group.name + ".json"), group)
    }

    override fun existsGroup(name: String): Boolean {
        for (file in listFiles()!!) {
            if (file.name.replace(".json", "")
                    .equals(name, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override fun getGroupFromFile(name: String): SimpleCloudGroup? {
        for (file in listFiles()!!) {
            if (file.name.replace(".json", "")
                    .equals(name, ignoreCase = true)) {
                return FileUtils.readObject(file, SimpleCloudGroup::class.java)
            }
        }
        return null
    }

    override fun listGroups(): List<SimpleCloudGroup>? {
        val list= ArrayList<SimpleCloudGroup>()
        return try {
            for (file in dirFile.listFiles()) {
                if (file.name.endsWith(".json"))
                    list.add(FileUtils.readObject(file, SimpleCloudGroup::class.java))
            }
            list
        } catch (_: java.lang.NullPointerException) {
            null
        }
    }

    override fun listFiles(): List<File>? {
        val list= ArrayList<File>()
        return try {
            for (file in dirFile.listFiles()) {
                if (file.name.endsWith(".json"))
                    list.add(file)
            }
            list
        } catch (_: java.lang.NullPointerException) {
            null
        }
    }
}