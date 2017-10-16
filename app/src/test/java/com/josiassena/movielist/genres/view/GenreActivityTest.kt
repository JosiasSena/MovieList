package com.josiassena.movielist.genres.view

import com.josiassena.movielist.BaseUnitTest
import com.josiassena.movielist.app.App
import com.josiassena.movielist.genres.presenter.GenrePresenter
import com.josiassena.movielist.genres.view.rec_view.GenresAdapter
import com.rapidsos.helpers.network.NetworkManager
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

/**
 * @author Josias Sena
 */
@RunWith(RobolectricTestRunner::class)
class GenreActivityTest : BaseUnitTest() {

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var genresAdapter: GenresAdapter

    @Mock
    private lateinit var presenter: GenrePresenter

    private lateinit var genreView: GenreView

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
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        App.component = component
        component.inject(this)
    }

    @Test
    fun displayGenres() {
    }

    @Test
    fun showEmptyStateView() {

    }

    @Test
    fun showLoading() {

    }

    @Test
    fun hideLoading() {

    }

    @Test
    fun showNoInternetConnectionError() {

    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }
}