package com.example.googleapiimplimentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class For_Hotspot extends AppCompatActivity {

    private  long pressedTime;

    TextView pin1,pin2,pin3;
    Button show1,show2,show3;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_hotspot);

        pin1 = (TextView)findViewById(R.id.pincode1);
        pin2 = (TextView)findViewById(R.id.pincode2);
        pin3 = (TextView)findViewById(R.id.pincode3);
        show1 = (Button)findViewById(R.id.showhotspot1);
        show2 = (Button)findViewById(R.id.showhotspot2);
        show3 = (Button)findViewById(R.id.showhotspot3);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.hotspot);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),login.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.fullComplainList:
                        startActivity(new Intent(getApplicationContext(),ComplaintList.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.add_complaint:
                        startActivity(new Intent(getApplicationContext(),complain.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.hotspot:
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });

        ArrayList<String> gops = new ArrayList<String>();
        String[] store = new String[3];

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_complaint");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> gops = new ArrayList<String>();
                String[] store = new String[3];
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String ma = snapshot.child("pincode").getValue().toString();
                    System.out.println("pincodes are "+ ma);
                    gops.add(ma);
                }
                HashMap<String, Integer> mpp
                        = new HashMap<String, Integer>();
                for (String i : gops) {
                    if (mpp.containsKey(i)) {
                        mpp.put(i, mpp.get(i) + 1);
                    } else {
                        mpp.put(i, 1);
                    }
                }
                for (Map.Entry entry : mpp.entrySet()) {
                    Log.e("mpp: ",  entry.getKey()+ "=" + entry.getValue());
                    //System.out.println(entry.getKey() + " " + entry.getValue());
                }
                ArrayList<Integer> ans = new ArrayList<Integer>();
                for (Map.Entry entry : mpp.entrySet()) {
                    ans.add((Integer) entry.getValue());
                }
                Collections.sort(ans, Collections.reverseOrder());
                int c=0;
                int a=0;
                for (Integer i : ans) {
                    //System.out.println(i);
                    if(a==i)
                        continue;
                    for (Map.Entry<String, Integer> entry : mpp.entrySet()) {
                        if (entry.getValue() == i) {
                            //System.out.println("pincode "+entry.getKey());
                            Log.e("final:",entry.getKey());

                            store[c] = entry.getKey();
                            c++;
                            if(c==3)
                                break;
                        }
                        a=i;
                    }
                    if(c==3)
                        break;
                }
                pin1.setText(store[0]);
                pin2.setText(store[1]);
                pin3.setText(store[2]);
               /* for(int i=0; i<gops.size(); i++)
                {
                    Log.e("gops:",gops.get(i));
                }*/
                show1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(store[0]));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
                show2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(store[1]));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
                show3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(store[2]));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       // Log.d("status-text1", "Status: "+store[0]);
       // System.out.println("pincode "+store[0]);
        //System.out.println("pincode "+store[1]);
        //System.out.println("pincode "+store[2]);



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