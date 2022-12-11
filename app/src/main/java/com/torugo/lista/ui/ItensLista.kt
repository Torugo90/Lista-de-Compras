package com.torugo.lista.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torugo.lista.R
import com.torugo.lista.data.model.Item
import com.torugo.lista.ui.additens.AddItemViewModel

@Composable
fun ItensLista(viewModel: AddItemViewModel, navController: NavController, idLista: Int? ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = colorResource(id = R.color.purple_500),
                contentColor = Color.White
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .height(500.dp)//acertar esse tamanho
            ) {
//                ListarItens(viewModel)
            }
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp),
                    horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = "TOTAL:", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Text(text = "R$:${idLista}")
                }
//                Fab(modifier = Modifier, texto = "Novo Item", botao = Color.Cyan, conteudo = Color.White) {

//                }
            }
        }
    }
}

@Composable
fun ListarItens(viewModel: AddItemViewModel) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
//                        .verticalScroll(ScrollState(1))
    ) {
//        CardItens(cor = R.color.purple_200, viewModel)
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
    val itens: List<Item> by viewModel.itensData.observeAsState(emptyList())
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
