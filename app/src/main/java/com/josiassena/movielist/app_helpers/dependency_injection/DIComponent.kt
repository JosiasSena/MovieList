package com.josiassena.movielist.app_helpers.dependency_injection

import com.josiassena.database.database.DatabaseManager
import com.josiassena.database.di_modules.DatabaseModule
import com.josiassena.googleplacesapi.di_modules.GooglePlacesApiModule
import com.josiassena.helpers.network.di_module.NetworkManagerModule
import com.josiassena.movieapi.di_modules.MovieApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.AndroidServicesModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.FirebaseModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.MoviesPreferenceModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import com.josiassena.movielist.favorite_movies.presenter.FavoritesPresenterImpl
import com.josiassena.movielist.full_screen_image.presenter.FullScreenPresenterImpl
import com.josiassena.movielist.genres.presenter.GenrePresenterImpl
import com.josiassena.movielist.genres.view.GenreFragment
import com.josiassena.movielist.home.presenter.HomePresenterImpl
import com.josiassena.movielist.main.view.MainActivity
import com.josiassena.movielist.movie_info.presenter.MovieInfoPresenterImpl
import com.josiassena.movielist.movie_info.receiver.PosterDownloadBroadcastReceiver
import com.josiassena.movielist.movie_info.view.MovieInfoActivity
import com.josiassena.movielist.movies.presenter.MoviesPresenterImpl
import com.josiassena.movielist.nearby_theaters.view.NearbyTheatersFragment
import com.josiassena.movielist.settings.view.SettingsFragment
import com.josiassena.movielist.top_rated_movies.presenter.TopRatedMoviesPresenterImpl
import dagger.Component
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Singleton
@Component(modules = [NetworkManagerModule::class, DatabaseModule::class,
    ProvidersModule::class, MovieApiModule::class, AndroidServicesModule::class,
    MoviesPreferenceModule::class, FirebaseModule::class, GooglePlacesApiModule::class])
interface DIComponent {
    fun inject(mainPresenterImpl: GenrePresenterImpl)
    fun inject(moviesPresenterImpl: MoviesPresenterImpl)
    fun inject(genreFragment: GenreFragment)
    fun inject(movieInfoPresenterImpl: MovieInfoPresenterImpl)
    fun inject(fullScreenPresenterImpl: FullScreenPresenterImpl)
    fun inject(databaseManager: DatabaseManager)
    fun inject(movieInfoActivity: MovieInfoActivity)
    fun inject(posterDownloadBroadcastReceiver: PosterDownloadBroadcastReceiver)
    fun inject(topRatedMoviesPresenterImpl: TopRatedMoviesPresenterImpl)
    fun inject(homePresenterImpl: HomePresenterImpl)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(favoritesPresenterImpl: FavoritesPresenterImpl)
    fun inject(nearbyTheatersFragment: NearbyTheatersFragment)
}