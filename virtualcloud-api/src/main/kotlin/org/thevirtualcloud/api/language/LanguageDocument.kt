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

package org.thevirtualcloud.api.language

import com.google.gson.JsonObject
import org.thevirtualcloud.api.CloudAPI
import org.thevirtualcloud.api.common.INameable
import org.thevirtualcloud.api.utilities.FileUtils
import java.io.File

/**
 *
 * this doc was created on 06.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

abstract class LanguageDocument(private val name: String): INameable {

    private var entries: JsonObject = JsonObject()
    private val remoteFile: File = File(CloudAPI.instance.getCloudContentLoader().getCloudConstructionContent().languagePathFolder + "\\" + getName() + ".json")

    final override fun getName(): String {
        return this.name
    }

    fun registerEntry(property: String, entry: String) {
        this.entries.addProperty(property, entry)
    }

    fun getEntryFromCashed(property: String): String {
        return this.entries[property].asString
    }

    fun updateCashed() {
        this.entries = FileUtils.readObject(this.remoteFile, JsonObject::class.java)
    }

    fun updateDocument() {
        FileUtils.writeObject(remoteFile, entries)
    }

    fun getFile(): File {
        return this.remoteFile
    }

}