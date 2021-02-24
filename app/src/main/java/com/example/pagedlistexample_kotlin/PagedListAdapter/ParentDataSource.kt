package com.example.pagedlistexample_kotlin.PagedListAdapter

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.pagedlistexample_kotlin.API.Api
import com.example.pagedlistexample_kotlin.API.ParentNetwork
import com.example.pagedlistexample_kotlin.API.ParentNetwork.FIRST_PAGE
import com.example.pagedlistexample_kotlin.parentt.Item
import com.example.pagedlistexample_kotlin.parentt.Parent
import com.example.pagedlistexample_kotlin.util.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ParentDataSource(
    private val apiService: Api,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Long, Item>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private var page : Int = FIRST_PAGE

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Item>
    ) {
        Log.d("ParentDataSource", "LoadingInitial")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTop("created:>2021-02-17", "stars", "desc", "$page")
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.items!!, null, (page + 1).toLong())
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("ParentDataSource", it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Item>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Item>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getTop("created:>2021-02-17", "stars", "desc", "${params.key}")
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {

                            callback.onResult(it.items!!, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)

                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }
}