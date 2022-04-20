package com.example.googleapiimplimentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    Button signout;

    TextView name, email;
    //Button complain;
    GoogleSignInClient mGoogleSignInClient;
    //Button complaintList/*,button*/;

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        signout = (Button) findViewById(R.id.signout);
        // complaintList=findViewById(R.id.complaintList);
        //complain=(Button) findViewById(R.id.complain);
        //button=(Button)findViewById(R.id.button2);

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode("800002"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });*/
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fullComplainList:
                        startActivity(new Intent(getApplicationContext(), ComplaintList.class));
                        overridePendingTransition(R.anim.scale_up, R.anim.scale_down);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.add_complaint:
                        startActivity(new Intent(getApplicationContext(), complain.class));
                        overridePendingTransition(R.anim.scale_up, R.anim.scale_down);
                        return true;
                    case R.id.hotspot:
                        startActivity(new Intent(getApplicationContext(), For_Hotspot.class));
                        overridePendingTransition(R.anim.scale_up, R.anim.scale_down);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });

       /* complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(login.this, complain.class);
                startActivity(intent);

            }
        });

        complaintList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, ComplaintList.class);
                startActivity(intent);
            }
        });*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Button signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();

            name.setText(personName);

            email.setText(personEmail);
            GlobalVariable.username = personEmail;

        } else {


            name.setText("Please Login to view user profile!");
            signout.setText("SignIn to continue");

        }
        recyclerView = (RecyclerView) findViewById(R.id.recylerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("user_complaint"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_complaint");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ma;
                    try {
                        ma = snapshot.child("username").getValue().toString();
                    } catch (NullPointerException e) {
                        continue;
                    }
                    if (ma.equals(GlobalVariable.username)) {

                        display();
                    } else
                        continue;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(login.this, "signout", Toast.LENGTH_SHORT).show();
                        GlobalVariable.username = null;
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });


    }

    private void display() {
        recyclerView.setAdapter(mainAdapter);
       /* if(recyclerView.getAdapter().getItemCount() == 0)
        {

        }*/
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

}