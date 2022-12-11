package com.torugo.lista.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torugo.lista.R
import com.torugo.lista.data.model.Lista
import com.torugo.lista.ui.creatlist.AddListViewModel
import java.text.SimpleDateFormat
import java.util.*

    @Composable
    fun ListaDeCompras(viewModel: AddListViewModel, navController: NavController) {
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
                modifier = Modifier.padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        CardLista(
                            R.color.purple_200,
                            icone = R.drawable.ic_launcher_foreground,
                            viewModel,
                            navController
                        )
                    }
                }
                Buttons(viewModel)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CardLista(
        cor: Int,
        icone: Int,
        viewModel: AddListViewModel,
        navController: NavController,
    ) {
        viewModel.init()
        val lazyListState = rememberLazyListState()
        val listas: List<Lista> by viewModel.listasData.observeAsState(emptyList())
        val context = LocalContext.current
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
                        onClick = {
                            navController.navigate("argument/${lista.id}")
                        },
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
    fun Buttons(viewModel: AddListViewModel){
        val openDialog = remember { mutableStateOf(false) }
        var value by remember { mutableStateOf(TextFieldValue("")) }
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Fab(
                modifier = Modifier,
                texto = "Nova Lista",
                backgroundColor = Color.Magenta,
                contentColor = Color.White,
                icon = Icons.Filled.Add
            ) { openDialog.value = true }

        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    value = TextFieldValue("")
                    openDialog.value = false
                },
                title = {
                    Text(text = "Nova Lista")
                },
                text = {
                    Column {
                        BasicTextField(
                            value = value,
                            onValueChange = { value = it },
                            decorationBox = { innerTextField ->
                                Row(
                                    Modifier
                                        .background(
                                            Color.LightGray,
                                            RoundedCornerShape(percent = 20)
                                        )
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {

                                    if (value.text.isEmpty()) {
                                        Text("Digite o nome da Lista...")
                                    }
                                    innerTextField()
                                }
                            },
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val date = Calendar.getInstance().time
                            val dateTimeFormat =
                                SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                            val novaLista = Lista(
                                0,
                                value.text,
                                "",
                                0.0,
                                dateTimeFormat.format(date).toString(),
                                ""
                            )
                            viewModel.criarLista(novaLista)

                            openDialog.value = false
                            value = TextFieldValue("")
                        }) {
                        Text("Criar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            value = TextFieldValue("")
                            openDialog.value = false
                        }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }