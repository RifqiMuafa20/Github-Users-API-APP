package com.d121211063.mygithubusers.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.MainActivity
import com.d121211063.mygithubusers.data.history.History

class HistoryViewModel : ViewModel() {

    private val _listHistory = MutableLiveData<List<History>>()
    val listHistory: LiveData<List<History>> = _listHistory

    init {
        findHistory()
    }

    fun findHistory(){
        _listHistory.value = MainActivity.historyList
    }
}