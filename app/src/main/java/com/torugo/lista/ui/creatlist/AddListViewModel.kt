package com.torugo.lista.ui.creatlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torugo.lista.data.model.Lista
import com.torugo.lista.data.repository.ListaRepository
import kotlinx.coroutines.launch

class AddListViewModel constructor(
    private val listaRepository: ListaRepository
) : ViewModel() {

    private val _listasData = MutableLiveData<List<Lista>>()
    val listasData: LiveData<List<Lista>>
        get() = _listasData

    fun init(){
        launchDataLoad { getAllLists() }
    }

    fun criarLista(lista: Lista){
        viewModelScope.launch {
            try {
                listaRepository.createList(lista = lista)
                _listasData.postValue(listaRepository.getAll())
            }catch (e: Exception){
                Log.d("Service Error:", e.toString())
            }
        }
    }

    fun deletarTodas(){
        viewModelScope.launch {
            try {
                listaRepository.deleteAll()
                _listasData.postValue(listaRepository.getAll())
            }catch (e: Exception){
                Log.d("Service Error:", e.toString())
            }
        }
    }

    fun deletarLista(lista: Lista){
        viewModelScope.launch {
            try {
                listaRepository.deleteLista(lista = lista)
                _listasData.postValue(listaRepository.getAll())
            }catch (e: Exception){
                Log.d("Service Error:", e.toString())
            }
        }
    }

    private suspend fun getAllLists(){
        try {
            _listasData.postValue(listaRepository.getAll())
        }catch (ex: Exception){
            Log.e("deuMerda", "getAllLists: Deu merda " + ex.message)
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
