package com.example.artspace.structure

import com.example.artspace.R


enum class InteractionType(
    val activeIcon: Int,
    val inactiveIcon: Int,
    val description: String,

) {
    LIKE(
        activeIcon = R.drawable.fillheart,
        inactiveIcon = R.drawable.strokeheart,
        description = "Like icon",
    ),

    COMMENT(
        activeIcon = R.drawable.comment,
        inactiveIcon = R.drawable.comment,
        description = "Comment icon"
    ),

    SHARE(
        activeIcon = R.drawable.share,
        inactiveIcon = R.drawable.share,
        description = "Share icon"
    ),
}