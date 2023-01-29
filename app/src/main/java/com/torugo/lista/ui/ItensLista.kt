package com.torugo.lista.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torugo.lista.R
import com.torugo.lista.data.model.Item
import com.torugo.lista.ui.additens.AddItemViewModel

var total: Number = 0

@Composable
fun ItensLista(viewModel: AddItemViewModel, navController: NavController, idLista: Long?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item_list)) },
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
//                    .fillMaxSize()
                    .height(500.dp)//acertar esse tamanho
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(8.dp)
//                        .verticalScroll(ScrollState(1))
                ) {
                    CardItens(cor = R.color.purple_200, idLista, viewModel)
                }
            }
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
//                        .fillMaxHeight()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "TOTAL:", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
//                    Text(text = "R$:${total}")
                    Text(text = "R$:${viewModel.total(idLista!!)}")
                }
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    ButtonsItens(viewModel, idLista)
                }
            }
        }
    }
}


@Composable
fun CardItens(
    cor: Int,
    idLista: Long?,
    viewModel: AddItemViewModel,
    onClick: () -> Unit = {}
) {
    viewModel.init(idLista!!)
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

@Composable
fun ButtonsItens(viewModel: AddItemViewModel, idLista: Long?) {
    val openDialog = remember { mutableStateOf(false) }
    var nomeItem by remember { mutableStateOf(TextFieldValue("")) }
    var descricao by remember { mutableStateOf(TextFieldValue("")) }
    var valor by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier
    ) {
        Fab(
            modifier = Modifier,
            texto = "Novo Item",
            backgroundColor = Color.Magenta,
            contentColor = Color.White,
            icon = Icons.Filled.Add
        ) { openDialog.value = true }

    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                nomeItem = TextFieldValue("")
                descricao = TextFieldValue("")
                valor = TextFieldValue("")
                openDialog.value = false
            },
            title = {
                Text(text = "Novo Item")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = nomeItem,
                        label = { Text(text = "Nome do Item") },
                        onValueChange = {
                            nomeItem = it
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = descricao,
                        label = { Text(text = "Descrição do Item") },
                        onValueChange = {
                            descricao = it
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = valor,
                        label = { Text(text = "Valor do Item") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            valor = it
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val novoItem = Item(
                            id = 0,
                            nome = nomeItem.text,
                            descricao = descricao.text,
                            valor = valor.text.toDouble(),
                            idLista = idLista!!,
                            estado = "",
                            categoria = ""
                        )
                        viewModel.addItem(novoItem, idLista)
//                        total = viewModel.total(idLista!!)
                        openDialog.value = false
                        nomeItem = TextFieldValue("")
                        descricao = TextFieldValue("")
                        valor = TextFieldValue("")
                    }) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        nomeItem = TextFieldValue("")
                        openDialog.value = false
                    }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview
@Composable
fun teste() {
    val idLista: Long? = 2
    val viewModel: AddItemViewModel

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
//                    .fillMaxSize()
                .height(500.dp)//acertar esse tamanho
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
//                        .verticalScroll(ScrollState(1))
            ) {
//                CardItens(cor = R.color.purple_200, idLista)
            }
        }
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
//                        .fillMaxHeight()
                    .padding(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "TOTAL:", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Text(text = "R$:15")
            }
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Fab(
                    modifier = Modifier,
                    texto = "Novo Item",
                    backgroundColor = Color.Magenta,
                    contentColor = Color.White,
                    icon = Icons.Filled.Add
                ) { }
            }
        }
    }
}