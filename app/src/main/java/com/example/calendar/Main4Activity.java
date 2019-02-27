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

public class Main4Activity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    EditText Title;
    EditText Location;
    EditText Description;
    Button Start_Date;
    Button End_Date;
    Button Start_Time;
    Button End_Time;
    Switch All_Day;
    Switch reminder;
    Switch notification;
    Switch vibrate;
    Integer AllDay;
    Integer Reminder;
    Integer Notification;
    Integer Vibrate;
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
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    SQLiteDataBqase sqLiteDataBqase;
    Cursor cursor;
    Bundle bundle;
    String ID;
    Integer Start_Hour;
    String spacestart = "                                                      ";
    String spaceend = "                                                           ";
    String spacestart1 = "                                                          ";
    String spaceend1 = "                                                               ";
    Calendar c;
    int mYear;
    int mMonth;
    int mDay;
    Integer mHour;
    Integer mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(ContextCompat.
                        getColor(this, R.color.colorPrimary)));
        getSupportActionBar().setTitle(R.string.main4activity);
        bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        sqLiteDataBqase = new SQLiteDataBqase(Main4Activity.this);
        cursor = sqLiteDataBqase.getallrecordsbyID(ID);
        cursor.moveToFirst();
        Title = findViewById(R.id.titleedt1);
        Location = findViewById(R.id.location1);
        Description = findViewById(R.id.desc1);
        Start_Date = findViewById(R.id.button51);
        End_Date = findViewById(R.id.button61);
        Start_Time = findViewById(R.id.button71);
        End_Time = findViewById(R.id.button81);
        All_Day = findViewById(R.id.allday1);
        reminder = findViewById(R.id.reminder1);
        notification = findViewById(R.id.notification1);
        vibrate = findViewById(R.id.vibrate1);
        floatingActionButton = findViewById(R.id.fab11);
        Title.setText(cursor.getString(1));
        Location.setText(cursor.getString(3));
        Description.setText(cursor.getString(2));
        Start_Date.setText("Start Date" + spacestart + String.valueOf(cursor.getInt(6)) + "/" +
                String.valueOf(cursor.getInt(5)) + "/" + String.valueOf(cursor.getInt(4)));
        startyear = cursor.getInt(4);
        startmonth = cursor.getInt(5);
        startday = cursor.getInt(6);
        End_Date.setText("End Date" + spaceend + String.valueOf(cursor.getInt(9)) + "/" +
                String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(7)));
        endyear = cursor.getInt(7);
        endmonth = cursor.getInt(8);
        endday = cursor.getInt(9);
        AllDay = cursor.getInt(16);
        Reminder = cursor.getInt(17);
        Notification = cursor.getInt(18);
        Vibrate = cursor.getInt(19);
        if (AllDay == 0) {
            Start_Time.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            Start_Time.setText("Start Time" + spacestart1 + String.valueOf(cursor.getInt(10)) + ":" + String.valueOf(cursor.getInt(11)) +
                    " " + cursor.getString(12));
            starthour = cursor.getInt(10);
            Start_Hour = cursor.getInt(20);
            startminute = cursor.getInt(11);
            startapm = cursor.getString(12);
            End_Time.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            End_Time.setText("End Time" + spaceend1 + String.valueOf(cursor.getInt(13)) + ":" + String.valueOf(cursor.getInt(14)) +
                    " " + cursor.getString(15));
            endhour = cursor.getInt(13);
            endminute = cursor.getInt(14);
            endapm = cursor.getString(15);
        } else {
            All_Day.setChecked(true);
            Start_Time.setEnabled(false);
            End_Time.setEnabled(false);
        }

        if (Reminder == 1) {
            reminder.setChecked(true);
        } else if (Reminder == 0) {
            notification.setEnabled(false);
            vibrate.setEnabled(false);
        }
        if (Notification == 1) {
            notification.setChecked(true);
        }
        if (Vibrate == 1) {
            vibrate.setChecked(true);
        }
        Start_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDate_Fun();
            }
        });
        End_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDate_Fun();
            }
        });
        Start_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime_Fun();
            }
        });
        End_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndTime_Fun();
            }
        });

        All_Day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AllDay = 1;
                    Start_Time.setEnabled(false);
                    End_Time.setEnabled(false);
                } else {
                    AllDay = 0;
                    Start_Time.setEnabled(true);
                    End_Time.setEnabled(true);
                }
            }
        });
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Reminder = 1;
                    vibrate.setEnabled(true);
                    notification.setEnabled(true);
                    if (notification.isChecked()) {
                        Notification = 1;
                    }
                    if (vibrate.isChecked()) {
                        Vibrate = 1;
                    }
                } else {
                    Reminder = 0;
                    vibrate.setEnabled(false);
                    notification.setEnabled(false);
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
                UpdateEvent();
            }
        });
    }

    public void StartDate_Fun() {
        datePickerDialog = new DatePickerDialog(Main4Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Start_Date.setText("Start Date" + spacestart + String.valueOf(dayOfMonth) +
                                "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                        startyear = year;
                        startmonth = month + 1;
                        startday = dayOfMonth;
                    }
                }, startyear, startmonth - 1, startday);
        datePickerDialog.show();
    }

    public void EndDate_Fun() {
        datePickerDialog = new DatePickerDialog(Main4Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        End_Date.setText("End Date" + spaceend + String.valueOf(dayOfMonth) +
                                "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                        endyear = year;
                        endmonth = month + 1;
                        endday = dayOfMonth;
                    }
                }, endyear, endmonth - 1, endday);
        datePickerDialog.show();
    }

    public void StartTime_Fun() {
        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(Main4Activity.this, new TimePickerDialog.OnTimeSetListener() {
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
                Start_Time.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                Start_Time.setText("Start Time" + spacestart1 + String.valueOf(starthour) +
                        ":" + String.valueOf(startminute) + " " + startapm);

            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void EndTime_Fun() {
        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(Main4Activity.this, new TimePickerDialog.OnTimeSetListener() {
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
                End_Time.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                End_Time.setText("End Time" + spaceend1 + String.valueOf(endhour) +
                        ":" + String.valueOf(endminute) + " " + endapm);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void UpdateEvent() {
        titleop = Title.getText().toString().trim().length() > 0;
        descop = Description.getText().toString().trim().length() > 0;
        locationop = Location.getText().toString().trim().length() > 0;
        if (titleop && descop && locationop) {
            if (AllDay == 1) {
                starthour = 0;
                endhour = 0;
                startminute = 0;
                endminute = 0;
                startapm = "";
                endapm = "";
                Start_Hour = 0;
                sqLiteDataBqase = new SQLiteDataBqase(Main4Activity.this);
                sqLiteDataBqase.update(String.valueOf(ID), Title.getText().toString(), Description.getText().toString(),
                        Location.getText().toString(), startyear, startmonth, startday, endyear, endmonth, endday, starthour,
                        startminute, startapm, endhour, endminute, endapm, AllDay, Reminder, Notification, Vibrate, Start_Hour);
                Toast.makeText(Main4Activity.this, R.string.eventupdated, Toast.LENGTH_LONG).show();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(
                        startyear,
                        startmonth - 1,
                        startday,
                        9,
                        0,
                        0
                );
                UpdateAlarm(calendar2.getTimeInMillis(), Integer.parseInt(ID));
                if (Reminder == 1 && Notification == 1) {
                    Toast.makeText(Main4Activity.this, R.string.notification9, Toast.LENGTH_LONG).show();
                } else if (Reminder == 1) {
                    Toast.makeText(Main4Activity.this, R.string.reminder9, Toast.LENGTH_LONG).show();
                }
            } else {
                if (starthour != null && startminute != null &&
                        endhour != null && endminute != null) {
                    sqLiteDataBqase = new SQLiteDataBqase(Main4Activity.this);
                    sqLiteDataBqase.update(String.valueOf(ID), Title.getText().toString(), Description.getText().toString(),
                            Location.getText().toString(), startyear, startmonth, startday, endyear, endmonth, endday, starthour,
                            startminute, startapm, endhour, endminute, endapm, AllDay, Reminder, Notification, Vibrate, Start_Hour);
                    Toast.makeText(Main4Activity.this, R.string.eventupdated, Toast.LENGTH_LONG).show();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(
                            startyear,
                            startmonth - 1,
                            startday,
                            Start_Hour,
                            startminute,
                            0
                    );
                    UpdateAlarm(calendar2.getTimeInMillis(), Integer.parseInt(ID));
                } else {
                    Toast.makeText(Main4Activity.this, R.string.timedate, Toast.LENGTH_LONG).show();
                }
            }

        } else {
            Toast.makeText(Main4Activity.this, R.string.requireddate, Toast.LENGTH_LONG).show();
        }
    }

    private void UpdateAlarm(long timeInMillis, Integer RequestCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reminder.class);
        intent.putExtra("requestCode", RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, RequestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
        bundle = new Bundle();
        bundle.putString("ID", ID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
            bundle = new Bundle();
            bundle.putString("ID", ID);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
