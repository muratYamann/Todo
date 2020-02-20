package com.yaman.core.ioc

import android.content.Context
import android.os.Build
import com.yaman.core.BuildConfig
import com.yaman.core.constants.Constants
import com.yaman.core.constants.Constants.CLIENT_ID
import com.yaman.core.constants.Constants.CLIENT_PASS
import com.yaman.core.constants.Constants.HEADER_API_VERSION
import com.yaman.core.constants.Constants.HEADER_CLIENT_ID
import com.yaman.core.constants.Constants.HEADER_CLIENT_PASS
import com.yaman.core.constants.Constants.HEADER_CLIENT_USER_AGENT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(gsonConverterFactory: GsonConverterFactory,
                         rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, interceptor: Interceptor): Retrofit {

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder().baseUrl(Constants.API_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(client)
                .build()
    }


    @Provides
    @Singleton
    fun providesInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->

            var request: Request?
            val original = chain.request()
            var response: Response?

            request = original.newBuilder()
                    .header(HEADER_API_VERSION, BuildConfig.VERSION_NAME)
                    .header(HEADER_CLIENT_USER_AGENT, getUserAgent())
                    .header(HEADER_CLIENT_ID, CLIENT_ID)
                    .header(HEADER_CLIENT_PASS, CLIENT_PASS)
                    .method(original.method(), original.body())
                    .build()

            response = chain.proceed(request!!)


            if (request != null)
                response
            else
                chain.proceed(chain.request())
        }
    }


    private fun getUserAgent(): String {
        return createUserAgent()
    }

    private fun createUserAgent(): String {
        return "ANDROID APP " + Build.MODEL + Build.VERSION.SDK_INT + " "
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}
