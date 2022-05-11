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

package org.thevirtualcloud.api.console.impl

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

@Suppress("PropertyName")
class ConsoleColorPane {

    companion object {

        const val ANSI_RESET: String = "\u001B[0m"

        const val ANSI_BLACK: String = "\u001B[30m"
        const val ANSI_RED: String = "\u001B[31m"
        const val ANSI_GREEN: String = "\u001B[32m"
        const val ANSI_YELLOW: String = "\u001B[33m"
        const val ANSI_BLUE: String = "\u001B[34m"
        const val ANSI_PURPLE: String = "\u001B[35m"
        const val ANSI_CYAN: String = "\u001B[36m"
        const val ANSI_WHITE: String = "\u001B[37m"

        const val ANSI_BRIGHT_BLACK: String = "\u001B[90m"
        const val ANSI_BRIGHT_RED: String = "\u001B[91m"
        const val ANSI_BRIGHT_GREEN: String = "\u001B[92m"
        const val ANSI_BRIGHT_YELLOW: String = "\u001B[93m"
        const val ANSI_BRIGHT_BLUE: String = "\u001B[94m"
        const val ANSI_BRIGHT_PURPLE: String = "\u001B[95m"
        const val ANSI_BRIGHT_CYAN: String = "\u001B[96m"
        const val ANSI_BRIGHT_WHITE: String = "\u001B[97m"

        const val ANSI_BG_BLACK: String = "\u001B[40m"
        const val ANSI_BG_RED: String = "\u001B[41m"
        const val ANSI_BG_GREEN: String = "\u001B[42m"
        const val ANSI_BG_YELLOW: String = "\u001B[43m"
        const val ANSI_BG_BLUE: String = "\u001B[44m"
        const val ANSI_BG_PURPLE: String = "\u001B[45m"
        const val ANSI_BG_CYAN: String = "\u001B[46m"
        const val ANSI_BG_WHITE: String = "\u001B[47m"

        const val ANSI_BRIGHT_BG_BLACK: String = "\u001B[100m"
        const val ANSI_BRIGHT_BG_RED: String = "\u001B[101m"
        const val ANSI_BRIGHT_BG_GREEN: String = "\u001B[102m"
        const val ANSI_BRIGHT_BG_YELLOW: String = "\u001B[103m"
        const val ANSI_BRIGHT_BG_BLUE: String = "\u001B[104m"
        const val ANSI_BRIGHT_BG_PURPLE: String = "\u001B[105m"
        const val ANSI_BRIGHT_BG_CYAN: String = "\u001B[106m"
        const val ANSI_BRIGHT_BG_WHITE: String = "\u001B[107m"

    }


}