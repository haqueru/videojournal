package com.example.haqueru.neurotrack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class Remember extends AppCompatActivity {

    ArrayList<String> items;
    ArrayList<String> allitems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        Bundle extras = getIntent().getExtras();
        items = extras.getStringArrayList("items");
        allitems = extras.getStringArrayList("allitems");

        // initialize the gridview
        final GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new TextAdapter(this));

        switchToMemory();
        pressItem();
    }

    public void pressItem(){
        // initialize the gridview
        final GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // give green or red feedback
                if (allitems.get(0).equals(items.get(position))) {
                    ((TextView) view).setTextColor(Color.parseColor("#00FF00"));
                } else {
                    ((TextView) view).setTextColor(Color.parseColor("#FF0000"));
                }

                // change intent
                startActivity(new Intent(getApplicationContext(), Read.class));
            }
        });


    }

    public void switchToMemory() {
        // change directions
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(R.string.memory_directions);

        // initialize the gridview
        final GridView gridView = (GridView) findViewById(R.id.gridView);
        Collections.shuffle(allitems);
        items.set(0, allitems.get(0));
        Collections.shuffle(items);
        gridView.invalidateViews();

    }

    private class TextAdapter extends BaseAdapter {
        private Context mContext;
        TextView textview;

        public TextAdapter(Context c) {
            mContext = c;
        }

        // number of items within gridview
        public int getCount() {
            return items.size();
        }

        // required by baseadapter extension,
        // usually returns object at position
        public Object getItem(int position){
            return null;
        }

        public long getItemId(int position){
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textview;
            if (convertView==null){
                textview = new TextView(mContext);

                //textview.setLayoutParams(new GridView.LayoutParams(85,85));
                //textview.setPadding(8,8,8,8);
            }
            else {
                textview = (TextView) convertView;
            }

            textview.setText(items.get(position));
            textview.setTextColor(Color.parseColor("#FFFFFF"));
            textview.setAllCaps(true);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

            return textview;
        }



    }

}
