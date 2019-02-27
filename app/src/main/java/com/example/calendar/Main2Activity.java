package com.example.calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Button startdate_btn;
    Button enddate_btn;
    Button starttime_btn;
    Button endtime_btn;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText title;
    EditText description;
    EditText location;
    Switch allday;
    Switch reminder;
    Switch notification;
    Switch vibrate;
    boolean titleop;
    boolean descop;
    boolean locationop;
    Integer startyear;
    Integer startmonth;
    Integer startday;
    Integer endyear;
    Integer endmonth;
    Integer endday;
    Integer starthour;
    Integer startminute;
    String startapm;
    Integer endhour;
    Integer endminute;
    String endapm;
    Integer all_day;
    Integer Reminder;
    Integer Notification;
    Integer Vibrate;
    Integer ID;
    Integer Start_Hour;
    Integer year_year;
    Integer month_month;
    Integer day_day;
    Bundle bundle;
    String spacestart = "                                                      ";
    String spaceend = "                                                           ";
    String spacestart1 = "                                                          ";
    String spaceend1 = "                                                               ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(ContextCompat.
                        getColor(this, R.color.colorPrimary)));
        getSupportActionBar().setTitle(R.string.main2activity);
        bundle = getIntent().getExtras();
        year_year = bundle.getInt("year");
        month_month = bundle.getInt("month");
        day_day = bundle.getInt("day");
        Reminder = 0;
        Notification = 0;
        Vibrate = 0;
        all_day = 0;
        title = findViewById(R.id.titleedt);
        description = findViewById(R.id.desc);
        location = findViewById(R.id.location);
        startdate_btn = findViewById(R.id.button5);
        enddate_btn = findViewById(R.id.button6);
        starttime_btn = findViewById(R.id.button7);
        endtime_btn = findViewById(R.id.button8);
        allday = findViewById(R.id.allday);
        reminder = findViewById(R.id.reminder);
        notification = findViewById(R.id.notification);
        vibrate = findViewById(R.id.vibrate);
        floatingActionButton = findViewById(R.id.fab1);
        notification.setEnabled(false);
        vibrate.setEnabled(false);
        startdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDate_Fun();
            }
        });

        enddate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDate_Fun();
            }
        });

        starttime_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime_Fun();
            }
        });

        endtime_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndTime_Fun();
            }

        });

        allday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    all_day = 1;
                    starttime_btn.setEnabled(false);
                    endtime_btn.setEnabled(false);
                } else {
                    all_day = 0;
                    starttime_btn.setEnabled(true);
                    endtime_btn.setEnabled(true);
                }
            }
        });
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Reminder = 1;
                    notification.setEnabled(true);
                    vibrate.setEnabled(true);
                    if (notification.isChecked()) {
                        Notification = 1;
                    }
                    if (vibrate.isChecked()) {
                        Vibrate = 1;
                    }
                } else {
                    Reminder = 0;
                    notification.setEnabled(false);
                    vibrate.setEnabled(false);
                    Notification = 0;
                    Vibrate = 0;
                }
            }
        });
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Notification = 1;
                } else {
                    Notification = 0;
                }
            }
        });
        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Vibrate = 1;
                } else {
                    Vibrate = 0;
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEvent();
            }
        });
    }

    public void StartDate_Fun() {
        datePickerDialog = new DatePickerDialog(Main2Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startdate_btn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        startdate_btn.setText("Start Date" + spacestart + String.valueOf(dayOfMonth) +
                                "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                        startyear = year;
                        startmonth = month + 1;
                        startday = dayOfMonth;
                    }
                }, year_year, month_month, day_day);
        datePickerDialog.show();
    }

    public void EndDate_Fun() {
        datePickerDialog = new DatePickerDialog(Main2Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        enddate_btn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        enddate_btn.setText("End Date" + spaceend + String.valueOf(dayOfMonth) +
                                "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                        endyear = year;
                        endmonth = month + 1;
                        endday = dayOfMonth;
                    }
                }, year_year, month_month, day_day);
        datePickerDialog.show();
    }

    public void StartTime_Fun() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                starthour = hourOfDay;
                Start_Hour = hourOfDay;
                startminute = minute;
                if (starthour < 12) {
                    if (starthour == 0) {
                        starthour = 12;
                    }
                    startapm = "AM";
                } else {
                    if (starthour > 12) {
                        starthour = starthour - 12;
                    }
                    startapm = "PM";
                }
                starttime_btn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                starttime_btn.setText("Start Time" + spacestart1 + String.valueOf(starthour) +
                        ":" + String.valueOf(startminute) + " " + startapm);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void EndTime_Fun() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endhour = hourOfDay;
                endminute = minute;
                if (endhour < 12) {
                    if (endhour == 0) {
                        endhour = 12;
                    }
                    endapm = "AM";
                } else {
                    if (endhour > 12) {
                        endhour = endhour - 12;
                    }
                    endapm = "PM";

                }
                endtime_btn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                endtime_btn.setText("End Time" + spaceend1 + String.valueOf(endhour) +
                        ":" + String.valueOf(endminute) + " " + endapm);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void CreateEvent() {
        titleop = title.getText().toString().trim().length() > 0;
        descop = description.getText().toString().trim().length() > 0;
        locationop = location.getText().toString().trim().length() > 0;
        if (titleop && descop && locationop && startyear != null && startmonth != null && startday != null &&
                endyear != null && endmonth != null && endday != null) {
            if (all_day == 1) {
                starthour = 0;
                endhour = 0;
                startminute = 0;
                endminute = 0;
                startapm = "";
                endapm = "";
                Start_Hour = 0;
                SQLiteDataBqase sqLiteDataBqase = new SQLiteDataBqase(Main2Activity.this);
                sqLiteDataBqase.insertitem(title.getText().toString(), description.getText().toString(),
                        location.getText().toString(), startyear, startmonth, startday, endyear, endmonth, endday,
                        starthour, startminute, startapm, endhour, endminute, endapm, all_day, Reminder, Notification, Vibrate, Start_Hour);
                Toast.makeText(Main2Activity.this, R.string.eventcreated, Toast.LENGTH_LONG).show();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(
                        startyear,
                        startmonth - 1,
                        startday,
                        9,
                        0,
                        0
                );
                sqLiteDataBqase = new SQLiteDataBqase(Main2Activity.this);
                Cursor cursor = sqLiteDataBqase.getallrecords();
                cursor.moveToFirst();
                Integer size = cursor.getCount();
                Integer count = 1;
                while (cursor.isAfterLast() == false) {
                    if (count == size) {
                        ID = cursor.getInt(0);
                        cursor.moveToNext();
                    } else {
                        cursor.moveToNext();
                        count++;
                    }

                }
                SetAlarm(calendar2.getTimeInMillis(), ID);
                if (Reminder == 1 && Notification == 1) {
                    Toast.makeText(Main2Activity.this, R.string.notification9, Toast.LENGTH_LONG).show();
                } else if (Reminder == 1) {
                    Toast.makeText(Main2Activity.this, R.string.reminder9, Toast.LENGTH_LONG).show();
                }
            } else {
                if (starthour != null && startminute != null &&
                        endhour != null && endminute != null) {
                    SQLiteDataBqase sqLiteDataBqase = new SQLiteDataBqase(Main2Activity.this);
                    sqLiteDataBqase.insertitem(title.getText().toString(), description.getText().toString(),
                            location.getText().toString(), startyear, startmonth, startday, endyear, endmonth, endday,
                            starthour, startminute, startapm, endhour, endminute, endapm, all_day, Reminder, Notification, Vibrate, Start_Hour);
                    Toast.makeText(Main2Activity.this, R.string.eventcreated, Toast.LENGTH_LONG).show();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(
                            startyear,
                            startmonth - 1,
                            startday,
                            Start_Hour,
                            startminute,
                            0
                    );
                    sqLiteDataBqase = new SQLiteDataBqase(Main2Activity.this);
                    Cursor cursor = sqLiteDataBqase.getallrecords();
                    cursor.moveToFirst();
                    Integer size = cursor.getCount();
                    Integer count = 1;
                    while (cursor.isAfterLast() == false) {
                        if (count == size) {
                            ID = cursor.getInt(0);
                            cursor.moveToNext();
                        } else {
                            cursor.moveToNext();
                            count++;
                        }

                    }
                    SetAlarm(calendar2.getTimeInMillis(), ID);
                } else {
                    Toast.makeText(Main2Activity.this, R.string.timedate, Toast.LENGTH_LONG).show();
                }
            }

        } else {
            Toast.makeText(Main2Activity.this, R.string.requireddate, Toast.LENGTH_LONG).show();
        }
    }

    private void SetAlarm(long timeInMillis, Integer RequestCode) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reminder.class);
        intent.putExtra("requestCode", RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, RequestCode, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
