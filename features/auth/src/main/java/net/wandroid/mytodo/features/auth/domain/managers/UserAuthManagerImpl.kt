package net.wandroid.mytodo.features.auth.domain.managers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.wandroid.mytodo.features.auth.domain.user_cases.GetLoginStateUseCase
import net.wandroid.mytodo.features.auth.domain.user_cases.GetUserInfoUseCase
import net.wandroid.mytodo.features.auth.domain.user_cases.LogoutUserUseCase
import net.wandroid.mytodo.features.auth.UserAuthManager
import net.wandroid.mytodo.features.common.ResultState

/**
 * Implementation of [UserAuthManager] which is the core interface that other features can use
 */
internal class UserAuthManagerImpl(
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getLoginStateUseCase: GetLoginStateUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : UserAuthManager {
    override fun logout(): Flow<ResultState<Unit>> {
        return logoutUserUseCase()
    }

    override fun loginStatus(): Flow<UserAuthManager.UserLoginStatus> {
        return getLoginStateUseCase().map { isLoggedIn ->
            if (isLoggedIn) {
                UserAuthManager.UserLoginStatus.Valid
            } else {
                UserAuthManager.UserLoginStatus.Invalid
            }
        }
    }

    override suspend fun getUserInfo(): UserAuthManager.UserInfo {
        return getUserInfoUseCase()
    }
}