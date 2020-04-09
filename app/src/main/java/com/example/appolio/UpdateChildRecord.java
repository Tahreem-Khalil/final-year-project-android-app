package com.example.appolio;

import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateChildRecord extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_update_child_record);


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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    /**
     *
     */
    private class Send extends AsyncTask<String,String,String> {

        String msg = "";
        @Override
        protected void onPreExecute() {
            Toast.makeText(UpdateChildRecord.this,"Please wait ",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(HTTP_URL, USER, PASS);
                if (conn == null) {
                    msg = "Connection Loss";
                    Toast.makeText(UpdateChildRecord.this, "Connection Loss", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(UpdateChildRecord.this,""+ msg,Toast.LENGTH_SHORT).show();
        }
    }

}
