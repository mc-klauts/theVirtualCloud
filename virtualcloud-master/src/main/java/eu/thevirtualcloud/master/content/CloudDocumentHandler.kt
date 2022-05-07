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

package eu.thevirtualcloud.master.content

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.config.IDocument
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.api.console.question.IQuestSession
import eu.thevirtualcloud.api.content.types.CloudConstructionContent
import eu.thevirtualcloud.api.threading.CloudWrapperArray
import eu.thevirtualcloud.api.utilities.FileUtils
import eu.thevirtualcloud.master.CloudLauncher
import java.io.File

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class CloudDocumentHandler {

    val gson = GsonBuilder().setPrettyPrinting().create()!!

    val cloudContentDocument: IDocument = CloudAPI.instance.getCloudConfigFactory().getCloudConfiguration("CloudContent", "")
    val cloudDatabaseDocument: IDocument = CloudAPI.instance.getCloudConfigFactory().getCloudConfiguration("database", "storage")
    val cloudWrapperDocument: IDocument = CloudAPI.instance.getCloudConfigFactory().getCloudConfiguration("wrappers", "storage")
    var cloudConstructionBase: CloudConstructionContent

    init {
        if (!FileUtils.exists("construction.builder")) FileUtils.writeObject(File("construction.builder"), CloudConstructionContent())
        val cloudConstructionBase: CloudConstructionContent = FileUtils.readObject(File("construction.builder"), CloudConstructionContent::class.java)
        CloudLauncher.instance.getCloudBuilder().load(
            cloudConstructionBase.dependenciesFolder,
            cloudConstructionBase.versionsFolder,
            cloudConstructionBase.wrapperProduceFolder,
            cloudConstructionBase.languagePathFolder)

        if (!FileUtils.exists("construction.builder")) FileUtils.writeObject(File("construction.builder"), CloudConstructionContent())
        this.cloudConstructionBase = cloudConstructionBase

        if (cloudContentDocument.catcher().get("cloud.server.port") == null) {
            CloudAPI.instance.getCloudConsole().write("initialize the cloud according to the default values...")
            cloudContentDocument.setDefault("cloud.language", "en")
            cloudContentDocument.setDefault("cloud.server.host", "127.0.0.1")
            val remotePort = askPort()
            cloudContentDocument.setDefault("cloud.server.port", remotePort)
            cloudContentDocument.updateConfiguration()
            cloudContentDocument.loadCached()
        } else {
            cloudContentDocument.loadCached()
        }
        if (this.cloudWrapperDocument.isEmpty()) {
            cloudWrapperDocument.setDefault("remote", gson.toJson(CloudWrapperArray()))
        }
        if (this.cloudDatabaseDocument.isEmpty()) {
            CloudAPI.instance.getCloudConsole().write(ConsoleColorPane.ANSI_BRIGHT_RED + "Unable to locate database information")
        } else {
            CloudAPI.instance.getCloudConsole().write("The cloud has been connected to the database")
        }

    }

    fun readWrappers(): CloudWrapperArray {
        return gson.fromJson(this.cloudWrapperDocument.getString("remote"), CloudWrapperArray::class.java)
    }

    fun saveWrapperArray(array: CloudWrapperArray) {
        this.cloudWrapperDocument.setDefault("remote", gson.toJson(array))
        this.cloudWrapperDocument.updateConfiguration()
        this.cloudWrapperDocument.loadCached()
    }

    private fun askPort(): Int {
        return try {
            IQuestSession.ask("On which port should the cloud server be started?", false).toInt()
        } catch (exception: Exception) {
            CloudAPI.instance.getCloudConsole().write(ConsoleColorPane.ANSI_BRIGHT_RED + "The input could not be parsed" + ConsoleColorPane.ANSI_RESET)
            askPort()
        }
    }

}