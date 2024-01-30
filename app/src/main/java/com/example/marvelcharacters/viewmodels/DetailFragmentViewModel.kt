package com.example.marvelcharacters.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelcharacters.api.RetrofitInstance
import com.example.marvelcharacters.model.Character
import com.example.marvelcharacters.response.CharactersResponse
import com.example.marvelcharacters.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragmentViewModel(
    private val id:String
): ViewModel() {

    private val _character = MutableLiveData<Character>()
    val character: LiveData<Character> get() = _character
    suspend fun getCharacter(){
        withContext(Dispatchers.Main){
            RetrofitInstance.api.getCharacterById(characterId = id, limit = Constants.detailsLimit.toInt()).enqueue(object: Callback<CharactersResponse>{
                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
                    response.body()!!.data.results.map {
                        _character.postValue(it.newCharacter())
                    }
                }

                override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.stackTrace}")

                }
            })
        }
    }
}