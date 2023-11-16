package com.atilsamancioglu.cryptoworkshopstarter.data.repository

import com.atilsamancioglu.cryptoworkshopstarter.domain.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.data.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.domain.repository.CryptoDownload
import com.atilsamancioglu.cryptoworkshopstarter.domain.use_case.download_cryptos.DownloadCryptosUseCase
import com.atilsamancioglu.cryptoworkshopstarter.util.Resource
import retrofit2.Response
import javax.inject.Inject

class CryptoDownloadImpl @Inject constructor (val useCase: DownloadCryptosUseCase) : CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
        return useCase.executeDownloadCryptos()
    }
}