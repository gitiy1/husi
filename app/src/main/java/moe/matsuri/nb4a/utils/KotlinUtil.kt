package moe.matsuri.nb4a.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import io.nekohasekai.sagernet.SagerNet
import io.nekohasekai.sagernet.ktx.Logs
import java.io.Closeable
import java.io.File

// SagerNet Class

const val KB = 1024L
const val MB = KB * 1024
const val GB = MB * 1024

fun SagerNet.cleanWebview() {
    var pathToClean = "app_webview"
    if (isBgProcess) pathToClean += "_$process"
    try {
        val dataDir = filesDir.parentFile!!
        File(dataDir, "$pathToClean/BrowserMetrics").recreate(true)
        File(dataDir, "$pathToClean/BrowserMetrics-spare.pma").recreate(false)
    } catch (e: Exception) {
        Logs.e(e)
    }
}

fun File.recreate(dir: Boolean) {
    if (parentFile?.isDirectory != true) return
    if (dir && !isFile) {
        if (exists()) deleteRecursively()
        createNewFile()
    } else if (!dir && !isDirectory) {
        if (exists()) delete()
        mkdir()
    }
}

// Context utils

@SuppressLint("DiscouragedApi")
fun Context.getDrawableByName(name: String?): Drawable? {
    val resourceId: Int = resources.getIdentifier(name, "drawable", packageName)
    return AppCompatResources.getDrawable(this, resourceId)
}

// Traffic display

fun Long.toBytesString(): String {
    val size = this.toDouble()
    return when {
        this >= GB -> String.format("%.2f GiB", size / GB)
        this >= MB -> String.format("%.2f MiB", size / MB)
        this >= KB -> String.format("%.2f KiB", size / KB)
        else -> "$this Bytes"
    }
}

// List

fun String.listByLineOrComma(): List<String> {
    return this.split(",", "\n").map { it.trim() }.filter { it.isNotEmpty() }
}

// Address

// blur used to make server address blurred.
fun String.blur(): String {
    val l = this.length
    return if (l < 20) {
        val halfLength = this.length / 2
        this.substring(0, halfLength) + "*".repeat(this.length - halfLength)
    } else {
        this.substring(0, 15) + "*".repeat(3)
    }
}


fun Closeable.closeQuietly() {
    try {
        close()
    } catch (rethrown: RuntimeException) {
        throw rethrown
    } catch (_: Exception) {
    }
}
