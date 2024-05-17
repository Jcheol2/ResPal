package com.hocheol.respal.di

import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.api.RespalApi
import com.hocheol.respal.data.remote.api.TokenAuthenticator
import com.hocheol.respal.widget.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        sharedPreferenceStorage: SharedPreferenceStorage
    ): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder: Request.Builder = originalRequest.newBuilder()
            val accessToken = sharedPreferenceStorage.getAccessToken()
            requestBuilder.addHeader("accept", "application/json")
            requestBuilder.addHeader("content-type", "application/json")
            if (!accessToken.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $accessToken")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        val tokenAuthenticator = provideTokenAuthenticator(sharedPreferenceStorage)

        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .apply {
                // SSL 인증을 우회 (임시)
                try {
                    val trustAllCerts: Array<TrustManager> = arrayOf(
                        object : X509TrustManager {
                            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                        }
                    )
                    val sslContext = SSLContext.getInstance("SSL")
                    sslContext.init(null, trustAllCerts, SecureRandom())
                    sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                    hostnameVerifier { _, _ -> true }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .addInterceptor(interceptor) // 헤더에 토큰 넣는 로직
            .authenticator(tokenAuthenticator) // 토큰 만료 시 새로 받는 로직
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        sharedPreferenceStorage: SharedPreferenceStorage
    ): TokenAuthenticator {
        return TokenAuthenticator(sharedPreferenceStorage)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): RespalApi {
        return retrofit.create(RespalApi::class.java)
    }
}