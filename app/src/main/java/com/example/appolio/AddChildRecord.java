package com.example.appolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddChildRecord extends AppCompatActivity implements View.OnClickListener{

    Button addVaccinationHistory,addChildRecordButton,addCoordinatesButton;
    EditText bFormChildEditext,childNameedittext,dateofbirtheditext,gendereditext,adresseditext,
             mothernameeditext,fathernameeditext,motherCNICeditext,fatherCNICeditext,parentcontacteditext,healthWorkerCnicEditext;
    String bFormTextData,childNameData,dateOfBirthData,genderData,addressData,motherNameData,fatherNameData,motherCnicData
            ,fatherCnicData,parentContactData,healthWorkerCnicData;

    public static final String HTTP_URL = "jdbc:mysql://192.168.10.4/appolio";
    public static final String USER = "forandroid";
    public static final String PASS = "forandroid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_record);

        addVaccinationHistory= (Button) findViewById(R.id.vaccinationHistoryButton);
        addVaccinationHistory.setOnClickListener(this);

        addChildRecordButton= (Button) findViewById(R.id.addChildRecordInDatabaseButton);
        addChildRecordButton.setOnClickListener(this);

        addCoordinatesButton= (Button) findViewById(R.id.addCordinatesOfChildButton);
        addCoordinatesButton.setOnClickListener(this);

        bFormChildEditext=findViewById(R.id.childBformEditext);
        childNameedittext=findViewById(R.id.childNameedittext);
        dateofbirtheditext=findViewById(R.id.dateofbirtheditext);
        gendereditext=findViewById(R.id.gendereditext);
        adresseditext=findViewById(R.id.adresseditext);
        mothernameeditext=findViewById(R.id.mothernameeditext);
        fathernameeditext=findViewById(R.id.fathernameeditext);
        motherCNICeditext=findViewById(R.id.motherCNICeditext);
        fatherCNICeditext=findViewById(R.id.fatherCNICeditext);
        parentcontacteditext=findViewById(R.id.parentcontacteditext);
        healthWorkerCnicEditext=findViewById(R.id.healthWorkerCnicEditext);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addChildRecordInDatabaseButton:
                validateChildData();
                break;
            case R.id.addCordinatesOfChildButton:
                if (bFormTextData!= null){
                    bFormTextData=bFormChildEditext.getText().toString();
                }
                Intent toAddLocationActivity=new Intent(AddChildRecord.this, MapViewofChild.class);
                Bundle bundle = new Bundle();
                bundle.putString("childCnic", bFormTextData);
                toAddLocationActivity.putExtras(bundle);
                startActivity(toAddLocationActivity);
                break;
            case R.id.vaccinationHistoryButton:
                Intent toVaccinationHistoryActivity=new Intent(AddChildRecord.this, AddVaccinationHistory.class);
                startActivity(toVaccinationHistoryActivity);
                break;
        }
    }

    /**
     *
     *
     */
    private void validateChildData() {


        bFormTextData = bFormChildEditext.getText().toString().trim();
        childNameData = childNameedittext.getText().toString().trim();
        dateOfBirthData = dateofbirtheditext.getText().toString().trim();
        genderData = gendereditext.getText().toString().trim();
        addressData = adresseditext.getText().toString().trim();
        motherNameData = mothernameeditext.getText().toString().trim();
        fatherNameData = fathernameeditext.getText().toString().trim();
        motherCnicData = motherCNICeditext.getText().toString().trim();
        fatherCnicData = fatherCNICeditext.getText().toString().trim();
        parentContactData = parentcontacteditext.getText().toString().trim();
        healthWorkerCnicData = healthWorkerCnicEditext.getText().toString().trim();

        if (bFormChildEditext.getText().toString().isEmpty()) {
            bFormChildEditext.setError("BForm no is not entered");
            bFormChildEditext.requestFocus();
        } else if (bFormChildEditext.getText().toString().length() != 13) {
            bFormChildEditext.setError("Enter valid 13 digit Bform no");
            bFormChildEditext.requestFocus();
        } else if (childNameedittext.getText().toString().isEmpty()) {
            childNameedittext.setError("child name is not entered");
            childNameedittext.requestFocus();
        } else if (dateofbirtheditext.getText().toString().isEmpty()) {
            dateofbirtheditext.setError("date of birth is not entered");
            dateofbirtheditext.requestFocus();
        } else if (gendereditext.getText().toString().isEmpty()) {
            gendereditext.setError("gender is not entered");
            gendereditext.requestFocus();
        } else if (adresseditext.getText().toString().isEmpty()) {
            adresseditext.setError("address is not entered");
            adresseditext.requestFocus();
        } else if (mothernameeditext.getText().toString().isEmpty()) {
            mothernameeditext.setError("mother name is not entered");
            mothernameeditext.requestFocus();
        } else if (fathernameeditext.getText().toString().isEmpty()) {
            fathernameeditext.setError("father name is not entered");
            fathernameeditext.requestFocus();
        } else if (motherCNICeditext.getText().toString().isEmpty()) {
            motherCNICeditext.setError("mother cnic is not entered");
            motherCNICeditext.requestFocus();
        } else if (motherCNICeditext.getText().toString().length() != 13) {
            motherCNICeditext.setError("Enter valid 13 digit Bform no");
            motherCNICeditext.requestFocus();
        } else if (fatherCNICeditext.getText().toString().isEmpty()) {
            fatherCNICeditext.setError("father cnic is not entered");
            fatherCNICeditext.requestFocus();
        } else if (fatherCNICeditext.getText().toString().length() != 13) {
            fatherCNICeditext.setError("Enter valid 13 digit Bform no");
            fatherCNICeditext.requestFocus();
        } else if (parentcontacteditext.getText().toString().isEmpty()) {
            parentcontacteditext.setError("parents contact no is not entered");
            parentcontacteditext.requestFocus();
        } else if (parentcontacteditext.getText().toString().length() != 10) {
            parentcontacteditext.setError("enter a valid 10 digit contact no");
            parentcontacteditext.requestFocus();
        } else if (healthWorkerCnicEditext.getText().toString().isEmpty()) {
            healthWorkerCnicEditext.setError("health worker cnic is not entered");
            healthWorkerCnicEditext.requestFocus();
        } else if (healthWorkerCnicEditext.getText().toString().length() != 13) {
            healthWorkerCnicEditext.setError("enter a valid 13 digit cnic");
            healthWorkerCnicEditext.requestFocus();
        }
        else {
            Send objSend = new Send();
            objSend.execute("");
        }
    }


    /**
     *
     */
    private class Send extends AsyncTask<String,String,String> {

        String msg = "";
        @Override
        protected void onPreExecute() {
            Toast.makeText(AddChildRecord.this,"Please wait ",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(HTTP_URL, USER, PASS);
                if (conn == null) {
                    msg = "Connection Loss";
                    Toast.makeText(AddChildRecord.this, "Connection Loss", Toast.LENGTH_SHORT).show();
                } else {
                    String query="INSERT INTO child_data (Child_Bform_no, Name, Date_of_Birth, Gender, Address, Child_Father_Name, Child_Mother_Name, Child_Father_CNIC, Child_Mother_CNIC," +
                            " parent_contact_no,health_worker_CNIC) VALUES ('"+bFormTextData+"','"+childNameData+"','"+genderData+"','"+addressData+"','"+fatherNameData+"'," +
                            "'"+motherNameData+"','"+fatherCnicData+"','"+motherCnicData+"','"+parentContactData+"','"+healthWorkerCnicData+"')";

                    Statement statement = conn.createStatement();
                    ResultSet result =statement.executeQuery(query);
                    if (result.next())
                    {
                        msg = "Record Entered Successfully";
                    }
                    else{
                        msg = "Failed To Enter Record";
                    }
                }
                assert conn != null;
                conn.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(AddChildRecord.this,""+ msg,Toast.LENGTH_SHORT).show();
        }
    }
}
