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
class ChromeTabLauncher {

    private var customTabsSession: CustomTabsSession? = null
    private var context: Context? = null

    companion object {
        private const val CHROME_PACKAGE_NAME = "com.android.chrome"
    }

    fun initialize(context: Context?) {
        this.context = context

        CustomTabsClient.bindCustomTabsService(context, CHROME_PACKAGE_NAME,
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
        context?.let {
            val tabsIntent = CustomTabsIntent.Builder()
                    .setToolbarColor(ContextCompat.getColor(it, R.color.colorPrimary))
                    .setSecondaryToolbarColor(ContextCompat.getColor(it, R.color.colorPrimaryDark))
                    .setStartAnimations(it, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(it, R.anim.slide_in_left, R.anim.slide_out_right)
                    .build()

            tabsIntent.launchUrl(context, Uri.parse(url))
        }

    }

    fun terminate() {
        context = null
        customTabsSession = null
    }
}