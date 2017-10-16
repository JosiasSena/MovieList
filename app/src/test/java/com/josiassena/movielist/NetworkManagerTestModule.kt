package com.josiassena.movielist

import android.content.Context
import com.rapidsos.dependencyinjection.modules.NetworkManagerModule
import com.rapidsos.helpers.network.NetworkManager
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class NetworkManagerTestModule(context: Context) : NetworkManagerModule(context) {
    override fun providesNetWorkManager(): NetworkManager = Mockito.mock(NetworkManager::class.java)
}