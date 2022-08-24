package com.latihanrakamin.aplikasisederhana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.latihanrakamin.aplikasisederhana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}