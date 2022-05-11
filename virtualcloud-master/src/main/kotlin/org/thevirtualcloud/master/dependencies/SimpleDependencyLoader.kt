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

package org.thevirtualcloud.master.dependencies

import org.thevirtualcloud.api.CloudAPI
import org.thevirtualcloud.api.console.impl.ConsoleColorPane
import org.thevirtualcloud.master.CloudLauncher
import java.io.File
import java.net.URL
import java.net.URLClassLoader

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleDependencyLoader: IDependencyLoader {

    @Synchronized
    fun loadLibrary(jar: File) {
        try {
            val url = jar.toURI().toURL()
            val method = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            method.invoke(Thread.currentThread().contextClassLoader, url)
        } catch (ex: Exception) {
            CloudAPI.instance.getCloudConsole().write(ex.localizedMessage)
        }
    }

    override fun load() {
        val dir: File = File(CloudLauncher.instance.getCloudDocumentHandler().cloudConstructionBase.dependenciesFolder.replace("/", "\\"))
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        for (listFile in dir.listFiles()) {
            loadLibrary(listFile)
            CloudAPI.instance.getCloudConsole().write("loaded successfully dependency " + ConsoleColorPane.ANSI_BRIGHT_GREEN + listFile.name + ConsoleColorPane.ANSI_RESET)
        }
    }

}