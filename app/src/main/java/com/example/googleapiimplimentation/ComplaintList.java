package com.example.googleapiimplimentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintList extends AppCompatActivity {

    private long pressedTime;

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
   // FloatingActionButton ac;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.fullComplainList);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),login.class));
                        overridePendingTransition(R.anim.scale_up, R.anim.scale_down);
                        return true;
                    case R.id.fullComplainList:
                        return true;
                    case R.id.add_complaint:
                        startActivity(new Intent(getApplicationContext(),complain.class));
                        overridePendingTransition(R.anim.scale_up, R.anim.scale_down);
                        return true;
                    case R.id.hotspot:
                        startActivity(new Intent(getApplicationContext(),For_Hotspot.class));
                        overridePendingTransition(R.anim.scale_up, R.anim.scale_down);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });


        //ac= (FloatingActionButton)findViewById(R.id.fab_add_complaint) ;

       /* ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplaintList.this, complain.class);
                startActivity(intent);
            }
        });*/

        recyclerView = (RecyclerView)findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("user_complaint"),MainModel.class)
                .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);

        getMenuInflater().inflate(R.menu.hotspot,menu);
        MenuItem item2 = menu.findItem(R.id.hotspot);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ComplaintList.this, For_Hotspot.class);
                startActivity(intent);
                return false;
            }
        });

        SearchView searchView = (SearchView)item.getActionView();
        Toast.makeText(ComplaintList.this, "SEARCH BY NAME", Toast.LENGTH_SHORT).show();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("user_complaint").orderByChild("name").startAt(str).endAt(str+"~"),MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {

            super.onBackPressed();

            this.finishAffinity();

        } else {

            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();

        }

        pressedTime = System.currentTimeMillis();

    }
}