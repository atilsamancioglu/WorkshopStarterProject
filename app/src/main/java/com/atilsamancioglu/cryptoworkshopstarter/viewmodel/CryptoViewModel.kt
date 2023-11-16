package com.atilsamancioglu.cryptoworkshopstarter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atilsamancioglu.cryptoworkshopstarter.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.repository.CryptoDownload
import com.atilsamancioglu.cryptoworkshopstarter.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.util.Resource
import com.atilsamancioglu.cryptoworkshopstarter.view.RecyclerViewAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor (val cryptoDownloadRepository : CryptoDownload) : ViewModel() {

    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()

    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "Error!",true)
    }
    fun loadData() {
        cryptoLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("",false)
                    cryptoList.value = resource
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}