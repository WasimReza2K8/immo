package com.example.core.resProvider

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }
}
