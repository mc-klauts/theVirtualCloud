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

package org.thevirtualcloud.master.runner.key

import java.util.*

/**
 *
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleKeyGenerator: IKeyGenerator {

    override fun generatePassword(length: Int): String {
        val capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
        val specialCharacters = "!@#$"
        val numbers = "1234567890"
        val combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers
        val random = Random()
        val password = CharArray(length)
        password[0] = lowerCaseLetters[random.nextInt(lowerCaseLetters.length)]
        password[1] = capitalCaseLetters[random.nextInt(capitalCaseLetters.length)]
        password[2] = specialCharacters[random.nextInt(specialCharacters.length)]
        password[3] = numbers[random.nextInt(numbers.length)]
        for (i in 4 until length) {
            password[i] = combinedChars[random.nextInt(combinedChars.length)]
        }
        return String(password)
    }

}