package com.torugo.lista.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.torugo.lista.data.db.AppDatabase
import com.torugo.lista.data.repository.ItemDbDataSource
import com.torugo.lista.data.repository.ItemRepository
import com.torugo.lista.ui.additens.AddItemViewModel
import com.torugo.lista.ui.additens.AddItemViewModelFactory
import com.torugo.lista.ui.theme.ListaTheme

class ItensActivity : ComponentActivity() {
    lateinit var itemRepository: ItemRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemRepository = ItemDbDataSource(AppDatabase.getDatabase(applicationContext).itensDao())

        val viewModel: AddItemViewModel by viewModels {
            AddItemViewModelFactory(itemRepository)
        }
        setContent {
            ListaTheme {
//                ToolBarItens(viewModel)
            }
        }
    }
}

//@Composable
//fun ToolBarItens(viewModel: AddItemViewModel) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = stringResource(id = R.string.app_name)) },
//                backgroundColor = colorResource(id = R.color.purple_500),
//                contentColor = Color.White
//            )
//        }
//    ) {
//        Column(
//            verticalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .padding(it)
//        ) {
//            Box(
//                modifier = Modifier
//                    .height(500.dp)//acertar esse tamanho
//            ) {
//                ListarItens(viewModel)
//            }
//            Row(
//                modifier = Modifier
//                    .padding(12.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(12.dp),
//                    horizontalAlignment = Alignment.Start,
////                verticalArrangement = Arrangement.Bottom
//                ) {
//                    Text(text = "TOTAL:", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
//                    Text(text = "R$:299,00")
//                }
////                Fab(modifier = Modifier, texto = "Novo Item", botao = Color.Cyan, conteudo = Color.White) {
//
////                }
//            }
//        }
//    }
//}

//@Composable
//fun ListarItens(viewModel: AddItemViewModel) {
//    Column(
//        verticalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier
//            .padding(8.dp)
////                        .verticalScroll(ScrollState(1))
//    ) {
//        CardItens(cor = R.color.purple_200, viewModel)
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview2() {
//    ListaTheme {
//        ToolBarItens()
//    }
//}