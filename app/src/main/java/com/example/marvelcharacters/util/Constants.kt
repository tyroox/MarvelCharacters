package com.example.marvelcharacters.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val API_KEY = "63093ffc4e8aa2b60cdb6c993e0a327d"
        private const val PRIVATE_KEY = "7f93c88f9df1b888c732b6c29611330d565f2578"
        const val limit = "20"
        const val detailsLimit = "100"
        val hash = hash()
        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1,md.digest(input.toByteArray())).toString(16).padStart(32,'0')
        }
    }
}