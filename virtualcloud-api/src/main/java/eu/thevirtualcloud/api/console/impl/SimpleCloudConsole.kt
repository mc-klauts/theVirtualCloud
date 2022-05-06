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

package eu.thevirtualcloud.api.console.impl

import eu.thevirtualcloud.api.CloudAPI
import eu.thevirtualcloud.api.console.ICloudConsole
import eu.thevirtualcloud.api.event.impl.types.CloudInputEvent
import java.io.Console
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCloudConsole: ICloudConsole {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val prefix: String =
        ConsoleColorPane.ANSI_RESET + " " + dateTimeFormatter.format(LocalDateTime.now()) + " | " + ConsoleColorPane.ANSI_BRIGHT_GREEN + "Cloud " + ConsoleColorPane.ANSI_RESET +  "» "
    private val log: List<String> = ArrayList()

    override fun write(message: String): ICloudConsole {
        System.out.format(prefix)
        println(message)
        return this
    }

    override fun console(): Console {
        return System.console()
    }

    override fun prefix(): String {
        return this.prefix
    }

    override fun insert(): ICloudConsole {
        write(" ")
        println(" ")
        println("██╗   ██╗██╗██████╗ ████████╗██╗   ██╗ █████╗ ██╗      ██████╗██╗      ██████╗ ██╗   ██╗██████╗ \n" +
                "██║   ██║██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██║     ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗\n" +
                "██║   ██║██║██████╔╝   ██║   ██║   ██║███████║██║     ██║     ██║     ██║   ██║██║   ██║██║  ██║\n" +
                "╚██╗ ██╔╝██║██╔══██╗   ██║   ██║   ██║██╔══██║██║     ██║     ██║     ██║   ██║██║   ██║██║  ██║\n" +
                " ╚████╔╝ ██║██║  ██║   ██║   ╚██████╔╝██║  ██║███████╗╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝\n" +
                "  ╚═══╝  ╚═╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝ ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ \n" +
                "                                                                                                ")
        write("This cloud was developed by " + ConsoleColorPane.ANSI_BRIGHT_GREEN + "@Generix030 " + ConsoleColorPane.ANSI_RESET + "and " + ConsoleColorPane.ANSI_BRIGHT_GREEN + "@Rxphael" + ConsoleColorPane.ANSI_RESET)
        write("The cloud runs under version " + ConsoleColorPane.ANSI_BRIGHT_GREEN + "1.0.Alpha" + ConsoleColorPane.ANSI_RESET)
        return this
    }

}