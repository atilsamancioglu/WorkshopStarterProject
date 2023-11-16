package com.atilsamancioglu.cryptoworkshopstarter.data.service

import com.atilsamancioglu.cryptoworkshopstarter.domain.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {


    @GET("crypto.json")
    suspend fun getData() : Response<List<CryptoModel>>

}