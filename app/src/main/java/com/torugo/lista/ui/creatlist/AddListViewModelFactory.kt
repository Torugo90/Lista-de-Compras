package com.torugo.lista.ui.creatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torugo.lista.data.repository.ListaRepository

open class AddListViewModelFactory(private val listaRepository: ListaRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddListViewModel::class.java)){
           return AddListViewModel(listaRepository) as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel not Found")
        }
    }
}
