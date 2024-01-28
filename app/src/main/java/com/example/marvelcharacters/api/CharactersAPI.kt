package com.example.marvelcharacters.api

import com.example.marvelcharacters.response.CharactersResponse
import com.example.marvelcharacters.util.Constants.Companion.API_KEY
import com.example.marvelcharacters.util.Constants.Companion.hash
import com.example.marvelcharacters.util.Constants.Companion.timeStamp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersAPI {
    @GET("/v1/public/characters")
    fun getAllCharacters(
        @Query("apikey")apikey:String = API_KEY,
        @Query("ts")ts:String= timeStamp,
        @Query("hash")hash:String= hash(),
        @Query("offset")offset:String
    ): Call<CharactersResponse>

    @GET("/v1/public/characters")
    fun getAllSearchedCharacters(
        @Query("apikey")apikey:String = API_KEY,
        @Query("ts")ts:String = timeStamp,
        @Query("hash")hash:String= hash(),
        @Query("nameStartsWith")search:String
    ): Call<CharactersResponse>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacterById(
        @Path("characterId")characterId:String,
        @Query("apikey")apikey:String = API_KEY,
        @Query("ts")ts:String = timeStamp,
        @Query("hash")hash:String= hash(),
    ): Call<CharactersResponse>
}