package com.example.pagedlistexample_kotlin.PagedListAdapter

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.pagedlistexample_kotlin.API.Api
import com.example.pagedlistexample_kotlin.parentt.Item
import io.reactivex.disposables.CompositeDisposable


class ParentDataSourceFactory(private val apiService : Api, private val compositeDisposable: CompositeDisposable) :
  DataSource.Factory<Long, Item>() {


  val mutableLiveData = MutableLiveData<ParentDataSource>()


  override fun create(): DataSource<Long, Item> {
   val parentDataSource= ParentDataSource(apiService,compositeDisposable)
    mutableLiveData.postValue(parentDataSource)
    return parentDataSource
  }
}