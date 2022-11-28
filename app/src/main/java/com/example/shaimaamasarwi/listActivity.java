package com.example.shaimaamasarwi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listView) ;
        ArrayList<String> arrayList = new ArrayList<>(); // ma3rakh

        //add items
        arrayList.add("smth");
        arrayList.add("smth2");
        // adapter bo5eth mn al array w bdeph bl list bo5eth array list w be70t bldesine
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);
        // handle items clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            // i he al3er5 alli bdna aya
            {
                Toast.makeText(listActivity.this, "clicked item"+i+ arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}