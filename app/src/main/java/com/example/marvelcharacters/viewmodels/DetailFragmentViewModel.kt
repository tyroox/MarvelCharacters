package com.example.marvelcharacters.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelcharacters.api.RetrofitInstance
import com.example.marvelcharacters.model.Character
import com.example.marvelcharacters.response.CharactersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragmentViewModel(
    private val id:Int
): ViewModel() {
    var character = Character()
    var liveCharacter = MutableLiveData<Character>()
    suspend fun getCharacter(){
        withContext(Dispatchers.Main){
            RetrofitInstance.api.getCharacterById(characterId = id).enqueue(object: Callback<CharactersResponse>{
                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
                    response.body()!!.data.results.map {
                        character = it.newCharacter()
                        liveCharacter.postValue(character)
                    }
                    Log.d("model", "onCreateView: $character")
                }

                override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.stackTrace}")
                }
            })
        }
    }
}