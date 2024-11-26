package com.example.app1;

// MainActivity.java


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAddTask;
    private Button buttonClearList;
    private ListView listViewTasks;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hämta referenser till UI-element
        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonClearList = findViewById(R.id.buttonClearList);
        listViewTasks = findViewById(R.id.listViewTasks);

        // Skapa en lista för uppgifter och koppla till listan
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listViewTasks.setAdapter(adapter);

        // Lägg till lyssnare för knappar och listvy
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        buttonClearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggleTaskCompletion(position);
            }
        });
    }

    // Lägg till en ny uppgift i listan
    private void addTask() {
        String task = editTextTask.getText().toString().trim();
        if (!task.isEmpty()) {
            taskList.add(task);
            adapter.notifyDataSetChanged();
            editTextTask.getText().clear();
        } else {
            Toast.makeText(this, "Ange en uppgift", Toast.LENGTH_SHORT).show();
        }
    }

    // Ta bort en uppgift från listan
    private void removeTask(int position) {
        if (position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Uppgift borttagen", Toast.LENGTH_SHORT).show();
        }
    }

    // Återställ hela listan
    private void clearList() {
        taskList.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Listan har återställts", Toast.LENGTH_SHORT).show();
    }

    // Ändra textfärgen när en uppgift klickas på
    private void toggleTaskCompletion(int position) {
        // Hämta den synliga delen av listvyn
        View view = listViewTasks.getChildAt(position - listViewTasks.getFirstVisiblePosition());
        // Hämta textvyn inuti listobjektet
        TextView textViewTask = view.findViewById(android.R.id.text1);

        // Om textfärgen är röd, ändra till grön, annars ändra till röd
        int currentColor = textViewTask.getCurrentTextColor();
        if (currentColor == Color.RED) {
            textViewTask.setTextColor(Color.GREEN);
        } else {
            textViewTask.setTextColor(Color.RED);
        }
    }
}
