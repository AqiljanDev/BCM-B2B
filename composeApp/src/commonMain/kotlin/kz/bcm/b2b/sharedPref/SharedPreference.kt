package kz.bcm.b2b.sharedPref

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set


private val setting = Settings()

fun getStringSharedPref(key: String): String? {

    return setting[key]
}

fun putStringSharedPref(key: String, value: String) {
    setting.putString(key, value)
}

fun removeStringSharedPref(key: String) {
    setting.remove(key)
}

enum class URL(val key: String) {
    TOKEN(key = "token")
}