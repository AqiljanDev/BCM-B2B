package kz.bcm.b2b.sharedPref

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set


private val setting = Settings()

fun getStringSharedPref(key: String): String? {
    println("Shared Preference -> getString = key: $key")
    return setting[key]
}

fun putStringSharedPref(key: String, value: String) {
    println("Shared Preference -> putString = key: $key, value: $value")
    setting.putString(key, value)
}

fun removeStringSharedPref(key: String) {
    setting.remove(key)
}

enum class URL(val key: String) {
    TOKEN(key = "token")
}