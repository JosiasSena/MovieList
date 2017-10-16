package com.josiassena.movielist

import android.content.Context
import com.rapidsos.dependencyinjection.modules.ApiModule
import com.rapidsos.helpers.api.Api
import com.rapidsos.helpers.api.ApiManager
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class ApiTestModule(context: Context) : ApiModule(context) {

    override fun providesApiManager(): ApiManager = Mockito.mock(ApiManager::class.java)

    override fun providesMovieApi(apiManager: ApiManager): Api = Mockito.mock(Api::class.java)
}