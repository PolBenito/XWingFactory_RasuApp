package com.example.rasuapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.simplifiedlearning.simplifiedcoding.URLs;
import net.simplifiedlearning.simplifiedcoding.User;
import net.simplifiedlearning.simplifiedcoding.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    private EditText etusername, etpassword;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }*/

        etusername = findViewById(R.id.editTextUsername);
        etpassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mUsername = etusername.getText().toString().trim();
                String mPass = etpassword.getText().toString().trim();

                if(!mUsername.isEmpty() || !mPass.isEmpty()){
                    Login();
                } else {
                    etusername.setError("Si us plau, introdueix l'usuari");
                    etpassword.setError("Si us plau, introdueix la contrasenya");
                }
            }
        });
    }

    public void Login() {
        final String username = etusername.getText().toString();
        final String password = etpassword.getText().toString();

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_LOGIN, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                boolean haEntrat = false;
                for (int i = 0; i < response.length(); i++)
                {
                    try {
                        short id = (short) response.getJSONObject(i).getInt("idUser");
                        String usuari = response.getJSONObject(i).getString("UserName");
                        String contrasenya = response.getJSONObject(i).getString("password");
                            if (usuari.equals(username) && contrasenya.equals(password)) {
                                haEntrat = true;
                                User realUser = new User(id, usuari, contrasenya);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (!haEntrat) {
                            Toast.makeText(login.this, "ERROR D'USUARI O CONTRASENYA", Toast.LENGTH_SHORT).show();
                            etusername.setText("");
                            etpassword.setText("");
                        }
                    }
                }
        }},
        new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) { btnlogin.setVisibility(View.VISIBLE);
                    Toast.makeText(login.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                    protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
