import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var taskInput: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var addButton: Button
    private lateinit var taskListView: ListView
    private lateinit var clearCompletedButton: Button
    private lateinit var taskRepository: TaskRepository
    private lateinit var tasks: MutableList<Task>
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskInput = findViewById(R.id.taskInput)
        prioritySpinner = findViewById(R.id.prioritySpinner)
        addButton = findViewById(R.id.addButton)
        taskListView = findViewById(R.id.taskListView)
        clearCompletedButton = findViewById(R.id.clearCompletedButton)

        taskRepository = TaskRepository(this)
        tasks = taskRepository.getTasks()
        adapter = TaskAdapter(this, tasks)
        taskListView.adapter = adapter

        addButton.setOnClickListener { addTask() }
        clearCompletedButton.setOnClickListener { clearCompletedTasks() }

        taskListView.setOnItemClickListener { _, _, position, _ ->
            val task = tasks[position]
            task.completed = !task.completed
            taskRepository.saveTasks(tasks)
            adapter.notifyDataSetChanged()
        }
    }

    private fun addTask() {
        val text = taskInput.text.toString().trim()
        val priority = prioritySpinner.selectedItem.toString()

        if (text.isNotEmpty()) {
            val task = Task(System.currentTimeMillis(), text, false, priority)
            tasks.add(task)
            taskRepository.saveTasks(tasks)
            adapter.notifyDataSetChanged()
            taskInput.text.clear()
        }
    }

    private fun clearCompletedTasks() {
        tasks.removeAll { it.completed }
        taskRepository.saveTasks(tasks)
        adapter.notifyDataSetChanged()
    }
}
