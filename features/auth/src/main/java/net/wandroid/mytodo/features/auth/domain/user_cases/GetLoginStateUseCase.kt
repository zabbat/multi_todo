package net.wandroid.mytodo.features.auth.domain.user_cases

import kotlinx.coroutines.flow.Flow
import net.wandroid.mytodo.features.auth.domain.model.repositories.AuthRepository

internal class GetLoginStateUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.getLoginState()
    }
}