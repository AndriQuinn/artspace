package com.example.artspace.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.artspace.R
import com.example.artspace.structure.addContentFile
import com.example.artspace.structure.copyImage
import com.example.artspace.ui.components.ArtSpaceHeader
import com.example.artspace.ui.theme.ArtSpaceTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ArtSpaceAddContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    var imageUri by remember { mutableStateOf("") } // Image uri
    var inputValue by remember { mutableStateOf("") } // Text input
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ArtSpaceHeader()
        AddContentNavBar(
            backFunction = {
            destination -> navController.navigate(destination)
        },
            addContentFile = {
                // Add the content
                addContentFile(
                    context = context,
                    imageId = imageUri,
                    imageAuthor = context.getString(R.string.bakunawa_txt),
                    imageDate = formatter.format(Date()),
                    imageTitle = inputValue
                )
                navController.navigate("home") // Return to home screen after post
            })
        InputTitleField(
            inputValue = inputValue,
            onValueChanged = {
                input -> inputValue = input
            }
        )
        GetImage(
            returnUri = {
                uri -> imageUri = uri // Get the image uri from image picker
            }
        )
    }
}

@Composable
fun AddContentNavBar(
    backFunction: (String) -> Unit,
    addContentFile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        // Back button - return to home screen
        Button (
            colors = buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(0.dp),
            onClick = { backFunction("home") }
        ) {
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = stringResource(R.string.back_icon_txt),
                modifier = Modifier.size(25.dp)
            )
        }
        // Post the content -> write to json
        Button (
            colors = buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(0.dp),
            onClick = {
                addContentFile()
            }
        ) {
            Text (
                text = stringResource(R.string.post_txt),
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF8154EF)
            )
        }
    }
}

@Composable
fun InputTitleField(
    modifier: Modifier = Modifier,
    inputValue: String,
    onValueChanged: (String) -> Unit
) {
    TextField (
        value = inputValue,
        onValueChange = {onValueChanged(it)},
        label = { Text(stringResource(R.string.about_the_picture_txt)) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        singleLine = false,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun GetImage(
    modifier: Modifier = Modifier,
    returnUri: (String) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Set the image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() // Set contract
    ) {
            uri ->
                val fileName = uri.toString() // Set the filename
                imageUri = uri
                val copiedPath = copyImage(
                    context = context,
                    imageUri = (uri as Uri), // Send the uri to copy the image
                    filename = fileName.substring((fileName.length-4),fileName.length) // Trim the file name
                )
                returnUri(copiedPath) // Return the path to parent composable
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Image picker
        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            colors = buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(0.dp),
            modifier = modifier.height(500.dp)
        ) {
            if (imageUri == null) { // Display button if no image is picked
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.photo),
                        contentDescription = stringResource(R.string.photo_icon_txt),
                        modifier = Modifier.size(18.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.pick_an_image_txt))
                }
            }
            // Preview the image picked
            else {
                imageUri.let {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = stringResource(R.string.selected_image_txt),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Add image window"
)
@Composable
fun AddImagePreview() {
    ArtSpaceTheme {
        val navController = rememberNavController()
        ArtSpaceAddContent(navController)
    }
}