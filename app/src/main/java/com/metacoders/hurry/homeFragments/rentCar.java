package com.metacoders.hurry.homeFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.metacoders.hurry.R;

import java.util.Calendar;

public class rentCar extends AppCompatActivity {


           TextView  datePicker  , timePicker ;
    DatePickerDialog  datePickerDialog ;
    String amPmChecker ;
    Spinner noOfppl , typeOfVehical ;
    ArrayAdapter<String>adapter , arrayAdapter ;



    Calendar c ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentcar);

        timePicker = findViewById(R.id.timeEdit);
        datePicker = findViewById(R.id.dateEdit);
        noOfppl = findViewById(R.id.no_of_people_spinner) ;
        typeOfVehical = findViewById(R.id.type_of_vehicle_spinner);



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
}
