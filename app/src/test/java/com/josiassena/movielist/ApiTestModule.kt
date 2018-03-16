package com.josiassena.movielist

import android.content.Context
import com.josiassena.movielist.app_helpers.dependency_injection.modules.ApiModule
import com.rapidsos.helpers.api.Api
import com.rapidsos.helpers.api.ApiBuilder
import org.mockito.Mockito

/**
 * @author Josias Sena
 */
class ApiTestModule(context: Context) : ApiModule(context) {

    override fun providesApiManager(): ApiBuilder = Mockito.mock(ApiBuilder::class.java)

    override fun providesMovieApi(apiBuilder: ApiBuilder): Api = Mockito.mock(Api::class.java)
}