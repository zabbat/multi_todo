package net.wandroid.mytodo.features.auth

import kotlinx.coroutines.flow.Flow
import net.wandroid.mytodo.features.common.ResultState

interface UserAuthManager {
    fun logout(): Flow<ResultState<Unit>>
    fun loginStatus(): Flow<UserLoginStatus>
    suspend fun getUserInfo(): UserInfo

    sealed interface UserLoginStatus {
        data object Valid : UserLoginStatus
        data object Invalid : UserLoginStatus
    }

    data class UserInfo(val id: String?)
}