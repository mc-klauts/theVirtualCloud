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

package eu.thevirtualcloud.master.layout

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.console.impl.ConsoleColorPane
import eu.thevirtualcloud.master.CloudLauncher
import eu.thevirtualcloud.master.commands.CloudDatabaseCommand
import eu.thevirtualcloud.master.commands.CloudHelpCommand
import eu.thevirtualcloud.master.commands.CloudStopCommand
import eu.thevirtualcloud.master.commands.CloudWrapperCommand

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class ConsoleBaseLoader {

    init {
        CloudAPI.instance.getCloudCommandHandler().registerCommand("stop", CloudStopCommand())
        CloudAPI.instance.getCloudCommandHandler().registerCommand("help", CloudHelpCommand())
        CloudAPI.instance.getCloudCommandHandler().registerCommand("database", CloudDatabaseCommand())
        CloudAPI.instance.getCloudCommandHandler().registerCommand("produce", CloudWrapperCommand())
        CloudAPI.instance.getCloudConsole().write("the registry loaded" + ConsoleColorPane.ANSI_BRIGHT_GREEN + " " + CloudAPI.instance.getCloudCommandHandler().commands().size + ConsoleColorPane.ANSI_RESET +" commands")

        for (wrapper in CloudLauncher.instance.getCloudDocumentHandler().readWrappers().getWrappers()) {
            CloudAPI.instance.getCloudConsole().write("Wait for " + ConsoleColorPane.ANSI_BRIGHT_YELLOW + wrapper.name + ConsoleColorPane.ANSI_RESET)
        }
        CloudAPI.instance.getCloudCommandHandler().listen(true)
    }

}