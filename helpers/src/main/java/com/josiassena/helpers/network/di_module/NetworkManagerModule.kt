package com.josiassena.helpers.network.di_module

import android.content.Context
import com.josiassena.helpers.network.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class NetworkManagerModule(private val context: Context) {

    @Provides
    @Singleton
    open fun providesNetWorkManager() = NetworkManager(context)
}