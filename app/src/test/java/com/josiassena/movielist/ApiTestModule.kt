package com.josiassena.movielist

import android.content.Context
import com.josiassena.movieapi.Api
import com.josiassena.movieapi.ApiBuilder
import com.josiassena.movieapi.di_modules.MovieApiModule
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class ApiTestModule(context: Context) : MovieApiModule(context) {

    override fun providesApiManager(): ApiBuilder = Mockito.mock(ApiBuilder::class.java)

    override fun providesMovieApi(apiBuilder: ApiBuilder): Api = Mockito.mock(Api::class.java)
}