package net.wandroid.mytodo.features.auth.data.repositories

import kotlinx.coroutines.flow.Flow
import net.wandroid.mytodo.features.auth.data.firebase.FirebaseAuthManager
import net.wandroid.mytodo.features.auth.domain.model.repositories.AuthRepository

internal class FirebaseAuthRepository(
    private val firebaseAuthManager: FirebaseAuthManager,
) : AuthRepository {
    override suspend fun createUser(email: String, password: String) {
        try {
            firebaseAuthManager.createUser(email, password)
        } catch (e: Exception) {
            throw AuthRepository.AuthException(
                message = "could not create u:$email, ${e.message}",
                exception = e,
            )
        }

    }

    override suspend fun loginUser(email: String, password: String) {
        try {
            firebaseAuthManager.loginUser(email, password)
        } catch (e: Exception) {
            throw AuthRepository.AuthException(
                message = "could not login u:$email, ${e.message}",
                exception = e,
            )
        }
    }

    override suspend fun logout() {
        firebaseAuthManager.logout()
    }

    override fun getLoginState(): Flow<Boolean> {
        return firebaseAuthManager.isUserLoggedInState()
    }

    override suspend fun getUserLoginToken(): String? {
        return try {
            firebaseAuthManager.getUserToken(false)
        } catch (e: NullPointerException) {
            throw AuthRepository.AuthException(
                message = e.message ?: "Could not fetch user",
                exception = e,
            )
        }
    }
}