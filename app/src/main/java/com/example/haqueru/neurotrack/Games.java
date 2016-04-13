package com.example.haqueru.neurotrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Games extends AppCompatActivity {

    ListView list;
    String[] itemname ={
            "Word Memory",
            "Simon Says",
            "Video Journal"
    };

    Integer[] imgid={
            R.drawable.word,
            R.drawable.simon,
            R.drawable.video
    };

    String[] itemdescription={
            ""
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        // create list of games by binding custom adapter to list
        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        // go to game if item is pressed
        pushListItem();



    }


    public void pushListItem() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
               //switch (position) {
//                if (position == 1) {
//                    startActivity(new Intent(getApplicationContext(), Read.class));
//                }
//                if (position==3){
                    startActivity(new Intent(getApplicationContext(),VideoJournal.class));
//                }
                   //case 2:startActivity(new Intent(getApplicationContext(),SimonSays.class));

               //}



            }
        });
    }





    public class CustomListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] itemname;
        private final Integer[] imgid;

        public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
            super(context, R.layout.mylist, itemname);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.itemname=itemname;
            this.imgid=imgid;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.mylist, null,true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

            txtTitle.setText(itemname[position]);
            imageView.setImageResource(imgid[position]);
            //extratxt.setText("Description "+itemname[position]);
            return rowView;

        };
    }

}
