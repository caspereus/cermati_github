package com.cermati.putu.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.net.ConnectivityManager
import com.cermati.putu.data.network.exceptions.NoInternetException

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext: Context = context.applicationContext;

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) throw NoInternetException("Make sure you have an active data connection")

        return chain.proceed(chain.request())

    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected;
    }
}