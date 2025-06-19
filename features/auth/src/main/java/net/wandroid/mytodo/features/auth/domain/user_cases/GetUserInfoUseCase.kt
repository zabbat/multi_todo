package net.wandroid.mytodo.features.auth.domain.user_cases

import net.wandroid.mytodo.features.auth.domain.model.repositories.AuthRepository
import net.wandroid.mytodo.features.auth.UserAuthManager

internal class GetUserInfoUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): UserAuthManager.UserInfo {
        val token = authRepository.getUserLoginToken()
        return UserAuthManager.UserInfo(
            id = token,
        )
    }
}