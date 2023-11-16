package com.atilsamancioglu.cryptoworkshopstarter.service

import com.atilsamancioglu.cryptoworkshopstarter.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {


    @GET("crypto.json")
    suspend fun getData() : Response<List<CryptoModel>>

}