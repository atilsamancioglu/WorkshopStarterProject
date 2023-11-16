package com.atilsamancioglu.cryptoworkshopstarter.domain.repository

import com.atilsamancioglu.cryptoworkshopstarter.domain.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.util.Resource
import retrofit2.Response

interface CryptoDownload {
    suspend fun downloadCryptos() : Resource<List<CryptoModel>>
}