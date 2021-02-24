package com.example.pagedlistexample_kotlin.Activity2

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pagedlistexample_kotlin.parentt.Item
import com.example.pagedlistexample_kotlin.R
import com.example.pagedlistexample_kotlin.databinding.ActivitySingleSelectBinding

public class SingleSelectActivity : AppCompatActivity() {
    companion object {
        const val Extra_ID = "com.example.kotlingabywifiroom.Activity2.EXTRA_ID"
        const val Extra_FULL_NAME = "com.example.kotlingabywifiroom.Activity2.EXTRA_FULL_NAME"
        const val Extra_NAME = "com.example.kotlingabywifiroom.Activity2.EXTRA_NAME"
    }
    private lateinit var item : Item
    private lateinit var activityMovieBinding: ActivitySingleSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_select)
        activityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_select)
        if(intent.hasExtra("item")){
            item = intent.getParcelableExtra("item")!!
        }


        Log.d("Main Thread", Thread.currentThread().name)
        val idView = findViewById<TextView>(R.id.id2)
        val nameView = findViewById<TextView>(R.id.node_id2)
        val full_name = findViewById<TextView>(R.id.pri2)
        idView.setText("Message from Second Activtiy: " + item.id.toString())
        nameView.setText("Message from Second Activtiy: " + item.name)
        full_name.setText("Message from Second Activtiy: " + item.full_name)
    }
}