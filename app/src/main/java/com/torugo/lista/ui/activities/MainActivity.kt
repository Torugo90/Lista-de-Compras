package com.torugo.lista.ui.activities


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.torugo.lista.R
import com.torugo.lista.data.db.AppDatabase
import com.torugo.lista.data.model.Lista
import com.torugo.lista.data.repository.ListaDbDataSource
import com.torugo.lista.data.repository.ListaRepository
import com.torugo.lista.ui.CardLista
import com.torugo.lista.ui.Fab
import com.torugo.lista.ui.creatlist.AddListViewModel
import com.torugo.lista.ui.creatlist.AddListViewModelFactory
import com.torugo.lista.ui.theme.ListaTheme
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity() {
    lateinit var listaRepository: ListaRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    listaRepository = ListaDbDataSource(AppDatabase.getDatabase(applicationContext).listaDao())

    val viewModel: AddListViewModel by viewModels {
        AddListViewModelFactory(listaRepository)
    }
        setContent {
            ListaTheme {
                ToolBar(viewModel)
            }
        }
    }
}

@Composable
fun ToolBar(viewModel: AddListViewModel) {
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
                Listas(viewModel)
            }
           Buttons(viewModel)
        }
    }
}

@Composable
fun Listas(viewModel: AddListViewModel) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
    ) {
        CardLista(
            R.color.purple_200,
            icone = R.drawable.ic_launcher_foreground,
            viewModel,
//                        onClick = { mToast(context = context, "teste") }
        )
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

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ListaTheme {
//        ToolBar()
//    }
//}