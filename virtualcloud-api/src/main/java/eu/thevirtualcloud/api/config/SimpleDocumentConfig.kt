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

package eu.thevirtualcloud.api.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import eu.thevirtualcloud.api.utilities.FileUtils
import java.io.File

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

abstract class SimpleDocumentConfig(private val name: String, private val path: String): IDocument {

    companion object {
        private val GSON = GsonBuilder().setPrettyPrinting().create()
    }

    private val remoteFile = File(path.replace("/", "\\") + "\\" + name + ".json")
    private val remotePathDir = File(path.replace("/", "\\"))

    private var dataCatcher: JsonObject = JsonObject()

    init {
        if (path != "")
            if (!remoteFile.exists()) remotePathDir.mkdirs()
        if (!remoteFile.exists()) remoteFile.createNewFile()
    }

    override fun updateConfiguration(): IDocument {
        FileUtils.writeObject(this.remoteFile, dataCatcher)
        return this
    }

    override fun loadCached(): IDocument {
        this.dataCatcher = FileUtils.readObject(this.remoteFile, JsonObject::class.java)
        return this
    }

    override fun setDefault(property: String, value: String): IDocument {
        this.dataCatcher.addProperty(property, value)
        return this
    }

    override fun setDefault(property: String, value: Double): IDocument {
        this.dataCatcher.addProperty(property, value)
        return this
    }

    override fun setDefault(property: String, value: Int): IDocument {
        this.dataCatcher.addProperty(property, value)
        return this
    }

    override fun setDefault(property: String, value: Boolean): IDocument {
        this.dataCatcher.addProperty(property, value)
        return this
    }

    override fun getString(property: String): String? {
        return this.dataCatcher[property].asString
    }

    override fun getInt(property: String): Int {
        return this.dataCatcher[property].asInt
    }

    override fun getDouble(property: String): Double {
        return this.dataCatcher[property].asDouble
    }

    override fun getBool(property: String): Boolean {
        return this.dataCatcher[property].asBoolean
    }

}