package net.wandroid.mytodo.features.auth.data.remote

import android.util.Log
import kotlinx.coroutines.runBlocking
import net.wandroid.mytodo.features.auth.UserAuthManager
import okhttp3.Interceptor
import okhttp3.Response

internal class RetrofitAuthInterceptor(
    private val authManager: UserAuthManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userInfo =
            runBlocking { authManager.getUserInfo() } // could be slow, might need caching
        val request = userInfo.id?.let {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        } ?: chain.request().also {
            Log.e(
                "RetrofitAuthInterceptor",
                "Failed to get user id from firebase, will not use Authorization header"
            )
        }
        return chain.proceed(request)
    }
}