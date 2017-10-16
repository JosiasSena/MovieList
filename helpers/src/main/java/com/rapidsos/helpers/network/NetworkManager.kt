package com.rapidsos.helpers.network

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Josias Sena
 */
class NetworkManager(val context: Context) {

    private var isNetworkAvailable: Boolean = false

    fun subscribeToCurrentNetworkState(observer: Observer<Connectivity>) {
        ReactiveNetwork.observeNetworkConnectivity(context)
                .subscribeOn(Schedulers.io())
                .doOnEach(object : Observer<Connectivity> {
                    override fun onError(e: Throwable) {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Connectivity) {
                        isNetworkAvailable = isInternetConnectionAvailable(t)
                    }

                    override fun onComplete() {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun isInternetConnectionAvailable(connectivity: Connectivity) =
            connectivity.isAvailable && connectivity.state == NetworkInfo.State.CONNECTED

    fun isNetworkAvailable() = isNetworkAvailable

}