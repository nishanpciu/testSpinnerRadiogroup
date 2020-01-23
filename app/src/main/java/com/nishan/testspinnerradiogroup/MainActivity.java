package com.nishan.testspinnerradiogroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGrooupGender,radioGroupType;
    private RadioButton rbMale,rbFemale,rb_Patient,rb_Specialist,rb_ShopOwner;
    Button btnAdd;

    ProgressDialog loading;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbFemale=findViewById(R.id.rb_female);
        rbMale=findViewById(R.id.rb_male);
        btnAdd=findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // gender();
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

    }
    public void gender(){
        String getGender="";
        if(rbMale.isChecked()){
            getGender="Male";
        }
        else if (rbFemale.isChecked())
        {
            getGender="Female";
        }


        final String gender=getGender;

        if(!rbFemale.isChecked() && !rbMale.isChecked()){
            Toast.makeText(getApplicationContext(),"insert gender",Toast.LENGTH_SHORT).show();
        }
          //  eT_diseasetype.setError("Please enter Disease type !");
         //   requestFocus(eT_diseasetype);        }
        else{
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Adding");
            loading.setMessage("Please wait....");
            loading.show();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.GENDER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("RESPONSE", response);

                    if (response.equals("success")) {
                        loading.dismiss();
                        //Intent intent = new Intent(DiseaseActivity.this, secondActivity.class);
                        Toast.makeText(MainActivity.this, "Disease successful", Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
                    }
                    else if (response.equals("exists")) {

                        Toast.makeText(MainActivity.this, "Disease already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.equals("failure")) {

                        Toast.makeText(MainActivity.this, "Add disease process Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                    loading.dismiss();


                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Constants.KEY_GENDER,gender);
                    Log.d("info"," "+gender);
                    //returning parameter
                    return params;
                }

            };
            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }
    }
    //for request focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

