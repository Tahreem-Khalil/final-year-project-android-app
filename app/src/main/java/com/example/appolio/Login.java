package com.example.appolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button login;
    EditText emailTextView, passwordTextView;
    String hwEmail, hwPassword;
   // String url = "http://10.0.2.2/fyp/appolio/androidScripts/androidLogin.php";
    public static final String HTTP_URL = "jdbc:mysql://192.168.10.4/appolio";

    public static final String USER = "forandroid";
    public static final String PASS = "forandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginButton);
        login.setOnClickListener(Login.this);

        emailTextView = findViewById(R.id.emailText);
        passwordTextView = findViewById(R.id.passwordText);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        hwEmail = emailTextView.getText().toString().trim();
        hwPassword = passwordTextView.getText().toString().trim();

        if (emailTextView.getText().toString().isEmpty()) {
            emailTextView.setError("Email is not entered");
            emailTextView.requestFocus();
        } else if (passwordTextView.getText().toString().isEmpty()) {
            passwordTextView.setError("Password is not entered");
            passwordTextView.requestFocus();
        } else {
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
            Toast.makeText(Login.this,"Please wait ",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(HTTP_URL, USER, PASS);
                if (conn == null) {
                    msg = "Connection Loss";
                    Toast.makeText(Login.this, "Connection Loss", Toast.LENGTH_SHORT).show();
                } else {
                    String sql=" SELECT health_worker_email, health_worker_password FROM `health worker data` WHERE health_worker_email = '"+hwEmail+"' AND health_worker_password = '"+hwPassword+"'";
                    Statement statement = conn.createStatement();
                    ResultSet result =statement.executeQuery(sql);
                    if (result.next())
                    {
                        msg = "Logged In Successfully";
                        startActivity(new Intent(Login.this,HomeActivity.class));
                    }
                    else{
                        msg = "You are not a registered user";
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
            Toast.makeText(Login.this,""+ msg,Toast.LENGTH_SHORT).show();
        }
    }
}
