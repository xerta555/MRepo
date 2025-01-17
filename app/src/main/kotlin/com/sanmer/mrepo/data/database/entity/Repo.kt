package com.sanmer.mrepo.data.database.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.reflect.KProperty

@Entity(tableName = "repo")
data class Repo(
    val url: String,
    val name: String = url,
    val size: Int = 0,
    val timestamp: Float = 0f,
    var enable: Boolean = true,
    @PrimaryKey val id: Long = System.currentTimeMillis()
) {
    @get:Ignore
    @set:JvmName("state")
    var isEnable: Boolean by mutableStateOf(enable)

    private operator fun MutableState<Boolean>.setValue(thisObj: Any?, property: KProperty<*>, value: Boolean) {
        this.value = value
        enable = value
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Repo -> url == other.url
            else -> false
        }
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}