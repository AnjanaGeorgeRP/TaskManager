package com.myapplicationdev.android.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvTasks;
    ArrayList<String> alTasks = new ArrayList<String>() ;
    ArrayAdapter<String> aaTasks;
    ArrayList<Task> tasks;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTasks = (ListView) findViewById(R.id.lvTasks);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        aaTasks = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alTasks);
        lvTasks.setAdapter(aaTasks);

        DBHelper db = new DBHelper(MainActivity.this);
        tasks = db.getAllTasks();
        for(int i =0;i<tasks.size();i++){
            alTasks.add(tasks.get(i).getId()+" " +tasks.get(i).getName()+"\n"+tasks.get(i).getDescription());
            aaTasks.notifyDataSetChanged();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(i, 9);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9){
            DBHelper db = new DBHelper(MainActivity.this);
            tasks.clear();
            alTasks.clear();
            tasks.addAll(db.getAllTasks());
            for(int i =0;i<tasks.size();i++){
                alTasks.add(tasks.get(i).getId()+" " +tasks.get(i).getName()+"\n"+tasks.get(i).getDescription());
                aaTasks.notifyDataSetChanged();
            }
        }
    }
}
