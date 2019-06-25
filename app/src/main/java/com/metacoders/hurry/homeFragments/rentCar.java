package com.metacoders.hurry.homeFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurry.R;
import com.metacoders.hurry.model.modelForSpinner;

import java.util.ArrayList;
import java.util.Calendar;

public class rentCar extends AppCompatActivity {


           TextView  datePicker  , timePicker ;
    DatePickerDialog  datePickerDialog ;
    String amPmChecker ;
    Spinner noOfppl , typeOfVehical , citySpinner , TownSpinner , citySpinnerTo , townSpinnerTO  ;
    ArrayAdapter<String>adapter , arrayAdapter ;
    ArrayList<modelForSpinner> locList = new ArrayList<>();
    ArrayList<String> locNameList = new ArrayList<>();
    ArrayList<modelForSpinner> TownList = new ArrayList<>();
    ArrayList<String> TownNameList = new ArrayList<>();

    ArrayList<modelForSpinner> TownListTo = new ArrayList<>();
    ArrayList<String> TownNameListTo = new ArrayList<>();

    ArrayList<modelForSpinner> cityListTo = new ArrayList<>();
    ArrayList<String> CityNameListto = new ArrayList<>();



    Calendar c ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentcar);

        timePicker = findViewById(R.id.timeEdit);
        datePicker = findViewById(R.id.dateEdit);
        noOfppl = findViewById(R.id.no_of_people_spinner) ;
        typeOfVehical = findViewById(R.id.type_of_vehicle_spinner);
        citySpinner = findViewById(R.id.citySpinner) ;
        TownSpinner = findViewById(R.id.townSpinner) ;
        citySpinnerTo = findViewById(R.id.citySpinnerTo) ;
        townSpinnerTO = findViewById(R.id.townSpinnerTo);



        TownSpinner.setVisibility(View.GONE);

        // calling funtion for dwnlding spinner data

        GettingSpinnerDataFromFireBase();
        gettingToCitySpinnerData();


        adapter = new ArrayAdapter<String>(getApplicationContext() , android.R.layout.simple_spinner_item
                ,getResources().getStringArray(R.array.NoOFPPL));
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        noOfppl.setAdapter(adapter);
        noOfppl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String num =    noOfppl.getSelectedItem().toString() ;

                typeOfVehical.setVisibility(View.VISIBLE);

                if(
                    Integer.valueOf(num)>4
                )
                {

                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext() , android.R.layout.simple_spinner_item
                            ,getResources().getStringArray(R.array.vehicleList2));
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                    typeOfVehical.setAdapter(arrayAdapter);


                }
                else if(
                        Integer.valueOf(num)<=4
                )  {

                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext() , android.R.layout.simple_spinner_item
                            ,getResources().getStringArray(R.array.vehicleList));
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
                    typeOfVehical.setAdapter(arrayAdapter);

                }
                else {
                    typeOfVehical.setVisibility(View.INVISIBLE);
                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // date picker
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calling the date Picker

                Calendar c = Calendar.getInstance();

                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(rentCar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        datePicker.setText(dayOfMonth + "/"+(month+1)+"/"+year);

                    }
                } ,year , month , day);

                    datePickerDialog.show();
            }
        });


        //time picker
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling the time picker

                TimePickerDialog timePickerDialog  = new TimePickerDialog(rentCar.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay >= 12){
                            amPmChecker = "AM";

                        }
                        else  {
                            amPmChecker= "PM";
                        }


                        timePicker.setText(hourOfDay+":"+minute+" "+amPmChecker);

                    }
                }, 0,0, false);


                timePickerDialog.show();
            }
        });


    }




    // this function for From Spinners

    public  void GettingSpinnerDataFromFireBase(){

        DatabaseReference deptReference = FirebaseDatabase.getInstance().getReference("city");
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                locList.clear();
                locNameList.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    modelForSpinner departments = deptSnapshot.getValue(modelForSpinner.class);
                    //adding department to the list
                    locList.add(departments);
                }

                if(locList.size() > 0){
                    for(int i=0; i<locList.size(); i++){
                        locNameList.add(locList.get(i).getName());
                    }
                }

                //creating adapter
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rentCar.this, android.R.layout.simple_list_item_activated_1, locNameList);
                citySpinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                    TownSpinner.setVisibility(View.VISIBLE);

                    GettingSpinnerOfTown(locList.get(position).getName());



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public  void GettingSpinnerOfTown(String db){

        DatabaseReference deptReference = FirebaseDatabase.getInstance().getReference("town").child(db);
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TownList.clear();
                TownNameList.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    modelForSpinner departments = deptSnapshot.getValue(modelForSpinner.class);
                    //adding department to the list
                    TownList.add(departments);
                }

                if(TownList.size() > 0){
                    for(int i=0; i<TownList.size(); i++){
                        TownNameList.add(TownList.get(i).getName());
                    }
                }

                //creating adapter
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rentCar.this, android.R.layout.simple_list_item_activated_1, TownNameList);
                TownSpinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        TownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            //    TownSpinner.setVisibility(View.VISIBLE);

           //     GettingSpinnerOfTown(locList.get(position).getName());



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

// this function for To Spinners

    public  void  gettingToCitySpinnerData(){

        DatabaseReference deptReference = FirebaseDatabase.getInstance().getReference("city");
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cityListTo.clear();
                CityNameListto.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    modelForSpinner departments = deptSnapshot.getValue(modelForSpinner.class);
                    //adding department to the list
                    cityListTo.add(departments);
                }

                if(cityListTo.size() > 0){
                    for(int i=0; i<cityListTo.size(); i++){
                        CityNameListto.add(cityListTo.get(i).getName());
                    }
                }

                //creating adapter
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rentCar.this, android.R.layout.simple_list_item_activated_1, CityNameListto);
                citySpinnerTo.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        citySpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                townSpinnerTO.setVisibility(View.VISIBLE);

                GettingToSpinnerOfTown(cityListTo.get(position).getName());



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void GettingToSpinnerOfTown(String name) {

        DatabaseReference deptReference = FirebaseDatabase.getInstance().getReference("town").child(name);
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TownListTo.clear();
                TownNameListTo.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    modelForSpinner departments = deptSnapshot.getValue(modelForSpinner.class);
                    //adding department to the list
                    TownListTo.add(departments);
                }

                if(TownListTo.size() > 0){
                    for(int i=0; i<TownListTo.size(); i++){
                        TownNameListTo.add(TownListTo.get(i).getName());
                    }
                }

                //creating adapter
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rentCar.this, android.R.layout.simple_list_item_activated_1, TownNameListTo);
                townSpinnerTO.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        townSpinnerTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                //    TownSpinner.setVisibility(View.VISIBLE);

                //     GettingSpinnerOfTown(locList.get(position).getName());



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


}
