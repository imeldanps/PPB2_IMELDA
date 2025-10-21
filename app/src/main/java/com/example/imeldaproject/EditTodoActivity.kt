package com.example.imeldaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.imeldaproject.databinding.ActivityEditTodoBinding
import com.example.imeldaproject.entity.Todo
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
        registerEvents()
    }

    fun loadTodo() {
        lifecycleScope.launch {
            val todo = todoUseCase.getTodo(todoItemId)
            if (todo == null) {
                back()
            }

            binding.title.setText(todo?.title)
            binding.description.setText(todo?.description)
        }
    }

    fun back() {
        val intent = Intent(this@EditTodoActivity, Textactivity::class.java)
        startActivity(intent)
        finish()
    }

    fun registerEvents() {
        binding.tombolEdit.setOnClickListener {
            lifecycleScope.launch {
                val title = binding.title.text.toString()
                val description = binding.description.text.toString()
                val payload = Todo(
                    id = todoItemId,
                    title = title,
                    description = description,
                )

                try {
                    todoUseCase.updateTodo(payload)
                    displayMessage("Berhasil memperbarui data")
                    back()
                } catch (exc: Exception) {
                    displayMessage("Gagal memperbarui data : ${exc.message}")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadTodo()
    }

    fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}