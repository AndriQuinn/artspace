package com.example.artspace.structure

fun String.roundUp(): String {
    return if (this.toInt() > 999) "${this[0]}k+" else this
}