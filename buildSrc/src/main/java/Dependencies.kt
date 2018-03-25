/**
 * @author Josias Sena
 */
object Versions {

    const val compileSdkVersion = 27
    const val buildToolsVersion = "26.0.2"
    const val minSdkVersion = 21
    const val targetSdkVersion = 27
    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin = "1.2.31"
    const val anko = "0.10.1"

    const val room = "1.0.0"

    const val supportLibrary = "27.1.0"
    const val constraintLayout = "1.0.2"

    const val multidex = "1.0.3"
    const val mosby = "2.0.1"
    const val gson = "2.8.2"
    const val retrofit = "2.4.0"
    const val dagger = "2.11"
    const val mockito = "2.8.9"
    const val okHttp = "3.8.0"

    const val rxJava2 = "2.1.10"
    const val rxAndroid2 = "2.0.2"
    const val rxKotlin = "2.2.0"

    const val picasso = "2.5.2"

    const val jUnit = "4.12"
    const val robolectric = "3.3.2"
    const val assertJCore = "3.8.0"
    const val espressoCore = "3.0.1"
    const val RESTMock = "0.2.2"
    const val androidSupportTestRunner = "1.0.1"

    const val wasabeefRecViewAnimator = "2.2.7"
    const val materialSearchBar = "0.7.5"
    const val pinchToZoom = "0.1"
}

object Deps {

    // Multidex
    val multidex = "com.android.support:multidex:${Versions.multidex}"

    // Support libraries
    val supportV4 = "com.android.support:support-v4:${Versions.supportLibrary}"
    val supportV7 = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    val supportDesign = "com.android.support:design:${Versions.supportLibrary}"
    val supportCardviewV7 = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    val supportCustomtabs = "com.android.support:customtabs:${Versions.supportLibrary}"

    // Constraint layout
    val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"

    // Mosby MVP
    val mosbyMvp = "com.hannesdorfmann.mosby:mvp:${Versions.mosby}"

    // GSON
    val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // OkHttp
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

    // RxJava 2
    val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid2}"
    val rxKotlin2 = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"

    // Picasso
    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    // Kotlin
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"
    val anko = "org.jetbrains.anko:anko-commons:${Versions.anko}"
    val ankoCoroutines = "org.jetbrains.anko:anko-coroutines:${Versions.anko}"

    // Room
    val room = "android.arch.persistence.room:runtime:${Versions.room}"
    val rxJavaForRoom = "android.arch.persistence.room:rxjava2:${Versions.room}"
    val roomCompiler = "android.arch.persistence.room:compiler:${Versions.room}"

    // Dagger
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Mockito
    val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
    val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"

    // Testing
    val jUnit = "junit:junit:${Versions.jUnit}"
    val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    val assertJCore = "org.assertj:assertj-core:${Versions.assertJCore}"
    val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espressoCore}"
    val RESTMock = "com.github.andrzejchm.RESTMock:android:${Versions.RESTMock}"

    // Other
    val wasabeefRecViewAnimator = "jp.wasabeef:recyclerview-animators:${Versions.wasabeefRecViewAnimator}"
    val materialSearchBar = "com.github.mancj:MaterialSearchBar:${Versions.materialSearchBar}"
    val pinchToZoom = "com.bogdwellers:pinchtozoom:${Versions.pinchToZoom}"
    val androidTestRunner = "com.android.support.test:runner:${Versions.androidSupportTestRunner}"
}