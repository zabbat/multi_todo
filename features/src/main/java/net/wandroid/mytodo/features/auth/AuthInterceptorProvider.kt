package net.wandroid.mytodo.features.auth

import okhttp3.Interceptor

interface AuthInterceptorProvider {
    fun getAuthInterceptor(): Interceptor
}