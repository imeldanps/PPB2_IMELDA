package com.example.imeldaproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imeldaproject.adapter.TodoAdapter
import com.example.imeldaproject.databinding.ActivityTextactivityBinding
import com.example.imeldaproject.usecase.TodoUseCase
import kotlinx.coroutines.launch

class Textactivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextactivityBinding
    private lateinit var todoUseCase: TodoUseCase
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTextactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todoUseCase = TodoUseCase()
        setupRecyclerView()
        initializeData()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(mutableListOf())
        binding.container.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@Textactivity)
        }
    }

    private fun initializeData() {
        lifecycleScope.launch {
            binding.container.visibility = View.GONE
            binding.uiLoading.visibility = View.VISIBLE

            try {
                var todoList = todoUseCase.getTodo()
                binding.uiLoading.visibility = View.GONE
                binding.container.visibility = View.VISIBLE
                todoAdapter.updateData(todoList)
            } catch (e: Exception) {
                Toast.makeText(this@Textactivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}