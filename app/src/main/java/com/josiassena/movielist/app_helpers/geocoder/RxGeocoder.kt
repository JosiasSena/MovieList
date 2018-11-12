package com.josiassena.movielist.app_helpers.geocoder

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Josias Sena
 */
class RxGeocoder(context: Context) {

    private var geocoder: Geocoder = Geocoder(context)

    fun reverseGeocodeLocation(location: Location, listener: OnReverseGeocodeListener) {
        Observable.fromCallable {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        }
                .subscribeOn(Schedulers.io())
                .map { it.first() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Address> {
                    private lateinit var disposable: Disposable

                    override fun onSubscribe(disposable: Disposable) {
                        this.disposable = disposable
                    }

                    override fun onNext(address: Address) {
                        listener.onSuccess(address)
                    }

                    @SuppressLint("LongLogTag")
                    override fun onError(throwable: Throwable) {
                        listener.onError(throwable)
                    }

                    override fun onComplete() {
                        if (!disposable.isDisposed) {
                            disposable.dispose()
                        }
                    }
                })
    }

}