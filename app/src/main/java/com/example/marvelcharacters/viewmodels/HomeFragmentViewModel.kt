package com.example.marvelcharacters.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelcharacters.api.RetrofitInstance
import com.example.marvelcharacters.item.Item
import com.example.marvelcharacters.response.CharactersResponse
import com.example.marvelcharacters.util.Constants
import com.example.marvelcharacters.util.Enum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel (
    private var itemList: MutableLiveData<List<Item>> = MutableLiveData(),
    private var offset: Int

): ViewModel() {
    private var orderBy = "name"
    suspend fun getData(){
        val nameList = mutableListOf<String>()
        val imageList = mutableListOf<String>()
        val seriesCountList = mutableListOf<String>()
        val idList = mutableListOf<String>()
        withContext(Dispatchers.Main) {
            RetrofitInstance.api.getAllCharacters(offset = offset, orderBy = orderBy).enqueue(object :
                Callback<CharactersResponse> {
                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
                    Log.d("aaaa", "onResponse: $call $response")
                    val data = response.body()!!.data.results

                    for (i in data){
                        nameList.add(i.name)
                        imageList.add(i.thumbnail.path + "." + i.thumbnail.extension)
                        seriesCountList.add((i.series.available).toString())
                        idList.add((i.id).toString())
                    }
                    Log.d("TAG", "onResponse: $seriesCountList")

                    for (i in imageList.indices) {
                        imageList[i] = imageList[i].replace("http://", "https://")
                    }
                    val newItems = mutableListOf<Item>()
                    for (i in nameList.indices) {
                        newItems.add(Item(imageList[i], nameList[i], seriesCountList[i], idList[i]))
                    }
                    itemList.postValue(newItems)
                    Log.d("update", "onResponse: $nameList")
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

    fun getItemList(): LiveData<List<Item>> {
        return itemList
    }

    fun deleteItems(){
        itemList.value = mutableListOf()
        offset = 0
    }

    fun sortCharacterNames(): String{
        if (orderBy == Enum.NAME_ASC.str){
            orderBy = Enum.NAME_DESC.str
        }
        else if (orderBy == Enum.NAME_DESC.str){
            orderBy = Enum.NAME_ASC.str
        }
        return orderBy
    }
}
