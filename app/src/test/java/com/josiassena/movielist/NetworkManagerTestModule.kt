package com.josiassena.movielist

import android.content.Context
import com.josiassena.helpers.network.NetworkManager
import com.josiassena.helpers.network.di_module.NetworkManagerModule
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class NetworkManagerTestModule(context: Context) : NetworkManagerModule(context) {

    override fun providesNetWorkManager(): NetworkManager = Mockito.mock(NetworkManager::class.java)

}