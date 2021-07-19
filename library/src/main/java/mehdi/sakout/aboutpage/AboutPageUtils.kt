package mehdi.sakout.aboutpage

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources.NotFoundException
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

internal object AboutPageUtils {
    fun isAppInstalled(context: Context, appName: String): Boolean {
        val pm = context.packageManager
        var installed = false
        val packages = pm.getInstalledPackages(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == appName) {
                installed = true
                break
            }
        }
        return installed
    }

    @ColorInt
    fun resolveColorAttr(context: Context, @AttrRes attr: Int): Int {
        val outValue = resolveAttr(context, attr)
        return if (outValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && outValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            outValue.data
        } else ContextCompat.getColor(context, outValue.resourceId)
    }

    fun resolveResIdAttr(context: Context, @AttrRes attr: Int, defaultValue: Int): Int {
        return try {
            resolveAttr(context, attr).resourceId
        } catch (e: NotFoundException) {
            defaultValue
        }
    }

    private fun resolveAttr(context: Context, @AttrRes attr: Int): TypedValue {
        val outValue = TypedValue()
        if (!context.theme.resolveAttribute(attr, outValue, true)) {
            throw NotFoundException("'" + context.resources.getResourceName(attr) + "' is not set.")
        }
        return if (outValue.type == TypedValue.TYPE_ATTRIBUTE) {
            resolveAttr(context, outValue.data)
        } else outValue
    }

    fun isNightModeEnabled(context: Context): Boolean {
        val nightMode = context.resources.configuration.uiMode
        return nightMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}