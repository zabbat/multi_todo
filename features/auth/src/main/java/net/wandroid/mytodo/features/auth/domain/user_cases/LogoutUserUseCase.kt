package net.wandroid.mytodo.features.auth.domain.user_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.wandroid.mytodo.features.common.FailData
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.auth.domain.model.repositories.AuthRepository

internal class LogoutUserUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading())
        try {
            authRepository.logout()
            emit(ResultState.Success(data = Unit))
        } catch (e: AuthRepository.AuthException) {
            emit(
                ResultState.Fail(
                    failData = FailData(message = e.message ?: "unknown", exception = e.exception)
                )
            )
        }
    }
}