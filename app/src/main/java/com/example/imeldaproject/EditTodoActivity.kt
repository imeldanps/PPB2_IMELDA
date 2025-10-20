package com.example.imeldaproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.imeldaproject.databinding.ActivityEditTodoBinding
import com.example.imeldaproject.usecase.TodoUseCase
import kotlinx.coroutines.launch

class EditTodoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditTodoBinding
    private lateinit var todoUseCase: TodoUseCase
    private lateinit var todoItemId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todoItemId = intent.getStringExtra("todo_item_id").toString()
        todoUseCase = TodoUseCase()
    }

    fun loadTodo() {
        lifecycleScope.launch {
            val todo = todoUseCase.getTodo(todoItemId)
            if (todo == null) {
                val intent = Intent(this@EditTodoActivity, Textactivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.title.setText(todo?.title)
            binding.description.setText(todo?.description)
        }
    }

    override fun onStart() {
        super.onStart()
        loadTodo()
    }
}