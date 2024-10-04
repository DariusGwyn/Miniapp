import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    private lateinit var editTaskInput: EditText
    private lateinit var saveButton: Button
    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTaskInput = findViewById(R.id.editTaskInput)
        saveButton = findViewById(R.id.saveButton)

        task = intent.getSerializableExtra("TASK") as Task?
        task?.let {
            editTaskInput.setText(it.text)
        }

        saveButton.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        val newTaskText = editTaskInput.text.toString().trim()
        if (newTaskText.isNotEmpty()) {
            val updatedTask = task?.apply { text = newTaskText } ?: Task(System.currentTimeMillis(), newTaskText, false, "low")
            // Save to repository or return to previous activity with result
            // ...
        }
    }
}
