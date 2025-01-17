package com.sanmer.mrepo.data.module

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sanmer.mrepo.data.json.OnlineModule

data class LocalModule(
    var id: String = "unknown",
    var name: String = id,
    var version: String = id,
    var versionCode: Int = -1,
    var author: String = id,
    var description: String = id
) {
    var state by mutableStateOf(State.DISABLE)

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LocalModule -> id == other.id
            is OnlineModule -> id == other.id
            else -> false
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}