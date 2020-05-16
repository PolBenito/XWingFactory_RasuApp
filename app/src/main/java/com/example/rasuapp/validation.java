package com.example.rasuapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.simplifiedlearning.simplifiedcoding.LoggedUser;
import net.simplifiedlearning.simplifiedcoding.URLs;
import net.simplifiedlearning.simplifiedcoding.User;
import net.simplifiedlearning.simplifiedcoding.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

public class validation extends AppCompatActivity {

    short[] idInstr;
    String[] nomsTasques = new String[2];
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        TextView pieza1TB = (TextView) findViewById(R.id.pieza1);
        TextView pieza2TB = (TextView) findViewById(R.id.pieza2);

        short[] validate = {1,2,3};
        validate = Validation();
        Instructions(validate);

        pieza1TB.setText(nomsTasques[0]);
        pieza2TB.setText(nomsTasques[1]);

        pieza1TB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(validation.this, pieceinfo.class);
                startActivity(intent);
            }
        });
    }

    public short[] Validation() {
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_DAILYUSER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        short idUsuariDiari = (short) response.getJSONObject(i).getInt("idDailyUser");
                        short idUsuari = (short) response.getJSONObject(i).getInt("idUser");
                        short idInstrucEnssamblatge = (short) response.getJSONObject(i).getInt("idAssemblyInstructions");
                        if (idUsuari == (User.getId())) {
                            Intent intent = new Intent(validation.this, validation.class);
                            startActivity(intent);
                            LoggedUser UserLogged = new LoggedUser(idUsuariDiari, idUsuari, idInstrucEnssamblatge);
                            idInstr[count] = idInstrucEnssamblatge;
                            count++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(validation.this, "Error 1" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }},
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(validation.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        return idInstr;
    }

    public void Instructions(final short[] instruction){
        JsonArrayRequest validationRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_ASSEMBLYINSTR, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int contador = 0;
                for (int i = 0; i < response.length(); i++){
                    try {
                        Toast.makeText(validation.this, "Error CATCH" + response, Toast.LENGTH_SHORT).show();
                        short idInstrEnsamblatgeDetall = (short) response.getJSONObject(i).getInt("idAssemblyInstructions");
                        String CodiOperacio = response.getJSONObject(i).getString("CodeOperation");
                        for (int count = 0; count < instruction.length; count++)
                        {
                            if (idInstrEnsamblatgeDetall == instruction[count]) {
                                nomsTasques[contador]=CodiOperacio;
                                contador++;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(validation.this, "Error CATCH" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }},
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(validation.this, "Error 2" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(validationRequest);
    }
}
