package com.unicode.eventuni.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.unicode.eventuni.Adapter.EventAdapter;
import com.unicode.eventuni.Model.GetEvent;
import com.unicode.eventuni.Model.Event;
import com.unicode.eventuni.R;
import com.unicode.eventuni.Rest.ApiClient;
import com.unicode.eventuni.Rest.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static MainActivity ma;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String actionBarTitle;
        actionBarTitle = "";
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_event);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma=this;
        refresh();

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });
    }

    public void refresh() {
        Call<GetEvent> EventCall = mApiInterface.getEvent();
        EventCall.enqueue(new Callback<GetEvent>() {
            @Override
            public void onResponse(Call<GetEvent> call, Response<GetEvent>
                    response) {
                List<Event> EventList = response.body().getListDataEvent();
                Log.d("Retrofit Get", "Jumlah data Event: " +
                        String.valueOf(EventList.size()));
                mAdapter = new EventAdapter(EventList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetEvent> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}