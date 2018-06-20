/**
 * @author Josias Sena
 */
object Versions {

    const val compileSdkVersion = 27
    const val buildToolsVersion = "27.0.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 27
    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin = "1.2.50"
    const val anko = "0.10.5"
    const val kotlinExtensions = "0.2"

    const val room = "1.1.0"

    const val supportLibrary = "27.1.1"
    const val constraintLayout = "1.1.0"

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

    const val firebaseCore = "15.0.2"
    const val firebaseAuth = "15.1.0"
    const val firebaseUiAuth = "3.3.1"
}

object Deps {

    // Multidex
    const val multidex = "com.android.support:multidex:${Versions.multidex}"

    // Support libraries
    const val supportV4 = "com.android.support:support-v4:${Versions.supportLibrary}"
    const val supportV7 = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    const val supportDesign = "com.android.support:design:${Versions.supportLibrary}"
    const val supportCardviewV7 = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    const val supportCustomtabs = "com.android.support:customtabs:${Versions.supportLibrary}"

    // Constraint layout
    const val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"

    // Mosby MVP
    const val mosbyMvp = "com.hannesdorfmann.mosby:mvp:${Versions.mosby}"

    // GSON
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // OkHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // RxJava 2
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid2}"
    const val rxKotlin2 = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"

    // Picasso
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    // Kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val anko = "org.jetbrains.anko:anko-commons:${Versions.anko}"
    const val ankoCoroutines = "org.jetbrains.anko:anko-coroutines:${Versions.anko}"
    const val kotlinExgtensions = "androidx.core:core-ktx:${Versions.kotlinExtensions}"

    // Room
    const val room = "android.arch.persistence.room:runtime:${Versions.room}"
    const val rxJavaForRoom = "android.arch.persistence.room:rxjava2:${Versions.room}"
    const val roomCompiler = "android.arch.persistence.room:compiler:${Versions.room}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Mockito
    const val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"

    // Testing
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val assertJCore = "org.assertj:assertj-core:${Versions.assertJCore}"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espressoCore}"
    const val RESTMock = "com.github.andrzejchm.RESTMock:android:${Versions.RESTMock}"

    // Firebase
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    const val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    const val firebaseUiAuth = "com.firebaseui:firebase-ui-auth:${Versions.firebaseUiAuth}"

    // Other
    const val wasabeefRecViewAnimator = "jp.wasabeef:recyclerview-animators:${Versions.wasabeefRecViewAnimator}"
    const val materialSearchBar = "com.github.mancj:MaterialSearchBar:${Versions.materialSearchBar}"
    const val pinchToZoom = "com.bogdwellers:pinchtozoom:${Versions.pinchToZoom}"
    const val androidTestRunner = "com.android.support.test:runner:${Versions.androidSupportTestRunner}"
}