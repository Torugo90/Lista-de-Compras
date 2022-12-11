package com.torugo.lista.ui.additens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torugo.lista.data.repository.ItemRepository

open class AddItemViewModelFactory(private val itemRepository: ItemRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)){
            return AddItemViewModel(itemRepository) as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel not Found")
        }
    }
}