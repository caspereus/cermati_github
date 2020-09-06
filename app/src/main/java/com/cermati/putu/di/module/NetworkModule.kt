package com.cermati.putu.di.module

import android.content.Context
import com.cermati.putu.BuildConfig
import com.cermati.putu.data.network.NetworkConnectionInterceptor
import com.cermati.putu.data.network.exceptions.NetworkConstants
import com.cermati.putu.data.repositories.user.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        provideUserApi(get())
    }

    single {
        provideRetrofit(
            get(),
            NetworkConstants.BASE_URL
        )
    }

    single {
        providesHttpLoggingInterceptor()
    }


    single {
        provideHttpClient(
            NetworkConstants.CONNECTION_TIMEOUT,
            NetworkConstants.READ_TIMEOUT,
            androidContext(),
            get()
        )
    }
}

fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun provideHttpClient(
    connectTimeout: Long,
    readTimeout: Long,
    context: Context,
    interceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(NetworkConnectionInterceptor(context))
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .build()
}

fun provideUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
}