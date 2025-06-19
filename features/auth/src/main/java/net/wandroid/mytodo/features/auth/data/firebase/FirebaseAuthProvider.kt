package net.wandroid.mytodo.features.auth.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Hides FirebaseAuth from direct access through koin modules
 */
interface FirebaseAuthProvider {
    val auth: FirebaseAuth
}

internal class FirebaseAuthProviderImpl : FirebaseAuthProvider {
    override val auth: FirebaseAuth
        get() = Firebase.auth
}