package com.d121211063.mygithubusers.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211063.mygithubusers.MainActivity
import com.d121211063.mygithubusers.data.history.History

class HistoryViewModel : ViewModel() {

    private val _listHistory = MutableLiveData<List<History>>()
    val listHistory: LiveData<List<History>> = _listHistory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        findHistory()
    }

    fun findHistory() {
        _isLoading.value = true
        _listHistory.value = MainActivity.historyList

        if (_listHistory.value == null) {
            _isError.value = true
        } else {
            _isError.value = false
            _isLoading.value = false
        }
    }
}