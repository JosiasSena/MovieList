package com.josiassena.movielist.nearby_theaters.presenter

import android.annotation.SuppressLint
import android.location.Address
import android.location.Location
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.josiassena.googleplacesapi.models.PlaceResponse
import com.josiassena.movielist.app_helpers.data_providers.theaters_nearby.NearbyMovieTheatersProvider
import com.josiassena.movielist.app_helpers.geocoder.OnReverseGeocodeListener
import com.josiassena.movielist.app_helpers.geocoder.RxGeocoder
import com.josiassena.movielist.nearby_theaters.view.NearbyTheatersView
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Josias Sena
 */
class NearbyTheatersPresenterImpl @Inject constructor(private val movieTheatersProvider: NearbyMovieTheatersProvider,
                                                      private val geocoder: RxGeocoder) :
        MvpBasePresenter<NearbyTheatersView>(), NearbyTheatersPresenter {

    private val compositeDisposable = CompositeDisposable()

    private var mapHandlerThread: HandlerThread = HandlerThread("mapBackgroundHandlerThread")

    companion object {
        private const val TAG = "NearbyTheatersPresenterImpl"
    }

    init {
        if (!mapHandlerThread.isAlive) {
            mapHandlerThread.start()
        }
    }

    override fun getMovieTheatersNearby(apiKey: String, location: Location) {
        movieTheatersProvider.getMovieTheaters(apiKey, location,
                object : Observer<Response<PlaceResponse>> {

                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onNext(response: Response<PlaceResponse>) {
                        if (isViewAttached) {
                            view?.displayMovieTheaters(response.body()?.places)
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onError(throwable: Throwable) {
                        Log.e(TAG, throwable.message, throwable)
                    }

                    override fun onComplete() {
                    }
                })
    }

    override fun getAddressFromLocation(location: Location) {

        geocoder.reverseGeocodeLocation(location, object : OnReverseGeocodeListener {
            override fun onSuccess(address: Address) {
                if (isViewAttached) {
                    view?.onGotAddressFromLocation(address)
                }
            }

            @SuppressLint("LongLogTag")
            override fun onError(throwable: Throwable) {
                Log.e(TAG, throwable.message, throwable)
            }
        })

    }

    override fun getMapsLooper(): Looper = mapHandlerThread.looper

    override fun terminate() {
        unSubscribe()

        mapHandlerThread.quitSafely()
    }

    override fun unSubscribe() {
        compositeDisposable.clear()

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}