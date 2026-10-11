package com.example.mha

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun registerClient(
        fullName: String,
        email: String,
        password: String
    ) {
        // Create the account in Firebase Authentication.
        val result = auth.createUserWithEmailAndPassword(
            email,
            password
        ).await()

        val user = result.user
            ?: throw Exception("Firebase did not return a user.")

        // Save profile details using the Firebase Auth UID.
        val profile = hashMapOf(
            "uid" to user.uid,
            "fullName" to fullName,
            "email" to email,
            "role" to "client"
        )

        try {
            db.collection("users")
                .document(user.uid)
                .set(profile)
                .await()
        } catch (e: Exception) {
            // Avoid leaving a signed-in user if profile saving fails.
            auth.signOut()
            throw Exception(
                "Your account was created, but your profile could not " +
                        "be saved. Check your Firestore setup and rules."
            )
        }
    }
}
