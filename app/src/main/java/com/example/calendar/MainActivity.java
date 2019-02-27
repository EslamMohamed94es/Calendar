package com.example.calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    CalendarView calendarView;
    ListView listView;
    Intent intent;
    Bundle bundle;
    ArrayList<EventsItems> listitems1;
    MyCustomAdapter myadapter;
    SQLiteDataBqase sqLiteDataBqase;
    Cursor cursor;
    String StartDateTime;
    String EndDateTime;
    Integer AllDay;
    String ID;
    TextView EventID;
    boolean show;
    Integer year_year;
    Integer month_month;
    Integer day_day;
    SharedPreferences sharedPreferences;
    String WeekSarts;
    String DayWeekStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calendarView = findViewById(R.id.calendarView);
        fab = findViewById(R.id.fab);
        listView = findViewById(R.id.listview);
        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        DayWeekStart = sharedPreferences.getString("Day", "Sunday");
        SetWeekStartsFrom();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, Main2Activity.class);
                bundle = new Bundle();
                bundle.putInt("year", year_year);
                bundle.putInt("month", month_month);
                bundle.putInt("day", day_day);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, Main3Activity.class);
                bundle = new Bundle();
                EventID = view.findViewById(R.id.textView4);
                ID = EventID.getText().toString();
                bundle.putString("ID", ID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Calendar c = Calendar.getInstance();
        Integer Day = c.get(Calendar.DAY_OF_MONTH);
        final Integer Month = c.get(Calendar.MONTH);
        Integer Year = c.get(Calendar.YEAR);
        year_year = Year;
        month_month = Month;
        day_day = Day;
        SetCalendarEvents(Year, Month, Day);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                year_year = year;
                month_month = month;
                day_day = dayOfMonth;
                SetCalendarEvents(year, month, dayOfMonth);
            }
        });
    }

    public void SetCalendarEvents(Integer year, Integer month, Integer dayOfMonth) {
        month = month + 1;
        show = false;
        listitems1 = new ArrayList<EventsItems>();
        myadapter = new MyCustomAdapter(listitems1);
        listView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
        sqLiteDataBqase = new SQLiteDataBqase(MainActivity.this);
        cursor = sqLiteDataBqase.getallrecords();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (year > cursor.getInt(4) && year < cursor.getInt(7)) {
                show = true;
            } else if (year > cursor.getInt(4) && year == cursor.getInt(7)) {
                if (month < cursor.getInt(8)) {
                    show = true;
                } else if (month == cursor.getInt(8)) {
                    if (dayOfMonth <= cursor.getInt(9)) {
                        show = true;
                    }
                }
            } else if (year == cursor.getInt(4) && year < cursor.getInt(7)) {
                if (month > cursor.getInt(5)) {
                    show = true;
                } else if (month == cursor.getInt(5)) {
                    if (dayOfMonth >= cursor.getInt(6)) {
                        show = true;
                    }

                }
            } else if (year == cursor.getInt(4) && year == cursor.getInt(7)) {
                if (month > cursor.getInt(5) && month < cursor.getInt(8)) {
                    show = true;
                } else if (month > cursor.getInt(5) && month == cursor.getInt(8)) {
                    if (dayOfMonth <= cursor.getInt(9)) {
                        show = true;
                    }

                } else if (month == cursor.getInt(5) && month < cursor.getInt(8)) {
                    if (dayOfMonth >= cursor.getInt(6)) {
                        show = true;
                    }
                } else if (month == cursor.getInt(5) && month == cursor.getInt(8)) {
                    if (dayOfMonth >= cursor.getInt(6) && dayOfMonth <= cursor.getInt(9)) {
                        show = true;
                    }
                }
            }
            if (show == true) {
                AllDay = cursor.getInt(16);
                if (AllDay == 0) {
                    StartDateTime = String.valueOf(cursor.getInt(6)) + "/" +
                            String.valueOf(cursor.getInt(5)) + "/" + String.valueOf(cursor.getInt(4)) +
                            "_" + String.valueOf(cursor.getInt(10)) + ":" + String.valueOf(cursor.getInt(11)) +
                            " " + cursor.getString(12);
                    EndDateTime = String.valueOf(cursor.getInt(9)) + "/" +
                            String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(7)) +
                            "_" + String.valueOf(cursor.getInt(13)) + ":" + String.valueOf(cursor.getInt(14)) +
                            " " + cursor.getString(15);
                    listitems1.add(new EventsItems(String.valueOf(cursor.getInt(0)), cursor.getString(1), cursor.getString(3),
                            StartDateTime, EndDateTime));
                } else {
                    StartDateTime = String.valueOf(cursor.getInt(6)) + "/" +
                            String.valueOf(cursor.getInt(5)) + "/" + String.valueOf(cursor.getInt(4)) + " All Day";
                    EndDateTime = String.valueOf(cursor.getInt(9)) + "/" +
                            String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(7)) + " All Day";
                    listitems1.add(new EventsItems(String.valueOf(cursor.getInt(0)), cursor.getString(1), cursor.getString(3),
                            StartDateTime, EndDateTime));
                }


            }
            cursor.moveToNext();
            show = false;
        }
        myadapter = new MyCustomAdapter(listitems1);
        listView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();

    }

    public void SetWeekStartsFrom() {
        if (DayWeekStart.equals("Saturday")) {
            calendarView.setFirstDayOfWeek(Calendar.SATURDAY);
        } else if (DayWeekStart.equals("Sunday")) {
            calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        } else if (DayWeekStart.equals("Monday")) {
            calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        } else if (DayWeekStart.equals("Tuesday")) {
            calendarView.setFirstDayOfWeek(Calendar.TUESDAY);
        } else if (DayWeekStart.equals("Wednesday")) {
            calendarView.setFirstDayOfWeek(Calendar.WEDNESDAY);
        } else if (DayWeekStart.equals("Thursday")) {
            calendarView.setFirstDayOfWeek(Calendar.THURSDAY);
        } else if (DayWeekStart.equals("Friday")) {
            calendarView.setFirstDayOfWeek(Calendar.FRIDAY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.today) {
            Date date = new Date();
            calendarView.setDate(date.getTime());
            Calendar c = Calendar.getInstance();
            Integer Day = c.get(Calendar.DAY_OF_MONTH);
            Integer Month = c.get(Calendar.MONTH);
            Integer Year = c.get(Calendar.YEAR);
            SetCalendarEvents(Year, Month, Day);
        } else if (id == R.id.gote) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(MainActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.set(
                                    year,
                                    month,
                                    dayOfMonth
                            );
                            calendarView.setDate(calendar2.getTimeInMillis());
                            SetCalendarEvents(year, month, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (id == R.id.scedule) {
            intent = new Intent(MainActivity.this, Main5Activity.class);
            startActivity(intent);
        } else if (id == R.id.weekstarts) {
            Context mContext;
            CoordinatorLayout mRelativeLayout;
            final PopupWindow mPopupWindow;
            mContext = getApplicationContext();
            mRelativeLayout = findViewById(R.id.coordlayout);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.popup, null);
            mPopupWindow = new PopupWindow(
                    customView,
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            mPopupWindow.setFocusable(true);

            // Set an elevation value for popup window
            // Call requires API level 21
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }
            mPopupWindow.showAtLocation(mRelativeLayout, Gravity.BOTTOM, 0, 200);
            final RadioButton radioButton = customView.findViewById(R.id.radioButton);
            final RadioButton radioButton1 = customView.findViewById(R.id.radioButton1);
            final RadioButton radioButton2 = customView.findViewById(R.id.radioButton2);
            final RadioButton radioButton3 = customView.findViewById(R.id.radioButton3);
            final RadioButton radioButton4 = customView.findViewById(R.id.radioButton4);
            final RadioButton radioButton5 = customView.findViewById(R.id.radioButton5);
            final RadioButton radioButton6 = customView.findViewById(R.id.radioButton6);
            Button Done_btn = customView.findViewById(R.id.button3);
            Done_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioButton.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.SATURDAY);
                        WeekSarts = "Saturday";
                    } else if (radioButton1.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
                        WeekSarts = "Sunday";
                    } else if (radioButton2.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
                        WeekSarts = "Monday";
                    } else if (radioButton3.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.TUESDAY);
                        WeekSarts = "Tuesday";
                    } else if (radioButton4.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.WEDNESDAY);
                        WeekSarts = "Wednesday";
                    } else if (radioButton5.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.THURSDAY);
                        WeekSarts = "Thursday";
                    } else if (radioButton6.isChecked()) {
                        calendarView.setFirstDayOfWeek(Calendar.FRIDAY);
                        WeekSarts = "Friday";
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Day", WeekSarts);
                    editor.commit();
                    mPopupWindow.dismiss();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    class MyCustomAdapter extends BaseAdapter {
        ArrayList<EventsItems> lsiitm = new ArrayList<EventsItems>();

        MyCustomAdapter(ArrayList<EventsItems> items) {
            this.lsiitm = items;
        }

        @Override
        public int getCount() {
            return lsiitm.size();
        }

        @Override
        public Object getItem(int position) {
            return lsiitm.get(position).title;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.eventitems, null);
            TextView TXTID = view.findViewById(R.id.textView4);
            TextView TXTTitle = view.findViewById(R.id.textView);
            TextView TXTLocation = view.findViewById(R.id.textView2);
            TextView TXTStartDate = view.findViewById(R.id.textView3);
            TextView TXTEndDate = view.findViewById(R.id.textView8);
            TXTTitle.setText(lsiitm.get(position).title);
            TXTLocation.setText(lsiitm.get(position).location);
            TXTStartDate.setText(lsiitm.get(position).starttime);
            TXTEndDate.setText(lsiitm.get(position).endtime);
            TXTID.setText(lsiitm.get(position).id);
            return view;
        }

    }
}
