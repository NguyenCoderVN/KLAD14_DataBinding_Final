package com.nguyencodervn.klad12_savedstatehandle

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameAndroidViewModelWithParaFactory(
    private val start: Int,
    private val app: Application
) : ViewModelProvider.AndroidViewModelFactory(app) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(
                GameAndroidViewModelWithPara::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return GameAndroidViewModelWithPara(start, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}