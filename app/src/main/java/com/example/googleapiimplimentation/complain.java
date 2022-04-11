package com.example.googleapiimplimentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class complain extends AppCompatActivity {
     TextInputLayout employeeNameEdt, employeePhoneEdt, employeeAddressEdt,pin,dat;
     Button maps;
     Button sendDatabtn;
    Button cam;
    TextView tv;
    // creating a variable for our

    // Firebase Database.

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // creating a variable for our Database

    // Reference for Firebase.

    //DatabaseReference databaseReference;


    // creating a variable for

    // our object class

    //user_complaint user_complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        employeeNameEdt = findViewById(R.id.editTextTextPersonName);

        employeePhoneEdt = findViewById(R.id.editTextTextPersonName2);

        employeeAddressEdt = findViewById(R.id.editTextTextPersonName3);
        pin=findViewById(R.id.pincode);
        dat=findViewById(R.id.date);
        cam = (Button) findViewById(R.id.cam);
        maps = (Button) findViewById(R.id.maps);
        tv=(TextView)findViewById(R.id.textView2);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        // below line is used to get the

        // instance of our FIrebase database.

        //firebaseDatabase=firebaseDatabase.getInstance();
        //databaseReference=firebaseDatabase.getReference("user_complaint");


        // below line is used to get reference for our database.


        // initializing our object

        // class variable.

        //user_complaint= new user_complaint();
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(complain.this, camera.class);
                startActivity(intent);

            }
        });
        sendDatabtn = findViewById(R.id.button);

        sendDatabtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                firebaseDatabase = firebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("user_complaint");
                String name = employeeNameEdt.getEditText().getText().toString();

                String phone = employeePhoneEdt.getEditText().getText().toString();

                String address = employeeAddressEdt.getEditText().getText().toString();
                String Image = GlobalVariable.photoid;
                String status=tv.getText().toString();
                String pincode=pin.getEditText().getText().toString();
                String date=dat.getEditText().getText().toString();
                // getting text from our edittext fields.
                if(TextUtils.isEmpty(name)){
                    employeeNameEdt.setError("NAME is required");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    employeePhoneEdt.setError("PHONE is required");
                    return;
                }
                if(phone.length()<10){
                    employeePhoneEdt.setError("MOBILE NUMBER must be OF 10 digits");
                    return;
                }

                user_complaint userhelper = new user_complaint(name, phone, address,Image,status,pincode,date);
                databaseReference.child(phone).setValue(userhelper);
                Toast.makeText(complain.this, "COMPLAIN REGISTERED", Toast.LENGTH_SHORT).show();
                // below line is for checking weather the

                // edittext fields are empty or not.
                /*if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {

                    // if the text fields are empty

                    // then show the below message.

                    Toast.makeText(complain.this, "Please add some data.", Toast.LENGTH_SHORT).show();

                } else {

                    // else call the method to add

                    // data to our database.

                    addDatatoFirebase(name, phone, address);

                }*/

            }

        });

    }


  /*private void addDatatoFirebase(String name, String phone, String address) {

                    // below 3 lines of code is used to set

                    // data in our object class.

                    user_complaint.setName(name);

                    user_complaint.setMobile(phone);

                    user_complaint.setAddress(address);



                    // we are use add value event listener method

                    // which is called with database reference.

                    databaseReference.addValueEventListener(new ValueEventListener() {

                        @Override

                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // inside the method of on Data change we are setting

                            // our object class to our database reference.

                            // data base reference will sends data to firebase.

                            databaseReference.setValue(user_complaint);



                            // after adding this data we are showing toast message.

                            Toast.makeText(complain.this, "data added", Toast.LENGTH_SHORT).show();
                        }


                        @Override

                        public void onCancelled(@NonNull DatabaseError error) {

                            // if the data is not added or it is cancelled then

                            // we are displaying a failure toast message.

                            Toast.makeText(complain.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();


                        }

                    });

    }*/
}