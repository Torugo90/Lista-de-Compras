package com.torugo.lista.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.torugo.lista.R
import com.torugo.lista.ui.theme.ListaTheme


@Composable
fun BotaoUnico(
    modifier: Modifier, clickAction: () -> Unit, @StringRes textId: Int
) {
    Button(
        modifier = modifier, shape = MaterialTheme.shapes.small, onClick = clickAction
    ) {
        Text(text = stringResource(id = textId))
    }
}

@Composable
fun Fab(
    modifier: Modifier,
    texto: String? = null,
    backgroundColor: Color,
    icon: ImageVector,
    contentColor: Color,
    clickAction: () -> Unit,
) {
    if (!texto.isNullOrEmpty()) {
        ExtendedFloatingActionButton(
            modifier = modifier,
            contentColor = contentColor,
            backgroundColor = backgroundColor,
            text = { Text(text = texto) },
            onClick = clickAction,
            icon = { Icon(icon, "") }
        )
    } else {
        FloatingActionButton(
            modifier = modifier,
            contentColor = contentColor,
            backgroundColor = backgroundColor,
            onClick = clickAction,
        ) {
            Icon(icon, "")
        }
    }
}


@Composable
fun LinhaDeBotoes(
    modifier: Modifier, // single button modifier
    positiveAction: (() -> Unit)? = null,
    negativeAction: (() -> Unit)? = null,
    neutralAction: (() -> Unit)? = null,
    @StringRes positiveTextId: Int? = null,
    @StringRes negativeTextId: Int? = null,
    @StringRes neutralTextId: Int? = null,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
    ) {
        negativeAction?.let { clickAction ->
            negativeTextId?.let { textId ->
                BotaoUnico(modifier, clickAction, textId)
            }
        }
        if (negativeAction == null) Spacer(modifier = modifier)

        neutralAction?.let { clickAction ->
            neutralTextId?.let { textId ->
                BotaoUnico(modifier, clickAction, textId)
            }
        }
        if (neutralAction == null) Spacer(modifier = modifier)

        positiveAction?.let { clickAction ->
            positiveTextId?.let { textId ->
                BotaoUnico(modifier, clickAction, textId)
            }
        }
        if (positiveAction == null) Spacer(modifier = modifier)
    }
}

@Preview
@Composable
fun BotaoUnicoPreview() {
    ListaTheme(darkTheme = false) {
        Fab(
            modifier = Modifier,
//            texto = "teste",
            backgroundColor = Color.Magenta,
            icon = Icons.Filled.Add,
            contentColor = Color.Black
        ) {

        }
    }
}

@Preview
@Composable
fun DoisBotoesPreview() {
    ListaTheme(darkTheme = false) {
        LinhaDeBotoes(
            modifier = Modifier.widthIn(min = 100.dp, max = 120.dp),
            positiveAction = {},
            positiveTextId = R.string.app_name,
            neutralAction = {},
            neutralTextId = R.string.app_name,
            negativeAction = {},
            negativeTextId = R.string.app_name
        )
    }
}