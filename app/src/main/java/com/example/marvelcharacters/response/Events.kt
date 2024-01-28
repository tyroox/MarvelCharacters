package com.example.marvelcharacters.response

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<İtem>,
    val returned: Int
)