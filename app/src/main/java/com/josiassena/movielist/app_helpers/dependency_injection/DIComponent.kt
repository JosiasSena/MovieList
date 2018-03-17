package com.josiassena.movielist.app_helpers.dependency_injection

import com.josiassena.movieapi.di_modules.MovieApiModule
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ProvidersModule
import com.josiassena.movielist.full_screen_image.presenter.FullScreenPresenterImpl
import com.josiassena.movielist.genres.presenter.GenrePresenterImpl
import com.josiassena.movielist.genres.view.GenreActivity
import com.josiassena.movielist.movie_info.presenter.MovieInfoPresenterImpl
import com.josiassena.movielist.movies.presenter.MoviesPresenterImpl
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.database.di_modules.DatabaseModule
import com.rapidsos.helpers.network.di_module.NetworkManagerModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Josias Sena
 */
@Singleton
@Component(modules = [NetworkManagerModule::class, DatabaseModule::class,
    ProvidersModule::class, MovieApiModule::class])
interface DIComponent {
    fun inject(mainPresenterImpl: GenrePresenterImpl)
    fun inject(moviesPresenterImpl: MoviesPresenterImpl)
    fun inject(genreActivity: GenreActivity)
    fun inject(movieInfoPresenterImpl: MovieInfoPresenterImpl)
    fun inject(fullScreenPresenterImpl: FullScreenPresenterImpl)
    fun inject(databaseManager: DatabaseManager)
}