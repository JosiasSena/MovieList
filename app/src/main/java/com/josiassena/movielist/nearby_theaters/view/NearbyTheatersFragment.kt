package com.josiassena.movielist.nearby_theaters.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.josiassena.googleplacesapi.models.Place
import com.josiassena.helpers.extensions.hide
import com.josiassena.helpers.extensions.show
import com.josiassena.movielist.R
import com.josiassena.movielist.app.App
import com.josiassena.movielist.nearby_theaters.presenter.NearbyTheatersPresenter
import com.josiassena.movielist.nearby_theaters.presenter.NearbyTheatersPresenterImpl
import kotlinx.android.synthetic.main.fragment_nearby_theaters.*
import javax.inject.Inject


class NearbyTheatersFragment : MvpFragment<NearbyTheatersView, NearbyTheatersPresenter>(),
        NearbyTheatersView, OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var theatersPresenterImpl: NearbyTheatersPresenterImpl

    companion object {
        private const val START_ZOOM_LEVEL = 15f
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

        fun newInstance() = NearbyTheatersFragment()
    }

    override fun createPresenter() = theatersPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nearby_theaters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        llMyAddressInfoExpanded.setOnClickListener {
            if (tvMyLocationTitle.isVisible) {
                tvMyLocationTitle.hide()
                tvMyLatLng.hide()
            } else {
                tvMyLocationTitle.show()
                tvMyLatLng.show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    val showRationale = ActivityCompat
                            .shouldShowRequestPermissionRationale(requireActivity(), permission)

                    if (!showRationale) {
                        displayReasonLocationPermissionIsNeeded()
                    } else {
                        if (ActivityCompat.checkSelfPermission(
                                        requireContext(),
                                        android.Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestLocationPermission()
                        }
                    }
                } else {
                    zoomInToCurrentLocation()
                }
            }
        }
    }

    private fun zoomInToCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            if (location == null) {
                requestLocationUpdates()
            } else {
                val currentPlaceLtLng = LatLng(location.latitude, location.longitude)
                val latLngZoom = CameraUpdateFactory.newLatLngZoom(currentPlaceLtLng, START_ZOOM_LEVEL)
                map.animateCamera(latLngZoom)

                getNearbyTheaters(location)
            }
        }
    }

    private fun getNearbyTheaters(location: Location) {
        presenter.getAddressFromLocation(location)

        presenter.getMovieTheatersNearby(getString(R.string.google_maps_key), location)
    }

    override fun onGotAddressFromLocation(address: Address) {
        tvMyLocation.text = address.getAddressLine(0)
        tvMyLatLng.text = "Coordinates: ${address.latitude}, ${address.longitude}"
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                locationResult?.lastLocation?.let {
                    val currentPlaceLtLng = LatLng(it.latitude, it.longitude)
                    val latLngZoom = CameraUpdateFactory.newLatLngZoom(currentPlaceLtLng, START_ZOOM_LEVEL)
                    map.animateCamera(latLngZoom)

                    getNearbyTheaters(it)
                }
            }
        }, presenter.getMapsLooper())
    }

    private fun requestLocationPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun displayReasonLocationPermissionIsNeeded() {
        showLongSnackBar("Location permission is required.")
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.apply {
            isMyLocationButtonEnabled = true
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isCompassEnabled = true
        }

        zoomInToCurrentLocation()
    }

    private fun showLongSnackBar(message: String) {
        fragMap.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }

    override fun displayMovieTheaters(places: List<Place>?) {
        places?.forEach { theater ->

            val geometry = theater.geometry
            val latitude = geometry?.location?.lat as Double
            val longitude = geometry.location?.lng as Double
            val latLng = LatLng(latitude, longitude)

            val markerOptions = MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_movie_map_pin))
                    .title(theater.name)

            map.addMarker(markerOptions)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.terminate()
    }
}
