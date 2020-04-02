package com.example.petinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddStudent extends AppCompatActivity {

    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    private Button addStudentConfirm;

    private EditText name;
    private EditText phone;
    private EditText id;
    private EditText email;

    private String[] availableBranches = {" Course "," Other "," PH.D (CE) "," PH.D (ECE) ", " PH.D (ME) ", " PH.D (CSE) "," M.Tech (CSE) "," M.Tech (CE) "," M.Tech (ECE) "," B.Tech (CSE) "," B.Tech (CE) "," B.Tech (ME) "," B.Tech (EEE) "," B.Tech (ECE) "," B.Tech (LEET) "};
    private String[] yearOfCourse = {" Year "," N/A"," 5th Year ", " 4th Year ", " 3rd Year ", " 2nd Year ", " 1st Year "};

    private Spinner branch;
    private Spinner year;
    private String branchName,currentYear;
    private String gender="Male";

    private String attendance = "On Working";

    private DatabaseHelperClass myDb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        getSupportActionBar().setTitle("Enter Student Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DatabaseHelperClass(this); // dataBase constructor is calling here

        name = (EditText) findViewById(R.id.add_student_name);
        phone = (EditText) findViewById(R.id.add_student_phone);
        id = (EditText) findViewById(R.id.add_student_id);
        email = (EditText) findViewById(R.id.add_student_email);

        branch = (Spinner) findViewById(R.id.spinner_branch);
        year = (Spinner) findViewById(R.id.spinner_year);

        radioGenderGroup = (RadioGroup) findViewById(R.id.radio_gender);
        radioGenderGroup.check(R.id.radio_male);
        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_male){
                    gender="Male";
                }else{
                    gender="Female";
                }

            }
        });

        addStudentConfirm = (Button) findViewById(R.id.add_student_button);
        addStudentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGenderGroup.getCheckedRadioButtonId();
                radioGenderButton = (RadioButton) findViewById(selectedId);
                if(!validation(name.getText().toString(),phone.getText().toString(),id.getText().toString(),email.getText().toString(),v)){
                    return;
                }

                boolean isInserted = myDb.insertData(id.getText().toString(), name.getText().toString(),gender,phone.getText().toString(),email.getText().toString(),branchName,currentYear,attendance);
                if(isInserted == true){
                    Toast.makeText(AddStudent.this, "Student Added", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddStudent.this, "Student Not Added", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("data",new Student(name.getText().toString(),phone.getText().toString(),gender,id.getText().toString(),email.getText().toString(),branchName,currentYear,attendance));
                setResult(RESULT_OK,intent);

                finish();
            }
        });

//Spinner Code

        //Creating the ArrayAdapter instance having the branch name list
        ArrayAdapter aaBranch = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, availableBranches);
        aaBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        branch.setAdapter(aaBranch);
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branchName=availableBranches[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter aaYear = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, yearOfCourse);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(aaYear);

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentYear=yearOfCourse[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private Boolean validation(String name,String phone,String id,String email,View v) {
        if(name.isEmpty()&&phone.isEmpty()&&id.isEmpty()&&email.isEmpty()){
            Snackbar.make(v,"Fields should not be empty",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (name.toString().isEmpty()) {
            Snackbar.make(v,"Name should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (phone.toString().isEmpty()) {
            Snackbar.make(v,"Phone should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;

        }

        if (id.toString().isEmpty()) {
            Snackbar.make(v,"Id should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (email.toString().isEmpty()) {
            Snackbar.make(v,"E-Mail should not be EMPTY",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }

        if (branchName.toString() == " Course ") {
            Snackbar.make(v,"Select Course",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        if (currentYear.toString() == " Year ") {
            Snackbar.make(v,"Select Year",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            return false;
        }
        return true;
    }

}
