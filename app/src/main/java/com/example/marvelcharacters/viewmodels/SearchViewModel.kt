package com.example.marvelcharacters.viewmodels

import android.util.Log
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

class SearchViewModel (
    private var offset: Int
): ViewModel() {
    var character = Character()
    var characterList = mutableListOf<Character>()
    var liveCharacterList = MutableLiveData<List<Character>>()
    suspend fun search(search: String) {
        withContext(Dispatchers.Main) {
            RetrofitInstance.api.getAllSearchedCharacters(offset = offset, search = search)
                .enqueue(object : Callback<CharactersResponse> {
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

    fun deleteItems() {
        liveCharacterList.value = emptyList()
        characterList = mutableListOf()
    }

    fun updateOffset() {
        offset += Constants.limit.toInt()
    }
}

