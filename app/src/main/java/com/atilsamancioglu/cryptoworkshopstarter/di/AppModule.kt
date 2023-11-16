package com.atilsamancioglu.cryptoworkshopstarter.di

import com.atilsamancioglu.cryptoworkshopstarter.repository.CryptoDownload
import com.atilsamancioglu.cryptoworkshopstarter.repository.CryptoDownloadImpl
import com.atilsamancioglu.cryptoworkshopstarter.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitAPI() : CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectCryptoRepo(cryptoAPI : CryptoAPI) = CryptoDownloadImpl(cryptoAPI) as CryptoDownload
}