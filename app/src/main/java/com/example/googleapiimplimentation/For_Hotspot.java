package com.example.googleapiimplimentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    TextView pin1,pin2,pin3;
    Button show1,show2,show3;

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

        ArrayList<String> gops = new ArrayList<String>();
        ArrayList<String> store = new ArrayList<String>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_complaint");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String ma = snapshot.child("pincode").getValue().toString();
                    //System.out.println("pincodes are "+ ma);
                    gops.add(ma);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        HashMap<String, Integer> mpp
                = new HashMap<String, Integer>();
        for (String i : gops) {
            if (mpp.containsKey(i)) {
                mpp.put(i, mpp.get(i) + 1);
            } else {
                mpp.put(i, 1);
            }
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
                    store.add(entry.getKey());
                    c++;
                    if(c==3)
                        break;
                }
                a=i;
            }
            if(c==3)
                break;
        }

        //System.out.println(store.get(0));
        //System.out.println(store.get(1));
        //System.out.println(store.get(2));

        pin1.setText(store.get(0));
        pin2.setText(store.get(1));
        pin3.setText(store.get(2));

        show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(store.get(0)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(store.get(1)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        show3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(store.get(2)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }
}