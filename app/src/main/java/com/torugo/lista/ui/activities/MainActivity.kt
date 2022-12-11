package com.torugo.lista.ui.activities


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.torugo.lista.data.db.AppDatabase
import com.torugo.lista.data.repository.ItemDbDataSource
import com.torugo.lista.data.repository.ItemRepository
import com.torugo.lista.data.repository.ListaDbDataSource
import com.torugo.lista.data.repository.ListaRepository
import com.torugo.lista.ui.ItensLista
import com.torugo.lista.ui.ListaDeCompras
import com.torugo.lista.ui.additens.AddItemViewModel
import com.torugo.lista.ui.additens.AddItemViewModelFactory
import com.torugo.lista.ui.creatlist.AddListViewModel
import com.torugo.lista.ui.creatlist.AddListViewModelFactory
import com.torugo.lista.ui.theme.ListaTheme


class MainActivity : ComponentActivity() {
    lateinit var listaRepository: ListaRepository
    lateinit var itemRepository: ItemRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaRepository = ListaDbDataSource(AppDatabase.getDatabase(applicationContext).listaDao())
        itemRepository = ItemDbDataSource(AppDatabase.getDatabase(applicationContext).itensDao())

        val listaViewModel: AddListViewModel by viewModels {
            AddListViewModelFactory(listaRepository)
        }
        val itensViewModel: AddItemViewModel by viewModels {
            AddItemViewModelFactory(itemRepository)
        }
        setContent {
            ListaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    builder = {
                        composable("home"){
                            ListaDeCompras(listaViewModel, navController)
                        }
                        composable("argument/{idLista}",
                            arguments = listOf(navArgument("idLista") {type = NavType.IntType})
                            ){
                            ItensLista(itensViewModel, navController, it.arguments?.getInt("idLista"))
                        }
                    }
                )
            }
        }
    }
}