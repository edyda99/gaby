package com.example.pagedlistexample_kotlin.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagedlistexample_kotlin.API.Api
import com.example.pagedlistexample_kotlin.API.ParentNetwork.POST_PER_PAGE
import com.example.pagedlistexample_kotlin.PagedListAdapter.ParentDataSource
import com.example.pagedlistexample_kotlin.PagedListAdapter.ParentDataSourceFactory
import com.example.pagedlistexample_kotlin.parentt.Item
import com.example.pagedlistexample_kotlin.util.NetworkState
import io.reactivex.disposables.CompositeDisposable
import retrofit2.*
import java.util.*

//private val database: ParentDatabase
class Repository(private val apiService : Api) {

    //    private var mpDao: PDao? = null
    lateinit var itemsPagedList: LiveData<PagedList<Item>>
    lateinit var parentDataSourceFactory: ParentDataSourceFactory
    private val TAG: String = Repository::class.java.getSimpleName()


    fun fetchLiveParentPagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Item>> {
        parentDataSourceFactory = ParentDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        itemsPagedList = LivePagedListBuilder(parentDataSourceFactory, config).build()

        return itemsPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<ParentDataSource, NetworkState>(
            parentDataSourceFactory.mutableLiveData, ParentDataSource::networkState)
    }
}



/*    fun getHeroes(): LiveData<Resource<Parentt>> = liveData(Dispatchers.IO) {
        Log.d(TAG, "taking results")
        emit(Resource.loading(data = null))
        try {
                emit(Resource.success(ParentNetwork.devbytes.getTop("created:>2021-02-17", "stars", "desc", "0")))
            } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }*/
/*

}
fun getHeroes(): LiveData<Items> {
    val mutableLiveData: MutableLiveData<Items> = MutableLiveData<Items>()
        ParentNetwork.devbytes.getTop("created:>2021-02-17", "stars", "desc", "0")
                    "created:>2019-11-01", "stars", "desc", "0"
            ?.enqueue(object : Callback<Items> {
                override fun onFailure(call: Call<Items>?, t: Throwable) {
                    Log.d(TAG, "FAILURE$t")
                }

                override fun onResponse(
                    call: Call<Items>?,
                    response: Response<Items>
                ) {
                    Log.d(TAG, "on Success")

                    mutableLiveData.setValue(response.body())


                }

            })



    return mutableLiveData
}
    fun insert(parent: Parentt?) {
        for (p in parent?.items!!) {
                mpDao?.insert(p)
            }
        }
    suspend fun refreshVideos() {
            val parents = ParentNetwork.devbytes.getTop("created:>2019-11-01","stars","desc","0")
            database.mparentDao.insertAll(parents)  }

*/


