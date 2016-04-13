package com.example.haqueru.neurotrack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

//import android.media.session.MediaController;


public class VideoJournal extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener {


    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Bind(R.id.calendarView)
    MaterialCalendarView widget;

    @Bind(R.id.videoList)
    ListView videoList;

    @Bind(R.id.view1)
    VideoView view1;

    ArrayList<String> itemarray  = new ArrayList<>();
    ArrayList<String> itemname = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_journal);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup initial text
        //textView.setText(getSelectedDatesString());

        // take video on floating action press
        takeVideo();

        // play video on item press
        pushListItem();

        // add string path to itemarray
        itemarray.add("android.resource://" + getPackageName() + "/" + R.raw.happy);
        itemarray.add("android.resource://" + getPackageName() + "/" + R.raw.sad);
        itemarray.add("android.resource://" + getPackageName() + "/" + R.raw.surprised);

        // add string name
        itemname.add("Happy");
        itemname.add("Sad");
        itemname.add("Surprised");

    }

    static final int REQUEST_VIDEO_CAPTURE = 1;
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


    private void takeVideo(){
        // floating action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });

    }

    private void pushListItem(){
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get uri for this position
                Uri uri = Uri.parse(itemarray.get(position));

                // set playback to the videoview
                MediaController mediaController = new MediaController(view.getContext());
                mediaController.setAnchorView(view1);
                view1.setMediaController(mediaController);

                // make vide visible and play video
                view1.setVisibility(View.VISIBLE);
                view1.setVideoURI(uri);
                view1.start();


                // close video once done
                view1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        view1.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            itemarray.add(videoUri.toString());
            videoList.invalidateViews();
        }
    }



    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        // create list of games by binding custom adapter to list
        CustomListAdapter adapter = new CustomListAdapter(this, itemname);
        videoList.setAdapter(adapter);


        //textView.setText(getSelectedDatesString());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }


    public class CustomListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> itemname;

        public CustomListAdapter(Activity context, ArrayList<String> itemname) {
            super(context, R.layout.mylist, itemname);
            // TODO Auto-generated constructor stub
            this.itemname = itemname;
            this.context=context;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.mylist, null,true);


            // set video name
            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            txtTitle.setText(itemname.get(position));
            txtTitle.setTextColor(Color.parseColor("#000000"));
            //txtTitle.setHeight(10);
            //imageView.setImageResource(imgid[position]);
            //extratxt.setText("Description "+itemname[position]);

            return rowView;

        }

    }









}
