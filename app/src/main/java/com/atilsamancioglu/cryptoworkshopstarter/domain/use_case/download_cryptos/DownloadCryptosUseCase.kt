package com.atilsamancioglu.cryptoworkshopstarter.domain.use_case.download_cryptos

import com.atilsamancioglu.cryptoworkshopstarter.data.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.domain.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.domain.repository.CryptoDownload
import com.atilsamancioglu.cryptoworkshopstarter.util.Resource
import javax.inject.Inject

class DownloadCryptosUseCase @Inject constructor (val api : CryptoAPI) {
    suspend fun executeDownloadCryptos(): Resource<List<CryptoModel>> {
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