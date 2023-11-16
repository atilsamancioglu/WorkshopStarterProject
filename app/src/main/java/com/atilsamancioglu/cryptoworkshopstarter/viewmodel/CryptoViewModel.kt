package com.atilsamancioglu.cryptoworkshopstarter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atilsamancioglu.cryptoworkshopstarter.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.view.RecyclerViewAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel : ViewModel() {

    val cryptoList = MutableLiveData<List<CryptoModel>>()
    val cryptoError = MutableLiveData<Boolean>()
    val cryptoLoading = MutableLiveData<Boolean>()

    private val BASE_URL = "https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/"
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        cryptoError.value = true
    }
    fun loadData() {
        cryptoLoading.value = true
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)


        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        cryptoLoading.value = false
                        cryptoError.value = false
                        cryptoList.value = it
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}