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

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.commands.ICloudCommand
import eu.thevirtualcloud.master.CloudLauncher

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class CloudDatabaseCommand: ICloudCommand {

    override fun onHandle(arguments: Array<String>) {
        if (arguments.size != 4) {
            CloudAPI.instance.getCloudConsole().write(CloudAPI.instance.getCloudCommandHandler().help("database")+ " " + CloudAPI.instance.getCloudCommandHandler().args("user", "password", "database"))
        } else {
            val user = arguments[1]
            val password = arguments[2]
            val database = arguments[3]
            if (CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.isEmpty()) {
                CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.setDefault("host", "localhost")
            }
            CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.setDefault("user", user)
            CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.setDefault("password", password)
            CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.setDefault("database", database)
            CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.updateConfiguration()
            CloudLauncher.instance.getCloudDocumentHandler().cloudDatabaseDocument.loadCached()
            CloudAPI.instance.getCloudConsole().write("The database has been set")
        }
    }

    override fun description(): String {
        return "inserts the database"
    }

}