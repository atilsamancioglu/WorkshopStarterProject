package com.atilsamancioglu.cryptoworkshopstarter.repository

import com.atilsamancioglu.cryptoworkshopstarter.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos() : Resource<List<CryptoModel>>
}