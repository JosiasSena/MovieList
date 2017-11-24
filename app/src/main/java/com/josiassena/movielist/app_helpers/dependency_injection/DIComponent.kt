package com.josiassena.movielist.app_helpers.dependency_injection

import com.josiassena.movielist.app_helpers.dependency_injection.modules.ApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.DatabaseModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.NetworkManagerModule
import com.josiassena.movielist.full_screen_image.presenter.FullScreenPresenterImpl
import com.josiassena.movielist.genres.presenter.GenrePresenterImpl
import com.josiassena.movielist.genres.view.GenreActivity
import com.josiassena.movielist.movie_info.presenter.MovieInfoPresenterImpl
import com.josiassena.movielist.movies.presenter.MoviesPresenterImpl
import com.rapidsos.database.database.DatabaseManager
import dagger.Component
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class, NetworkManagerModule::class, DatabaseModule::class))
interface DIComponent {
    fun inject(mainPresenterImpl: GenrePresenterImpl)
    fun inject(moviesPresenterImpl: MoviesPresenterImpl)
    fun inject(genreActivity: GenreActivity)
    fun inject(movieInfoPresenterImpl: MovieInfoPresenterImpl)
    fun inject(fullScreenPresenterImpl: FullScreenPresenterImpl)
    fun inject(databaseManager: DatabaseManager)
}