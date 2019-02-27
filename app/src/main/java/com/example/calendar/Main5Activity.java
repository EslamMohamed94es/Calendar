package com.example.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main5Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<EventsItems> listitems1;
    SQLiteDataBqase sqLiteDataBqase;
    Cursor cursor;
    String StartDateTime;
    String EndDateTime;
    Integer AllDay;
    MyCustomAdapterr myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(ContextCompat.
                        getColor(this, R.color.colorPrimary)));
        getSupportActionBar().setTitle(R.string.main5activity);
        listView = findViewById(R.id.listview3);
        listitems1 = new ArrayList<EventsItems>();
        sqLiteDataBqase = new SQLiteDataBqase(Main5Activity.this);
        cursor = sqLiteDataBqase.getallrecords();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
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
            cursor.moveToNext();
        }
        myadapter = new MyCustomAdapterr(listitems1);
        listView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main5Activity.this, Main3Activity.class);
                Bundle bundle = new Bundle();
                TextView EventID = view.findViewById(R.id.textView4);
                String ID = EventID.getText().toString();
                bundle.putString("ID", ID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    class MyCustomAdapterr extends BaseAdapter {
        ArrayList<EventsItems> lsiitm = new ArrayList<EventsItems>();

        MyCustomAdapterr(ArrayList<EventsItems> items) {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(Main5Activity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main5Activity.this, MainActivity.class);
        startActivity(intent);
    }

}
