package com.example.pagedlistexample_kotlin.Parent
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagedlistexample_kotlin.API.ParentNetwork
import com.example.pagedlistexample_kotlin.PagedListAdapter.ParentDataSource
import com.example.pagedlistexample_kotlin.PagedListAdapter.ParentDataSourceFactory
import com.example.pagedlistexample_kotlin.Repository.Repository
import com.example.pagedlistexample_kotlin.parentt.Item
import com.example.pagedlistexample_kotlin.util.NetworkState
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ParentViewModel(private val repository : Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  parentPagedList : LiveData<PagedList<Item>> by lazy {
        Log.d("ViewModel","parentPagedList")
        repository.fetchLiveParentPagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        repository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return parentPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    }

    class Factory(val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ParentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ParentViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
