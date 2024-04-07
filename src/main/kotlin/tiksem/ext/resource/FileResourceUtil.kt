package tiksem.ext.resource

import java.io.File
import java.io.InputStream

object FileResourceUtil {
    private val classLoader by lazy {
        FileResourceUtil::class.java.classLoader
    }

    fun getResourceAsText(path: String): String? {
        return classLoader.getResource(path)?.re\adText()
    }

    fun getResourceAsInputStream(path: String): InputStream? {
        return classLoader.getResourceAsStream(path)
    }

    fun getResourceAsByteArray(path: String): ByteArray? {
        return classLoader.getResource(path)?.readBytes()
    }

    fun getResourceAbsolutePath(path: String): String? {
        return classLoader.getResource(path)?.path
    }

    fun getResourceFile(path: String): File? {
        return classLoader.getResource(path)?.path?.let {
            File(it)
        }
    }
}