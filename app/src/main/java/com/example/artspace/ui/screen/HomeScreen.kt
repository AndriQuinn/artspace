package com.example.artspace.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.artspace.R
import com.example.artspace.structure.ContentNode
import com.example.artspace.structure.InteractionType
import com.example.artspace.ui.components.ArtCardInfo
import com.example.artspace.ui.components.ArtSpaceHeader
import com.example.artspace.ui.components.InteractionButton
import com.example.artspace.ui.theme.ArtSpaceTheme
import org.json.JSONArray
import java.io.File

@Composable
fun ArtSpaceHomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ArtSpaceHeader()
        AddContentButton(
            toAddContent = {
                destination ->
                    navController.navigate(destination)
            }
        )
        ArtSpaceContent()
    }
}

@Composable
fun AddContentButton(
    toAddContent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Add content button
    Row (
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {toAddContent("postImage")}, // Go to post content screen
            colors = buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(0.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.post_something_txt),
                fontWeight = FontWeight.Light
            )
            Spacer(Modifier.width(10.dp))
            Image(
                painter = painterResource(R.drawable.photo),
                contentDescription = stringResource(R.string.photo_icon2_txt),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ArtSpaceContent(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .padding(vertical = 30.dp)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        
        // Get the json file content, return empty json array if file dont exist yet
        val contentFile = if (File(context.filesDir,"contents.json").exists()) {
            JSONArray(File(context.filesDir,"contents.json").readText())
        } else { JSONArray() }

        val contents = remember { mutableStateListOf<ContentNode>()}
        val contentList = mutableListOf<ContentNode>()

        // Post all content that json file has to ContentNode - skip if empty
        if (contentFile.length()>0) {
            for (i in 0 until contentFile.length()) {
                val jsonObj = contentFile.getJSONObject(i)

                contentList.add(
                    ContentNode(
                        imageId = jsonObj["imageId"].toString(),
                        imageAuthor = jsonObj["imageAuthor"] as String,
                        imageDate = jsonObj["imageDate"] as String,
                        imageTitle = jsonObj["imageTitle"] as String
                    )
                )
            }
        }

        contents.addAll(contentList)

        // Post all Content Node has
        for (postContent in contents) {
            ArtCard(
                imageId = postContent.imageId,
                imageTitle = postContent.imageTitle,
                imageAuthor = postContent.imageAuthor,
                imageDate = postContent.imageDate
            )
        }
    }
}

@Composable
fun ArtCard(
    modifier: Modifier = Modifier,
    imageId: String,
    imageAuthor: String,
    imageDate: String,
    imageTitle: String
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
    ) {
        //Image content
        Image(
            painter = rememberAsyncImagePainter(imageId.toUri()),
            contentDescription = imageTitle,
            modifier = Modifier.aspectRatio(0.6f),
            contentScale = ContentScale.FillWidth

        )
        Spacer(Modifier.height(10.dp))
        // Art card infos
        ArtCardInfo(
            imageAuthor = imageAuthor,
            imageDate = imageDate,
            imageTitle = imageTitle,
        )
        Spacer(Modifier.height(10.dp))
        // Interactions such as Like, Comment, Share
        InteractionRow()
        HorizontalDivider (
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 20.dp)
        )
    }
}

@Composable
fun InteractionRow(modifier: Modifier = Modifier) {
    Row (
        modifier = modifier.fillMaxWidth()
    ) {
        InteractionButton(
            type = InteractionType.LIKE,
            modifier = Modifier.weight(1f)
        )
        InteractionButton(
            type = InteractionType.COMMENT,
            modifier = Modifier.weight(1f)
        )
        InteractionButton(
            type = InteractionType.SHARE,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Home Screen Window"
)
@Composable
fun HomeScreenPreview() {
    ArtSpaceTheme {
        val navController = rememberNavController()
        ArtSpaceHomeScreen(navController)
    }
}