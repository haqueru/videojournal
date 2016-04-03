package com.example.haqueru.neurotrack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Read extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<String>(
            Arrays.asList("RED", "BLUE", "GREEN", "YELLOW"));

    ArrayList<String> allitems = new ArrayList<String>(
            Arrays.asList("PIE", "FROG", "POOR", "FOUR", "TREE", "FIVE", "SIX", "SEVEN", "EIGHT"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        // initialize the gridview
        final GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new TextAdapter(this));

        switchToRead();
        pressButton();


    }
    public void switchToRead() {

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(R.string.read_directions);

        Collections.shuffle(allitems);
        for (int i = 0; i < items.size(); i++) {
            items.set(i,allitems.get(i));
        }

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.invalidateViews();
    }

    public void  pressButton() {
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Remember.class);
                Bundle extras = new Bundle();
                extras.putStringArrayList("items",items);
                extras.putStringArrayList("allitems",allitems);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

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
