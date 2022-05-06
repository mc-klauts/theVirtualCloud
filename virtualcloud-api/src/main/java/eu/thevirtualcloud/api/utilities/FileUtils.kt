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

package eu.thevirtualcloud.api.utilities

import com.google.gson.GsonBuilder
import lombok.SneakyThrows
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 *
 * this doc was created on 06.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

class FileUtils {

    companion object {
        private const val BUFFER = 2048

        @SneakyThrows
        fun write(file: File, str: String?) {
            if (!file.exists()) {
                val path: String = StringUtils.removeEnd(file.absolutePath, "\\" + file.name)
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                file.createNewFile()
            }
            val writer = FileWriter(file)
            writer.write(str)
            writer.flush()
            writer.close()
        }

        @Throws(IOException::class)
        fun copyFolder(src: File, dest: File) {
            if (src.isDirectory) {
                if (!dest.exists()) {
                    dest.mkdir()
                }
                val files = src.list()
                for (file in files) {
                    val srcFile = File(src, file)
                    val destFile = File(dest, file)
                    copyFolder(srcFile, destFile)
                }
            } else {
                val `in`: InputStream = FileInputStream(src)
                val out: OutputStream = FileOutputStream(dest)
                val buffer = ByteArray(1024)
                var length: Int
                while (`in`.read(buffer).also { length = it } > 0) {
                    out.write(buffer, 0, length)
                }
                `in`.close()
                out.close()
            }
        }

        @Throws(IOException::class)
        fun copyFile(source: File?, dest: File?) {
            FileInputStream(source).use { `is` ->
                FileOutputStream(dest).use { os ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (`is`.read(buffer).also { length = it } > 0) {
                        os.write(buffer, 0, length)
                    }
                }
            }
        }

        @SneakyThrows
        fun read(file: File): String {
            if (!file.exists()) {
                val path: String = StringUtils.removeEnd(file.absolutePath, "\\" + file.name)
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                file.createNewFile()
            }
            var b = ""
            val scanner = Scanner(file)
            while (scanner.hasNextLine()) b = """
     $b${scanner.nextLine()}
     
     """.trimIndent()
            scanner.close()
            return StringUtils.removeEnd(b, "\n")
        }

        @Throws(IOException::class)
        fun download(urlStr: String, file: File) {
            val url = URL(urlStr)
            val bis = BufferedInputStream(url.openStream())
            val fis = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var count = 0
            while (bis.read(buffer, 0, 1024).also { count = it } != -1) {
                fis.write(buffer, 0, count)
            }
            fis.close()
            bis.close()
        }

        @Throws(IOException::class)
        fun extractFileContentFromArchive(file: File, zis: ZipInputStream) {
            val fos = FileOutputStream(file)
            val bos = BufferedOutputStream(fos, BUFFER)
            var len = 0
            val data = ByteArray(BUFFER)
            while (zis.read(data, 0, BUFFER).also { len = it } != -1) {
                bos.write(data, 0, len)
            }
            bos.flush()
            bos.close()
        }

        fun extract(source: String, root: File) {
            try {
                if (!root.exists()) {
                    root.mkdir()
                }
                val bos: BufferedOutputStream? = null
                val fis = FileInputStream(source)
                val zis = ZipInputStream(BufferedInputStream(fis))
                var entry: ZipEntry
                while (zis.nextEntry.also { entry = it } != null) {
                    val fileName = entry.name
                    val file = File(root.absolutePath + File.separator + fileName)
                    if (!entry.isDirectory) {
                        extractFileContentFromArchive(file, zis)
                    } else {
                        if (!file.exists()) {
                            file.mkdirs()
                        }
                    }
                    zis.closeEntry()
                }
                zis.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @SneakyThrows
        fun zipFile(source: Path, zipFileName: String?) {
            if (!Files.isRegularFile(source)) return
            ZipOutputStream(FileOutputStream(zipFileName)).use { zos ->
                FileInputStream(source.toFile()).use { fis ->
                    val zipEntry = ZipEntry(source.fileName.toString())
                    zos.putNextEntry(zipEntry)
                    val buffer = ByteArray(1024)
                    var len: Int
                    while (fis.read(buffer).also { len = it } > 0) {
                        zos.write(buffer, 0, len)
                    }
                    zos.closeEntry()
                }
            }
        }

        private val gson = GsonBuilder().setPrettyPrinting().create()

        fun <T> writeObject(file: File, element: T) {
            val raw = gson.toJson(element)
            write(file, raw)
        }

        fun <T> readObject(file: File, classType: Class<T>): T {
            return gson.fromJson(read(file), classType)
        }

        fun insert(str: String): File? {
            return File(str.replace("//", "\\").replace("/", "\\"))
        }

        fun exists(path: String?): Boolean {
            return Files.exists(Paths.get(path))
        }

        @SneakyThrows
        fun tryCreate(file: File) {
            if (!file.exists()) {
                val path: String = StringUtils.removeEnd(file.path, "\\" + file.name)
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                file.createNewFile()
            }
        }
    }

}