package com.example.fetchrewards.ui.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewards.domain.ItemEntity
import com.example.fetchrewards.repository.Repository
import com.example.fetchrewards.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _items = MutableLiveData<Resources<List<ItemEntity>>>()
    val items: LiveData<Resources<List<ItemEntity>>> = _items

    init {
        getItems()
    }

    private fun getItems() {
        _items.value = Resources.Loading()
        viewModelScope.launch {
            delay(2000L) // Simulating Loading, just to see the ProgressBar in the UI
            _items.value = repository.getItem()
        }
    }
}