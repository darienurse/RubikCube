package com.darienurse.rubixcube.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RubikCubeViewModelFactory(private val size: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RubikCubeViewModel::class.java)) {
            return RubikCubeViewModel(size) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}