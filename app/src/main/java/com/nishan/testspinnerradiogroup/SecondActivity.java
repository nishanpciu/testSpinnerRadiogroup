package com.nishan.testspinnerradiogroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {


   EditText etxt_Name,etxt_Password,etxt_email;
    Button btnADD;
ProgressDialog loading;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etxt_email=findViewById(R.id.etxt_Email);
        etxt_Name=findViewById(R.id.etxt_name);
        etxt_Password=findViewById(R.id.etxt_password);
        btnADD=findViewById(R.id.btnAdd);

        btnADD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sign_Up();

            }
        });
    }
    public void sign_Up(){
        final String email=etxt_email.getText().toString().trim();
        final String password=etxt_Password.getText().toString().trim();
        final String name=etxt_Name.getText().toString().trim();

        if (email.isEmpty()){
            etxt_email.setError("Please enter your  email !");
            requestFocus(etxt_email);
        }
        else if(password.isEmpty()){
            etxt_Password.setError("Please enter your password !");
            requestFocus(etxt_Password);
        }
        else if(name.isEmpty()){
            etxt_Name.setError("Please enter your  name !");
            requestFocus(etxt_Name);
        }
        else {
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Sign up");
            loading.setMessage("Please wait....");
            loading.show();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.GENDER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("RESPONSE", response);

                    if (response.equals("success")) {
                        loading.dismiss();
                       // Intent intent = new Intent(MainActivity.this, secondActivity.class);
                        Toast.makeText(SecondActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                       // startActivity(intent);
                    }
                    else if (response.equals("exists")) {

                        Toast.makeText(SecondActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.equals("failure")) {

                        Toast.makeText(SecondActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SecondActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                    loading.dismiss();

                }
            }
            ){
                @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constants.KEY_NAME,name);
                params.put(Constants.KEY_GMAIL,email);
                params.put(Constants.KEY_PASSWORD,password);
                // params.put(Constants.KEY_GENDER,gender);
                //  params.put(Constants.KEY_GENDER, gender);



                Log.d("info",""+name+" "+email+" "+password);
                //  Log.d("info",""+name+" "+cell);


                //returning parameter
                return params;
            }

        };
            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
