package com.torugo.lista.ui.additens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torugo.lista.data.model.Item
import com.torugo.lista.data.repository.ItemRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddItemViewModel constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _itensData = MutableLiveData<List<Item>>()
    val itensData: LiveData<List<Item>>
        get() = _itensData

    fun init(idLista: Long){
        launchDataLoad { getAllItens(idLista) }
    }

    fun addItem(item: Item, idLista: Long){
        viewModelScope.launch {
            try {
                itemRepository.addItem(item = item)
                _itensData.postValue(itemRepository.getAll(idLista))
            }catch (e: Exception){'-'
                Log.d("Service Error:", e.toString());
            }
        }
    }

    fun total(idLista: Long): Number = runBlocking{
            try {
                itemRepository.total(idLista)
            }catch (e: Exception){
                Log.d("Service Error:", e.toString())
            }
    }

    fun deletarTodos(idLista: Long){
        viewModelScope.launch {
            try {
                itemRepository.deleteAll(idLista)
                _itensData.postValue(itemRepository.getAll(idLista))
            }catch (e: Exception){
                Log.d("Service Error:", e.toString())
            }
        }
    }

    fun deletarItem(item: Item, idLista: Long){
        viewModelScope.launch {
            try {
                itemRepository.deleteItem(item = item)
                _itensData.postValue(itemRepository.getAll(idLista))
            }catch (e: Exception){
                Log.d("Service Error:", e.toString())
            }
        }
    }

    private suspend fun getAllItens(idLista: Long){
        try {
            _itensData.postValue(itemRepository.getAll(idLista))
        }catch (ex: Exception){
            Log.e("deuMerda", "getAllItens: Deu merda " + ex.message)
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit){
        viewModelScope.launch {
            try {
                block()
            }catch (ex: Exception){
                Log.e("deuMerda", "launchDataLoad: Deu merda " + ex.message)
            }
        }
    }
}