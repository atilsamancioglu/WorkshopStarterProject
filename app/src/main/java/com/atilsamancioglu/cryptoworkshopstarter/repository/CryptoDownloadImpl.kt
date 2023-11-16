package com.atilsamancioglu.cryptoworkshopstarter.repository

import com.atilsamancioglu.cryptoworkshopstarter.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.util.Resource
import javax.inject.Inject

class CryptoDownloadImpl @Inject constructor (val api : CryptoAPI) : CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
        return try {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Body boş geldi!",null)
            } else {
                Resource.error("Response successful değil!",null)
            }
        } catch (e: Exception) {
            Resource.error("Bütün olay patladı!",null)
        }
    }
}