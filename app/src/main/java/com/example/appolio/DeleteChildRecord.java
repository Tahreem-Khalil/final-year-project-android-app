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

public class DeleteChildRecord extends AppCompatActivity implements View.OnClickListener {

    Button deletechildButton;
    EditText deletebformeditext;
    String bFormDeleteTextData;

    public static final String HTTP_URL = "jdbc:mysql://192.168.10.4/appolio";
    public static final String USER = "forandroid";
    public static final String PASS = "forandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_child_record);

        deletechildButton= (Button) findViewById(R.id.deletechildButton);
        deletechildButton.setOnClickListener(this);

        deletebformeditext=findViewById(R.id.deletebformeditext);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (deletebformeditext.getText() != null && deletebformeditext.getText().toString().length() == 13) {

            bFormDeleteTextData = deletebformeditext.getText().toString().trim();
            Send objSend = new Send();
            objSend.execute("");

        }else{

            Toast.makeText(DeleteChildRecord.this, "Enter a Valid Cnic and Make Sure that the" +
                    " child is already in the record", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     *
     */
    private class Send extends AsyncTask<String,String,String> {

        String msg = "";
        @Override
        protected void onPreExecute() {
            Toast.makeText(DeleteChildRecord.this,"Please wait ",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(HTTP_URL, USER, PASS);
                if (conn == null) {
                    msg = "Connection Loss";
                    Toast.makeText(DeleteChildRecord.this, "Connection Loss", Toast.LENGTH_SHORT).show();
                } else {
                    String query="DELETE FROM child_data WHERE Child_Bform_no = '"+bFormDeleteTextData+"'";

                    Statement statement = conn.createStatement();
                    statement.executeUpdate(query);
                        msg = "Record Deleted Successfully";
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
            Toast.makeText(DeleteChildRecord.this,""+ msg,Toast.LENGTH_SHORT).show();
        }
    }

}
