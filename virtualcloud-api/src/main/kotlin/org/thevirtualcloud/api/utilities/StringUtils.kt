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

package org.thevirtualcloud.api.utilities

/**
 *
 * this doc was created on 06.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

@Suppress("MemberVisibilityCanBePrivate")
class StringUtils {

    companion object {
        fun getWithSpaceAfter(string: String, remote: String): String {
            val strings = listOf(*string.split(" ".toRegex()).toTypedArray())
            return strings[strings.indexOf(remote) + 1]
        }

        fun removeEnd(str: String, remove: String?): String {
            return str.substring(0, str.lastIndexOf(remove!!))
        }

        fun removeBegin(str: String, remove: String?): String {
            return str.substring(str.indexOf(remove!!) + 1, str.length)
        }

        fun getCenter(f: String?, b: String?, str: String): String {
            return removeBegin(removeEnd(str, b), f)
        }

        fun minimize(str: String): String {
            return str.replace(" ", "").replace("\n", "")
        }

        fun minimizeChar(str: String): String {
            val arr = str.split(" ".toRegex()).toTypedArray()
            var build = ""
            for (arg in arr) {
                if (arg.isNotEmpty()) {
                    build = "$build$arg "
                }
            }
            return build.substring(0, build.length - 1)
        }
    }

}