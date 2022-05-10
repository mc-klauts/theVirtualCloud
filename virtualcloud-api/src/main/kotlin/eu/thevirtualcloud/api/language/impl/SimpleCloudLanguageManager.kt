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

package eu.thevirtualcloud.api.language.impl

import eu.thevirtualcloud.api.language.ICloudLanguageManager
import eu.thevirtualcloud.api.language.LanguageDocument

/**
 *
 * this doc was created on 06.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class SimpleCloudLanguageManager: ICloudLanguageManager {

    private val registeredDocuments: HashMap<String, ArrayList<LanguageDocument>> = HashMap()

    override fun getSelectedLanguage(): String {
        return "de"
    }

    override fun registerDocument(document: LanguageDocument) {
        if (this.registeredDocuments.containsKey(document.getName().substring(1))) {
            this.registeredDocuments[document.getName().substring(1)]!!.add(document)
            if (!document.getFile().exists()) {
                document.updateDocument()
            } else {
                document.updateCashed()
            }
        } else {
            val hashed: ArrayList<LanguageDocument> = ArrayList()
            hashed.add(document)
            this.registeredDocuments[document.getName().substring(1)] = hashed
            if (!document.getFile().exists()) {
                document.updateDocument()
            } else {
                document.updateCashed()
            }
        }
    }

    override fun getCashedEntry(property: String): String {
        for (doc in this.registeredDocuments[property.substring(1)]!!) {
            return doc.getEntryFromCashed(property.substring(2, property.length))
        }
        return "null"
    }


}