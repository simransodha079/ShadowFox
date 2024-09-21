package com.example.todo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private List<Task> tasks;
    private TaskAdapter adapter;
    private EditText taskInput;
    private Spinner prioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks, this);

        RecyclerView taskList = findViewById(R.id.task_list);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setAdapter(adapter);

        taskInput = findViewById(R.id.task_input);
        prioritySpinner = findViewById(R.id.priority_spinner);
        Button addTaskButton = findViewById(R.id.add_task_button);
        Button removeCompletedButton = findViewById(R.id.remove_completed_button);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_levels, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(spinnerAdapter);

        addTaskButton.setOnClickListener(v -> addTask());
        removeCompletedButton.setOnClickListener(v -> removeCompletedTasks());
    }

    private void addTask() {
        String title = taskInput.getText().toString().trim();
        int priority = prioritySpinner.getSelectedItemPosition() + 1;

        if (!title.isEmpty()) {
            tasks.add(new Task(title, priority));
            sortTasks();
            adapter.notifyDataSetChanged();
            taskInput.setText("");
        } else {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        }
    }

    private void sortTasks() {
        Collections.sort(tasks, (t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));
    }

    private void removeCompletedTasks() {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.isComplete()) {
                iterator.remove();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskClick(int position) {
        tasks.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, tasks.size());
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckBoxClick(int position, boolean isChecked) {
        Task task = tasks.get(position);
        task.setComplete(isChecked);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onEditClick(int position) {
        Task task = tasks.get(position);
        taskInput.setText(task.getTitle());
        prioritySpinner.setSelection(task.getPriority() - 1);
        tasks.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
