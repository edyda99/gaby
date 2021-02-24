package com.example.pagedlistexample_kotlin.API

//import com.example.kotlingabywifiroom.Parent.Items
import com.example.pagedlistexample_kotlin.parentt.Parent
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

//import com.example.kotlingabywifiroom.Parent.Parent as Pare
//?q=created:>2021-02-17&sort=stars&order=desc&page=0
interface  Api {
    @GET("repositories")
      fun getTop(@Query("q") q: String,
                        @Query("sort") limit: String,
                        @Query("order") order: String,
                        @Query("page") page :String): Single<Parent>
    @GET("repositories")
    fun getParents(@Query("q") q: String,
                   @Query("sort") limit: String,
                   @Query("order") order: String): Single<Parent>
}

object ParentNetwork {
    const val FIRST_PAGE = 0
    const val POST_PER_PAGE = 20
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/search/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val devbytes :Api = retrofit.create(Api::class.java)

}