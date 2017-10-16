package com.josiassena.movielist.genres.presenter

import com.josiassena.core.Genres
import com.josiassena.movielist.BaseUnitTest
import com.josiassena.movielist.app.App
import com.josiassena.movielist.genres.view.GenreView
import com.rapidsos.database.database.DatabaseManager
import com.rapidsos.helpers.api.Api
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Josias Sena
 */
@RunWith(MockitoJUnitRunner::class)
class GenrePresenterImplTest : BaseUnitTest() {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var databaseManager: DatabaseManager

    @Mock
    private lateinit var genreView: GenreView

    private lateinit var presenter: GenrePresenterImpl
    private lateinit var networkManager: NetworkManager

    companion object {
        @JvmStatic
        @BeforeClass
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                Schedulers.trampoline()
            }
        }
    }

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        App.component = component
        component.inject(this)

        networkManager = Mockito.mock(NetworkManager::class.java)
        presenter = GenrePresenterImpl()
        presenter.attachView(genreView)
    }

    @Test
    fun getGenres() {
        val genres = Mockito.mock(Genres::class.java)
        val response: Response<Genres> = Response.success(genres)
        val observable = Observable.just(response)

        Mockito.`when`(api.getMovieGenres()).thenReturn(observable)

        val observer = TestObserver<Genres>()
        presenter.getGenres()

        Mockito.verify(genreView).showLoading()

        observer.assertSubscribed()
        observer.assertNoErrors()
        observer.assertComplete()
    }

    @Test
    fun getGenresWithError() {
        val genres = Mockito.mock(Genres::class.java)
        Mockito.`when`(databaseManager.getGenres()).thenReturn(genres)

        val body = Mockito.mock(ResponseBody::class.java)
        val response: Response<Genres> = Response.error(400, body)
        val observable = Observable.just(response)
        Mockito.`when`(api.getMovieGenres()).thenReturn(observable)

        val observer = TestObserver<Genres>()
        presenter.getGenres()

        Mockito.verify(genreView).showLoading()
        Thread.sleep(2000)

        Mockito.verify(genreView).hideLoading()
        Mockito.verify(genreView).displayGenres(genres)
    }

    @Test
    fun getGenresWithErrorNullGenres() {
        Mockito.`when`(databaseManager.getGenres()).thenReturn(null)

        val body = Mockito.mock(ResponseBody::class.java)
        val response: Response<Genres> = Response.error(400, body)
        val observable = Observable.just(response)
        Mockito.`when`(api.getMovieGenres()).thenReturn(observable)

        val observer = TestObserver<Genres>()
        presenter.getGenres()

        Mockito.verify(genreView).showLoading()
        Thread.sleep(2000)

        Mockito.verify(genreView).hideLoading()
        Mockito.verify(genreView).showEmptyStateView()
        Mockito.verify(genreView).showNoInternetConnectionError()
    }

    @Test
    fun isConnected() {
        Mockito.`when`(networkManager.isNetworkAvailable()).thenReturn(true)
        assertThat(presenter.isNetworkAvailable()).isTrue()

        Mockito.`when`(networkManager.isNetworkAvailable()).thenReturn(false)
        assertThat(presenter.isNetworkAvailable()).isFalse()
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }
}