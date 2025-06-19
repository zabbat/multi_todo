package net.wandroid.mytodo.features.auth.data.remote

import net.wandroid.mytodo.features.auth.AuthInterceptorProvider
import okhttp3.Interceptor

internal class AuthInterceptorProviderImpl(
    private val authInterceptor: Interceptor,
) : AuthInterceptorProvider {
    override fun getAuthInterceptor(): Interceptor {
        return authInterceptor
    }
}