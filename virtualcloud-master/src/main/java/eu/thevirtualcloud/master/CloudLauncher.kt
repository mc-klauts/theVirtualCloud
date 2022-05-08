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

package eu.thevirtualcloud.master

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.api.event.ICloudEventRegistry
import eu.thevirtualcloud.master.construction.ConstructionLoader
import eu.thevirtualcloud.master.construction.IConstructionLoader
import eu.thevirtualcloud.master.content.CloudDocumentHandler
import eu.thevirtualcloud.master.content.CloudNetworkHandler
import eu.thevirtualcloud.master.dependencies.IDependencyLoader
import eu.thevirtualcloud.master.dependencies.SimpleDependencyLoader
import eu.thevirtualcloud.master.handler.PacketMasterHandler
import eu.thevirtualcloud.master.layout.ConsoleBaseLoader
import eu.thevirtualcloud.master.runner.key.IKeyGenerator
import eu.thevirtualcloud.master.runner.key.SimpleKeyGenerator

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class CloudLauncher {

    private val cloudApi: CloudAPI = CloudAPI()

    companion object {
        @JvmStatic
        lateinit var instance: CloudLauncher
    }

    init {
        instance = this
        this.cloudApi.getCloudConsole().insert()
    }

    private val cloudBuilder: IConstructionLoader = ConstructionLoader()
    private val cloudDocumentHandler: CloudDocumentHandler = CloudDocumentHandler()
    private val dependencyLoader: IDependencyLoader = SimpleDependencyLoader()
    private val cloudEventRegistry: ICloudEventRegistry = ICloudEventRegistry.insert()
    private val cloudKeyGenerator: IKeyGenerator = SimpleKeyGenerator()

    init {

        CloudAPI.instance.getCloudConsole().write("The master application is attempted to load...")
        dependencyLoader.load()
        CloudNetworkHandler()
        PacketMasterHandler()
        CloudAPI.instance.getCloudConsole().write("The cloud has been " + ConsoleColorPane.ANSI_BRIGHT_GREEN + "successfully " + ConsoleColorPane.ANSI_RESET +  "loaded")
        ConsoleBaseLoader()

    }

    fun getCloudApi(): CloudAPI = this.cloudApi

    fun getCloudKeyGenerator(): IKeyGenerator = this.cloudKeyGenerator

    fun getCloudBuilder(): IConstructionLoader = this.cloudBuilder

    fun getCloudDocumentHandler(): CloudDocumentHandler = this.cloudDocumentHandler

    fun getCloudRemoteEventRegistry(): ICloudEventRegistry = this.cloudEventRegistry

}