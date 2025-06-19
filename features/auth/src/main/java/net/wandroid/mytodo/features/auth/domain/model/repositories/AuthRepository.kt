package net.wandroid.mytodo.features.auth.domain.model.repositories

import kotlinx.coroutines.flow.Flow
import net.wandroid.mytodo.features.common.ResultState

internal interface AuthRepository {
    @Throws(AuthException::class)
    suspend fun createUser(email: String, password: String)

    @Throws(AuthException::class)
    suspend fun loginUser(email: String, password: String)

    suspend fun logout()

    fun getLoginState(): Flow<Boolean>

    @Throws(AuthException::class)
    suspend fun getUserLoginToken(): String?

    class AuthException(message: String, val exception: Exception) : Throwable(message = message)
}