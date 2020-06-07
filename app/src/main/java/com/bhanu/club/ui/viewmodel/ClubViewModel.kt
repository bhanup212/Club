package com.bhanu.club.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhanu.club.model.Club
import com.bhanu.club.network.ApiClient
import kotlinx.coroutines.launch
import java.net.UnknownHostException


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */
class ClubViewModel(private val apiClient: ApiClient):ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _clubs = MutableLiveData<ArrayList<Club>>()
    val clubs:LiveData<ArrayList<Club>> = _clubs

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg:LiveData<String> = _errorMsg

    fun getClubs(){

        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val res = apiClient.getClubs()
                _clubs.postValue(res)
                _isLoading.postValue(false)
            }catch (e:UnknownHostException){
                _isLoading.postValue(false)
                _errorMsg.postValue("No internet connection. Please try again")
            } catch (e: Exception) {
                Log.e("TAG","error: ${e.message}")
                _isLoading.postValue(false)

            }
        }
    }
}