package com.example.youtubeapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.utils.NetworkHelper

class ViewModelFactory(private val networkHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YoutubeViewModel::class.java)) {
            return YoutubeViewModel(networkHelper) as T
        }
        throw Exception("Error")
    }
}