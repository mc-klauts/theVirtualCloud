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

package org.thevirtualcloud.api

import org.thevirtualcloud.api.commands.ICloudCommandHandler
import org.thevirtualcloud.api.commands.impl.SimpleCommandHandler
import org.thevirtualcloud.api.commitment.ICommitmentRegistry
import org.thevirtualcloud.api.commitment.impl.SimpleCommitmentRegistry
import org.thevirtualcloud.api.config.IConfigurationFactory
import org.thevirtualcloud.api.config.impl.SimpleConfigurationFactory
import org.thevirtualcloud.api.console.ICloudConsole
import org.thevirtualcloud.api.console.impl.SimpleCloudConsole
import org.thevirtualcloud.api.content.IContentLoader
import org.thevirtualcloud.api.content.impl.SimpleContentLoader
import org.thevirtualcloud.api.database.IDatabaseAdapter
import org.thevirtualcloud.api.database.impl.DatabaseAdapter
import org.thevirtualcloud.api.event.ICloudEventManager
import org.thevirtualcloud.api.event.impl.SimpleCloudEventManager
import org.thevirtualcloud.api.event.impl.types.CloudAPIBootEvent
import org.thevirtualcloud.api.network.ICloudChannelManager
import org.thevirtualcloud.api.network.IRegistryDispatcher
import org.thevirtualcloud.api.network.impl.SimpleCloudChannelManager
import org.thevirtualcloud.api.network.impl.SimplePacketRegistry
import org.thevirtualcloud.api.network.protocol.PacketRegistry

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class CloudAPI {

    private val cloudChannelManager: ICloudChannelManager = SimpleCloudChannelManager()
    private val cloudEventManager: ICloudEventManager = SimpleCloudEventManager()
    private val cloudConsole: ICloudConsole = SimpleCloudConsole()
    private val cloudCommandHandler: ICloudCommandHandler = SimpleCommandHandler()
    private val cloudContentLoader: IContentLoader = SimpleContentLoader()
    private val cloudConfigFactory: IConfigurationFactory = SimpleConfigurationFactory()
    private val cloudPacketRegistry: PacketRegistry = SimplePacketRegistry()
    private val cloudCommitmentRegistry: ICommitmentRegistry = SimpleCommitmentRegistry()
    private val databaseAdapter: IDatabaseAdapter = DatabaseAdapter("localhost", "cloud", "virtualcloud-development", "72mwG3ATkah088R")

    companion object {
        @JvmStatic
        lateinit var instance: CloudAPI
    }

    init {
        instance = this
        IRegistryDispatcher.dispatcher().dispatch()
        this.cloudEventManager.callEvent(CloudAPIBootEvent())
    }

    fun getCloudContentLoader(): IContentLoader = this.cloudContentLoader

    fun getCloudCommitmentRegistry(): ICommitmentRegistry = this.cloudCommitmentRegistry

    fun getCloudPacketRegistry(): PacketRegistry = this.cloudPacketRegistry

    fun getCloudConfigFactory(): IConfigurationFactory = this.cloudConfigFactory

    fun getCloudChannelManager(): ICloudChannelManager = this.cloudChannelManager

    fun getCloudCommandHandler(): ICloudCommandHandler = this.cloudCommandHandler

    fun getCloudConsole(): ICloudConsole = this.cloudConsole

    fun getCloudEventManager(): ICloudEventManager = this.cloudEventManager

    fun getDatabaseAdapter(): IDatabaseAdapter = this.databaseAdapter

}