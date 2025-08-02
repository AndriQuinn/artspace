package com.example.artspace.structure

import android.content.Context
import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

fun addContentFile(
        context: Context,
        imageId: String,
        imageAuthor: String,
        imageDate: String,
        imageTitle: String
) {
    val contentFileDir = File(context.filesDir,"contents.json") // Create app directory to contents.json
    val contentFile: String = if (contentFileDir.exists()) {
       contentFileDir.readText() // Read the file if exist
    } else {
        contentFileDir.writeText("[]") // Create the file if not
        contentFileDir.readText()
    }

    val contentFileJson = JSONArray(contentFile) // Parse the text back to Json array
    val addedContent = JSONObject() // Create a Json object for content info
    addedContent.put("imageId",imageId)
    addedContent.put("imageAuthor",imageAuthor)
    addedContent.put("imageDate",imageDate)
    addedContent.put("imageTitle",imageTitle)
    contentFileJson.put(addedContent) // Add the object to Json array

    contentFileDir.writeText(contentFileJson.toString()) // Overwrites the Json file with new content
}

fun copyImage(context: Context, imageUri: Uri, filename: String): String {
    val imageInput = context.contentResolver.openInputStream(imageUri) // Open the byte stream of the image
    val outputDestination = File(context.filesDir,"$filename.jpg") // Set the destination for the stream
    val fileOutputDestination = FileOutputStream(outputDestination) // Points the byte stream to the file destination
    imageInput?.copyTo(fileOutputDestination) // Copy the image to destination
    fileOutputDestination.flush()
    imageInput?.close()
    fileOutputDestination.close()

    return outputDestination.toString() // Return the directory path ex. data/user... for File() object
}