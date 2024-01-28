package com.example.marvelcharacters.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelcharacters.api.RetrofitInstance
import com.example.marvelcharacters.item.Item
import com.example.marvelcharacters.response.CharactersResponse
import com.example.marvelcharacters.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel (
    private val itemList: MutableLiveData<List<Item>> = MutableLiveData(),
    private var offset: String
): ViewModel() {
    suspend fun getData(){
        val nameList = mutableListOf<String>()
        val imageList = mutableListOf<String>()
        val seriesCountList = mutableListOf<String>()
        val idList = mutableListOf<String>()
        withContext(Dispatchers.Main) {
            RetrofitInstance.api.getAllCharacters(offset = offset).enqueue(object :
                Callback<CharactersResponse> {
                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
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

    fun updateOffset(): String{
        var x = offset.toInt()
        x += Constants.limit.toInt()
        offset = x.toString()
        return offset
    }
    fun getItemList(): LiveData<List<Item>> {
        return itemList
    }
}
