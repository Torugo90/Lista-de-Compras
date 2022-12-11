package com.torugo.lista.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.torugo.lista.data.model.Item
import com.torugo.lista.data.model.Lista
import com.torugo.lista.ui.additens.AddItemViewModel
import com.torugo.lista.ui.creatlist.AddListViewModel

@Composable
fun CardLista(
    cor: Int,
    icone: Int,
    viewModel: AddListViewModel,
    onClick: () -> Unit = {},
) {
    viewModel.init()
    val lazyListState = rememberLazyListState()
    val listas: List<Lista> by viewModel.listasData.observeAsState(emptyList())
//    val context = LocalContext.current
    if (listas.isNotEmpty()) {
        LazyColumn(
            state = lazyListState
        ) {
            itemsIndexed(items = listas, key = { _, listItem ->
                listItem.id
            }) { _, lista ->
                println(lista)
                val checkedState = remember { mutableStateOf(false) }
                var exibir by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp, 4.dp, 8.dp, 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = colorResource(id = cor)
                ) {
                    Column(
                        modifier = Modifier.padding(6.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row {
                            IconImage(iconLightId = icone)
                            Column(//nome da lista e resumo da lista
                                modifier = Modifier.padding(6.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = lista.nome,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if (lista.texto.isEmpty()){
                                    Text(text = "Lista vazia")
                                }else{
                                    Text(text = lista.texto)
                                }
                            }
                        }
                    }

                    Column(//finalizado e checkbox
                        modifier = Modifier.padding(6.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.height(0.dp))
                        Text(
                            text = "Finalizado", fontSize = 12.sp,
//                                color = Color.Gray
                        )
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                exibir = !exibir
                            }
                        )
                        AnimatedVisibility(exibir) {
                            Fab(
                                modifier = Modifier,
                                contentColor = Color.Black,
                                icon = Icons.Filled.Delete,
                                backgroundColor = Color.White,
                                clickAction = {viewModel.deletarLista(lista)}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardItens(
    cor: Int,
    viewModel: AddItemViewModel,
    onClick: () -> Unit = {}
) {
    viewModel.init(13)
    val lazyListState = rememberLazyListState()
    val itens: List<Item> by viewModel.ItensData.observeAsState(emptyList())
    if (itens.isNotEmpty()) {
        LazyColumn(
            state = lazyListState
        ) {
            itemsIndexed(items = itens, key = { _, listItem ->
                listItem.id
            }) { _, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp, 4.dp, 8.dp, 4.dp)
                        .clickable { onClick() },
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = colorResource(id = cor)
                ) {
                    Column(//nome da lista e resumo da lista
                        modifier = Modifier.padding(6.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = item.nome)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = item.descricao)
                    }
                    Column(//finalizado e checkbox
                        modifier = Modifier.padding(6.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.height(0.dp))
                        Text(
                            text = "valor", fontSize = 14.sp,
    //                color = Color.Gray
                        )
                        Text(text = String.format("%.2f", item.valor))
                    }
                }
            }
        }
    }
}

//@Composable
//fun CardGrid(
//    cor: Int,
//    data: List<Itens>
//) { //tentativa de um gridLayout... ainda não sei pq não funciona
//
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(128.dp),
//        contentPadding = PaddingValues(8.dp),
//        content = {
//            items(data.size) { index ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(4.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    backgroundColor = colorResource(id = cor)
//                ) {
//                    Text(text = data[index].nome)
//                }
//            }
//        }
//    )
//}