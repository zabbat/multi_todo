package net.wandroid.mytodo.features.auth.data.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class FirebaseAuthManager(
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val applicationScope: CoroutineScope,
) {

    suspend fun createUser(
        email: String,
        password: String,
    ): AuthResult = suspendCoroutine { count ->
        firebaseAuthProvider.auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { count.resume(it) }
            .addOnFailureListener { count.resumeWithException(it) }
    }

    suspend fun loginUser(
        email: String,
        password: String,
    ): AuthResult = suspendCoroutine { count ->
        firebaseAuthProvider.auth.signInWithEmailAndPassword(
            email,
            password,
        )
            .addOnSuccessListener { count.resume(it) }
            .addOnFailureListener { count.resumeWithException(it) }

    }

    fun logout() {
        return firebaseAuthProvider.auth.signOut()
    }

    suspend fun getUserToken(forceRefresh: Boolean): String? {
        val user = firebaseAuthProvider.auth.currentUser ?: return null
        return suspendCoroutine { cont ->
            user.getIdToken(forceRefresh)
                .addOnSuccessListener { result ->
                    cont.resume(result.token)
                }
                .addOnFailureListener {
                    cont.resumeWithException(
                        IllegalStateException("user not logged in")
                    )
                }
        }
    }

    fun isUserLoggedInState(): Flow<Boolean> = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            val result = auth.currentUser != null
            trySend(result)
        }
        firebaseAuthProvider.auth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuthProvider.auth.removeAuthStateListener(authStateListener)
        }
    }.distinctUntilChanged()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = firebaseAuthProvider.auth.currentUser != null,
        )
}