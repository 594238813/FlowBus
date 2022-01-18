package com.flowbuslib

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object ApplicationViewModelProvider:ViewModelStoreOwner {

    private val mAppViewModelStore:ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {

        return mAppViewModelStore
    }
}