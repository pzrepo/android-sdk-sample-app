package com.pz.paymentlibraryintegrationexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentSuccessActivity extends AppCompatActivity
{
    private TextView demoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        demoText = (TextView)findViewById(R.id.demoText);
        String result = getIntent().getExtras().getString("result");
        try
        {
            JSONObject paymentResultJson = new JSONObject(result);
            demoText.setText("Tracking Id: "+paymentResultJson.getString("trackingId")+"\n"+
                    "desc: "+paymentResultJson.getString("desc")+"\n"+
                    "Amount: "+paymentResultJson.getString("amount")+"\n"+
                    "Status: "+paymentResultJson.getString("status"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
