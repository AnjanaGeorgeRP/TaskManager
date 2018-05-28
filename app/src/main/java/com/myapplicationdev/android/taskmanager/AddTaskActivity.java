package com.myapplicationdev.android.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDesc, etTime;
    Button buttonInsert, btnCancel;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etTime = findViewById(R.id.etTime);

        buttonInsert = findViewById(R.id.buttonInsert);
        btnCancel = findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String timeS = etTime.getText().toString().trim();
                int time = Integer.parseInt(timeS);

                Task task = new Task(name,desc,time);

                //launch notification here
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, time);

                Intent intent = new Intent(AddTaskActivity.this,
                        ScheduledNotificationReceiver.class);
                intent.putExtra("task",name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddTaskActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                //Database
                DBHelper db = new DBHelper(AddTaskActivity.this);

                long row_affected = db.insertTask(name,desc,time);

                db.close();

                if (row_affected != -1){
                    Toast.makeText(AddTaskActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
