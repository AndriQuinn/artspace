package com.example.artspace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.R
import com.example.artspace.structure.DateConversion
import com.example.artspace.structure.InteractionType
import com.example.artspace.structure.roundUp

val dateStyle = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.W300
)

val titleStyle = TextStyle (
    fontSize = 20.sp
)

@Composable
fun InteractionButton(
    type: InteractionType,
    modifier: Modifier = Modifier
) {
    var isToggle by remember { mutableStateOf(false) }
    var numberOfInteraction by remember { mutableIntStateOf(999) }

    Button (
        modifier = modifier.fillMaxWidth(),
        colors = buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        onClick = {
            isToggle = !isToggle
            if (isToggle) {
                numberOfInteraction += 1
            } else { numberOfInteraction -= 1 }
        }
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(
                    if (isToggle) {
                        type.activeIcon
                    } else {type.inactiveIcon}
                ),
                contentDescription = type.description,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = numberOfInteraction.toString().roundUp(),
                fontWeight = FontWeight.Light,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ArtCardInfo(
    modifier: Modifier = Modifier,
    imageAuthor: String,
    imageDate: String,
    imageTitle: String
) {
    Column (
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        // Image author
        Text (
            text = imageAuthor
            )
        Spacer(Modifier.height(1.dp))
        // Image date
        Text (
            text = DateConversion.middleEndianToDateFormat(imageDate),
            style = dateStyle
        )
        Spacer(Modifier.height(15.dp))
        // Image title
        Text (
            text = imageTitle,
            style = titleStyle
        )
    }
}

@Composable
fun ArtSpaceHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color(0xFF8154EF))
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.spng),
            contentDescription = "ArtSpace logo",
        )
    }
}