package wasdcantmove.basetest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Environment
import com.squareup.spoon.internal.Constants
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import uk.co.scottishpower.test.uiDevice
import java.io.File
import java.io.IOException

@SuppressLint("SetWorldReadable", "SetWorldWritable", "WorldReadableFiles")
class ScreenshotRule : TestRule {

    private var className: String? = null
    private var methodName: String? = null

    override fun apply(base: Statement?, description: Description?): Statement? {
        className = description?.className
        methodName = description?.methodName
        return base
    }

    operator fun invoke(activity: Activity, tag: String) {
        uiDevice.waitForIdle()
        val dir = getOutputDir(activity)
        val filename = "${System.currentTimeMillis()}${Constants.NAME_SEPARATOR}$tag.png"
        val file = File(dir, filename)
        uiDevice.takeScreenshot(file)
        file.setReadable(true, false)
    }

    private fun getOutputDir(context: Context): File =
            getRootDir(context)
                    .let { File(it, className) }
                    .let { File(it, methodName) }
                    .also { createOutputDir(it) }

    @Suppress("DEPRECATION")
    private fun getRootDir(context: Context) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) File(Environment.getExternalStorageDirectory(), "app_" + Constants.SPOON_SCREENSHOTS)
            else context.getDir(Constants.SPOON_SCREENSHOTS, Context.MODE_WORLD_READABLE)

    private fun createOutputDir(dir: File): File {
        val parent = dir.parentFile
        if (!parent.exists()) createOutputDir(parent)
        if (!dir.exists() && !dir.mkdirs()) {
            throw IOException("Could not create dir: ${dir.absolutePath}")
        }
        dir.setReadable(true, false)
        dir.setWritable(true, false)
        dir.setExecutable(true, false)
        return dir
    }
}