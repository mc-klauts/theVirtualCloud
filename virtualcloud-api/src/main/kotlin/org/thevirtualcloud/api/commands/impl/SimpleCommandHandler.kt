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

package org.thevirtualcloud.api.commands.impl

import org.thevirtualcloud.api.CloudAPI
import org.thevirtualcloud.api.commands.ICloudCommand
import org.thevirtualcloud.api.commands.ICloudCommandHandler
import org.thevirtualcloud.api.console.impl.ConsoleColorPane
import org.thevirtualcloud.api.event.impl.types.CloudCommandEvent
import org.thevirtualcloud.api.event.impl.types.CloudInputEvent
import org.thevirtualcloud.api.exceptions.EmptyInterfaceException
import java.lang.String.join
import java.util.*

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCommandHandler: ICloudCommandHandler {

    private val registry: HashMap<String, ICloudCommand> = HashMap()

    private var listen: Boolean = false
    private val commandThread: Timer = Timer()
    private val remoteReader = Scanner(System.`in`)

    override fun registerCommand(command: String, handler: ICloudCommand): ICloudCommandHandler {
        this.registry[command] = handler
        return this
    }

    override fun unregisterCommand(command: String): ICloudCommandHandler {
        this.registry.remove(command)
        return this
    }

    override fun dispatchCommand(command: String): ICloudCommandHandler {
        if (this.registry.containsKey(command))
            this.registry[command]!!.onHandle(emptyArray())
        return this
    }

    override fun listen(listen: Boolean) {
        when (listen) {
            true -> {
                this.listen = true
                if (this.listen) {
                    commandThread.schedule(object : TimerTask() {
                        override fun run() {
                            if (remoteReader.hasNextLine()) {
                                val line = remoteReader.nextLine().split(" ")
                                CloudAPI.instance.getCloudEventManager().callEvent(CloudInputEvent(join(" ", line)))
                                for (key in registry.keys) {
                                    if (line[0].equals(key, ignoreCase = true)) {
                                        CloudAPI.instance.getCloudEventManager().callEvent(CloudCommandEvent(line.toTypedArray()))
                                        registry[key]!!.onHandle(line.toTypedArray())
                                        print(CloudAPI.instance.getCloudConsole().profilePrefix())
                                        return
                                    }
                                }

                                CloudAPI.instance.getCloudConsole().write(ConsoleColorPane.ANSI_BRIGHT_RED + "This command couldn't found")
                                print(CloudAPI.instance.getCloudConsole().profilePrefix())
                            }
                        }
                    }, 100, 100)
                }
            }
            false -> {
                try {
                    this.commandThread.cancel()
                } catch (_: Exception) {
                }
                this.listen = false
            }
        }
    }



    override fun isListen(): Boolean {
        return this.listen
    }

    override fun scanner(): Scanner {
        return this.remoteReader
    }

    override fun description(command: String): String {
        return this.registry[command]!!.description()
    }

    override fun help(command: String): String {
        if (this.registry.containsKey(command))
            return CloudAPI.instance.getCloudCommandHandler().description(command) + " - " + ConsoleColorPane.ANSI_BRIGHT_GREEN + command
        else throw EmptyInterfaceException()
    }

    override fun commands(): Collection<String> {
        return this.registry.keys
    }

    override fun args(vararg args: String): String {
        var build = ConsoleColorPane.ANSI_RESET + "(" + ConsoleColorPane.ANSI_BRIGHT_GREEN
        for (arg in args) {
            build = build.plus(ConsoleColorPane.ANSI_BRIGHT_GREEN + arg + ConsoleColorPane.ANSI_RESET + ", ")
        }
        return build.substring(0, build.lastIndexOf(",")).plus(")")
    }

    override fun argsArray(args: Array<String>): String {
        var build = ConsoleColorPane.ANSI_RESET + "(" + ConsoleColorPane.ANSI_BRIGHT_GREEN
        for (arg in args) {
            build = build.plus(ConsoleColorPane.ANSI_BRIGHT_GREEN + arg + ConsoleColorPane.ANSI_RESET + ", ")
        }
        return build.substring(0, build.lastIndexOf(",")).plus(")")
    }


}