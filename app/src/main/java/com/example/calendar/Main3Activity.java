package com.example.calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    TextView Title;
    TextView Location;
    TextView Description;
    TextView StartDate;
    TextView EndDate;
    TextView Reminder;
    TextView Notifi;
    TextView Vibrate;
    String title;
    String location;
    String desc;
    Integer AllDay;
    String StartDateTime;
    String EndDateTime;
    Intent intent;
    Bundle bundle;
    SQLiteDataBqase sqLiteDataBqase;
    Cursor cursor;
    String ID;
    Button btn_edit;
    Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(ContextCompat.
                        getColor(this, R.color.colorPrimaryDark)));
        getSupportActionBar().setTitle(R.string.main3activity);
        bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        remove_notif();
        sqLiteDataBqase = new SQLiteDataBqase(Main3Activity.this);
        cursor = sqLiteDataBqase.getallrecordsbyID(ID);
        cursor.moveToFirst();
        Title = findViewById(R.id.textView9);
        Location = findViewById(R.id.textView12);
        Description = findViewById(R.id.textView10);
        StartDate = findViewById(R.id.textView11);
        EndDate = findViewById(R.id.textView13);
        Reminder = findViewById(R.id.textView50);
        Notifi = findViewById(R.id.textView600);
        Vibrate = findViewById(R.id.textView800);
        title = cursor.getString(1);
        location = cursor.getString(3);
        desc = cursor.getString(2);
        AllDay = cursor.getInt(16);
        Title.setText(title);
        Location.setText(location);
        Description.setText(desc);
        if (AllDay == 0) {
            StartDateTime = String.valueOf(cursor.getInt(6)) + "/" +
                    String.valueOf(cursor.getInt(5)) + "/" + String.valueOf(cursor.getInt(4)) +
                    "_" + String.valueOf(cursor.getInt(10)) + ":" + String.valueOf(cursor.getInt(11)) +
                    " " + cursor.getString(12);
            EndDateTime = String.valueOf(cursor.getInt(9)) + "/" +
                    String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(7)) +
                    "_" + String.valueOf(cursor.getInt(13)) + ":" + String.valueOf(cursor.getInt(14)) +
                    " " + cursor.getString(15);
        } else {
            StartDateTime = String.valueOf(cursor.getInt(6)) + "/" +
                    String.valueOf(cursor.getInt(5)) + "/" + String.valueOf(cursor.getInt(4)) + " All Day";
            EndDateTime = String.valueOf(cursor.getInt(9)) + "/" +
                    String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(7)) + " All Day";
        }
        StartDate.setText(StartDateTime);
        EndDate.setText(EndDateTime);
        if (cursor.getInt(17) == 1) {
            Reminder.setText(R.string.yes);
        } else {
            Reminder.setText(R.string.no);
        }
        if (cursor.getInt(18) == 1) {
            Notifi.setText(R.string.yes);
        } else {
            Notifi.setText(R.string.no);
        }
        if (cursor.getInt(19) == 1) {
            Vibrate.setText(R.string.yes);
        } else {
            Vibrate.setText(R.string.no);
        }
        btn_delete = findViewById(R.id.button2);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main3Activity.this);
                builder.setMessage(R.string.deleteevent)
                        .setIcon(R.drawable.delete)
                        .setTitle(R.string.eventDelete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqLiteDataBqase.delete(ID);
                                Toast.makeText(Main3Activity.this, R.string.event_deleted, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
        btn_edit = findViewById(R.id.button);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Main3Activity.this, Main4Activity.class);
                bundle = new Bundle();
                bundle.putString("ID", ID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void remove_notif() {
        NotificationManagerCompat notificationManagerCompat;
        notificationManagerCompat = NotificationManagerCompat.from(Main3Activity.this);
        notificationManagerCompat.cancel(Integer.parseInt(ID));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
