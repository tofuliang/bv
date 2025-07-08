package dev.aaa1115910.bv.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NetworkUtil {
    suspend fun isMainlandChina() = withContext(Dispatchers.IO) {
        false
    }
}