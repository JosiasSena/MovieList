package com.josiassena.movielist.app_helpers.dependency_injection.modules

import android.app.DownloadManager
import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Module
open class AndroidServicesModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesDownloadManager(): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    @Singleton
    fun providesGeoCoder() = Geocoder(context, Locale.getDefault())

}