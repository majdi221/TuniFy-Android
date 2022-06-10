package com.esprit.tunify.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esprit.tunify.model.Product
import com.esprit.tunify.network.RetroInstance
import com.esprit.tunify.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private var productListLiveData : MutableLiveData<Collection<Product>> = MutableLiveData()

    fun getProductListObserver(): MutableLiveData<Collection<Product>> {

        return productListLiveData
    }

    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            val response = retroInstance.getAllProducts()
            productListLiveData.postValue(response)
        }
    }
}