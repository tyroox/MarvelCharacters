package com.example.marvelcharacters.response

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<İtemXXX>,
    val returned: Int
)