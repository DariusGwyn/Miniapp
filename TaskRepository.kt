import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getTasks(): MutableList<Task> {
        val json = prefs.getString("tasks", null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<MutableList<Task>>() {}.type) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }

    fun saveTasks(tasks: List<Task>) {
        val json = gson.toJson(tasks)
        prefs.edit().putString("tasks", json).apply()
    }
}
