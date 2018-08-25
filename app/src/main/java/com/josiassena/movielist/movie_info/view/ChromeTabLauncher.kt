package com.josiassena.movielist.movie_info.view

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.*
import android.support.v4.content.ContextCompat
import com.josiassena.movielist.R

/**
 * @author Josias Sena
 */
class ChromeTabLauncher(private val context: Context) {

    private var customTabsSession: CustomTabsSession? = null

    fun initialize() {
        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome",
                object : CustomTabsServiceConnection() {

                    override fun onCustomTabsServiceConnected(name: ComponentName?, customTabsClient: CustomTabsClient?) {
                        customTabsClient?.warmup(0)
                        customTabsSession = customTabsClient?.newSession(null)
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                    }
                })
    }

    fun mayLaunchUrl(firstUrl: String?, otherLikelyUrls: List<String>) {
        val otherLikelyUrlBundles = otherLikelyUrls.map {
            val uri = Uri.parse(it)
            Bundle().apply {
                putParcelable(CustomTabsService.KEY_URL, uri)
            }
        }

        customTabsSession?.mayLaunchUrl(Uri.parse(firstUrl), null, otherLikelyUrlBundles)
    }

    fun launchUrl(url: String) {
        val tabsIntent = CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)
                .build()

        tabsIntent.launchUrl(context, Uri.parse(url))
    }
}