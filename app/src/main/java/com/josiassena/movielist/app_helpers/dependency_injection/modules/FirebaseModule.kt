package com.josiassena.movielist.app_helpers.dependency_injection.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.josiassena.movielist.firebase.FavoriteMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class FirebaseModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore(auth: FirebaseAuth): FirebaseFirestore {
        val firebaseFirestore = FirebaseFirestore.getInstance(auth.app)

        firebaseFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        return firebaseFirestore
    }

    @Provides
    @Singleton
    fun providesFirebaseFavoriteMovies(firebaseFirestore: FirebaseFirestore): FavoriteMovies {
        return FavoriteMovies(firebaseFirestore)
    }
}