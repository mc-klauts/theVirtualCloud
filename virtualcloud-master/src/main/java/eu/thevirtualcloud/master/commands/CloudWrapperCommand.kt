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

package eu.thevirtualcloud.master.commands

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.commands.ICloudCommand
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.api.content.types.wrapper.CloudWrapper
import eu.thevirtualcloud.api.content.types.wrapper.CloudWrapperContent
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

class CloudWrapperCommand: ICloudCommand {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun onHandle(arguments: Array<String>) {
        if (arguments.size != 1) {
            try {
                val iKey = CloudLauncher.instance.getCloudKeyGenerator().generatePassword(12)
                val iName = arguments[1]
                val producedWrapper = CloudWrapper(iName, iKey)
                val wrapperContent = CloudWrapperContent(
                    iName,
                    iKey,
                    CloudLauncher.instance.getCloudDocumentHandler().cloudContentDocument.getString("cloud.server.host")!!,
                    CloudLauncher.instance.getCloudDocumentHandler().cloudContentDocument.getInt("cloud.server.port"),
                )
                CloudLauncher.instance.getCloudDocumentHandler().saveWrapperArray(CloudLauncher.instance.getCloudDocumentHandler().readWrappers().add(producedWrapper))
                FileUtils.write(File(CloudLauncher.instance.getCloudDocumentHandler().cloudConstructionBase.wrapperProduceFolder + "//" + iName + "//" + "start.sh"),
                    "screen -S Virtual$iName java -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -XX:CompileThreshold=100 -XX:+UnlockExperimentalVMOptions -XX:+UseCompressedOops -Xmx512m -Xms256m -jar cloud-wrapper.jar")
                FileUtils.write(File(CloudLauncher.instance.getCloudDocumentHandler().cloudConstructionBase.wrapperProduceFolder + "//" + iName + "//" + "CloudContent.json"),
                    gson.toJson(wrapperContent))
                File(CloudLauncher.instance.getCloudDocumentHandler().cloudConstructionBase.wrapperProduceFolder + "//" + iName + "//" + "cloud-wrapper.jar").createNewFile()
                @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                for (file in File(CloudLauncher.instance.getCloudDocumentHandler().cloudConstructionBase.wrapperProduceFolder + "//" + iName).listFiles()) {
                    if (file.isDirectory) file.delete()
                }
                CloudAPI.instance.getCloudConsole().write("You have " + ConsoleColorPane.ANSI_BRIGHT_GREEN + iName + " " + ConsoleColorPane.ANSI_RESET + "successfully created")
            } catch (exception: Exception) {
                CloudAPI.instance.getCloudConsole().write("Unable to " + ConsoleColorPane.ANSI_BRIGHT_RED + "parse input")
            }
        } else {
            CloudAPI.instance.getCloudConsole().write(CloudAPI.instance.getCloudCommandHandler().help("produce") + " " + CloudAPI.instance.getCloudCommandHandler().args("name"))
        }
    }

    override fun description(): String {
        return "easy way to create a wrapper"
    }


}