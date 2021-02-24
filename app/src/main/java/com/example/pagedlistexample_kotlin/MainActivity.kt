package com.example.pagedlistexample_kotlin


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Layout
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Chronometer
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pagedlistexample_kotlin.API.Api
import com.example.pagedlistexample_kotlin.API.ParentNetwork
import com.example.pagedlistexample_kotlin.Adapter.ParentAdapter
import com.example.pagedlistexample_kotlin.Parent.Factory
import com.example.pagedlistexample_kotlin.Parent.ParentViewModel
import com.example.pagedlistexample_kotlin.Repository.Repository
import com.example.pagedlistexample_kotlin.databinding.ActivityMainBinding
import com.example.pagedlistexample_kotlin.parentt.Item
import com.example.pagedlistexample_kotlin.util.NetworkState
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var layoutSwitch: MenuItem
    private var timer: Long = 1000000
    private var chrono: Long? = null
    private lateinit var chronometer: Chronometer
    private lateinit var items: PagedList<Item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ParentAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var timerText: Any
    lateinit var repository: Repository

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("chrono", chronometer.base)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            //unused databinding
        val activityMainBinding =
            DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)

        //initializing my api service
        val apiService: Api = ParentNetwork.devbytes
        repository = Repository(apiService)
        //saved instance to keep data when rotating
        if (savedInstanceState != null) {
            chrono = savedInstanceState.getLong("chrono")
        }
        //recycle view instance
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = ParentAdapter(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        val parentViewModel = ViewModelProvider(this, Factory(repository))
            .get(ParentViewModel::class.java)

        parentViewModel.parentPagedList.observe(this, Observer {
            adapter.submitList(it)
        })

        val progress_bar_popular = findViewById<ProgressBar>(R.id.progress_bar_popular)
        parentViewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (parentViewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE

            if (!parentViewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        })
//        swipeRefreshLayout = findViewById(R.id.swipe_layout)
//        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_on_primary)
//        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
//            override fun onRefresh() {
//                getItems()
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        layoutSwitch = menu!!.findItem(R.id.counter)
        chronometer = layoutSwitch.actionView as Chronometer
        chrono?.let { chronometer.setBase(it) }
        chronometer.start()

/*        object : CountDownTimer(timer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val millis: Long = millisUntilFinished
                val hms: String =
                    (TimeUnit.MILLISECONDS.toHours(millis)).toString() + ":" + (TimeUnit.MILLISECONDS.toMinutes(
                        millis
                    ) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(
                        millis
                    ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
                layoutSwitch.setTitle(hms)
                timer = millis
            }

            override fun onFinish() {
                layoutSwitch.setTitle("done")
            }
        }.start()
        return true*/

        return true
    }

}

