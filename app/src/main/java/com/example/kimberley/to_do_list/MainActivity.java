package com.example.kimberley.to_do_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> taskList;
    private ArrayAdapter<String> listAdapter;
    private ListView tasksListView;

    // Reads tasks saved in todo.txt file.
    //code source = https://guides.codepath.com/android/Basic-Todo-App-Tutorial
    private void readItems() {
        // Opens file directory
        File filesDirectory = getFilesDir();
        // Creates todo.txt
        File todoFile = new File(filesDirectory, "todo.txt");
        try {
            taskList = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            taskList = new ArrayList<String>();
        }
    }
    // Writes tasks to todo.txt file.
    //https://guides.codepath.com/android/Basic-Todo-App-Tutorial
    private void writeItems() {
        // Opens file directory
        File filesDirectory = getFilesDir();
        // Creates todo.txt file in the file directory.
        File todoFile = new File(filesDirectory, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksListView = (ListView) findViewById(R.id.listView);
        taskList = new ArrayList<String>();
        readItems();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList);
        tasksListView.setAdapter(listAdapter);

        tasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, int pos, long id) {
                taskList.remove(pos);
                listAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        }

    /*private void saveTasks(){
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    }

    @Override
    protected void onStop() {
        saveTasks();
        super.onStop();
    }*/

    public void addTask(View view){
        EditText taskText = (EditText) findViewById(R.id.taskText);
        String itemText = taskText.getText().toString();
        listAdapter.add(itemText);
        taskText.setText("");
        writeItems();
    }
}
