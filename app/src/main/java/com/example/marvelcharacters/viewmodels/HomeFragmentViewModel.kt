package com.example.marvelcharacters.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelcharacters.api.RetrofitInstance
import com.example.marvelcharacters.model.Character
import com.example.marvelcharacters.response.CharactersResponse
import com.example.marvelcharacters.util.Constants
import com.example.marvelcharacters.util.Enum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel (
    private var offset: Int
): ViewModel() {
    var character = Character()
    var characterList = mutableListOf<Character>()
    var liveCharacterList = MutableLiveData<List<Character>>()
    private var orderBy = Enum.NAME_ASC.str
    suspend fun getData(){
        withContext(Dispatchers.Main){
            RetrofitInstance.api.getAllCharacters(offset = offset, orderBy = orderBy).enqueue(object: Callback<CharactersResponse>{
                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
                    response.body()!!.data.results.map {
                        character = it.newCharacter()
                        characterList.add(character)
                        liveCharacterList.postValue(characterList)
                    }
                    Log.d("model", "onCreateView: $character")
                }

                override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.stackTrace}")
                }
            })
        }
    }

    fun updateOffset(){
        offset += Constants.limit.toInt()
    }

    fun deleteItems(){
        liveCharacterList.value = emptyList()
        characterList = mutableListOf()
        offset = 0
    }

    fun sortCharacterNames(){
        if (orderBy == Enum.NAME_ASC.str){
            orderBy = Enum.NAME_DESC.str
        }
        else if (orderBy == Enum.NAME_DESC.str){
            orderBy = Enum.NAME_ASC.str
        }
    }
}
