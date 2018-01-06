package com.paymentz.sdk.paymentzlibraryintegrationexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paymentz.standard.checkout.Paymentz;
import com.paymentz.standard.checkout.model.PaymentBrand;
import com.paymentz.standard.checkout.model.PaymentMode;
import com.paymentz.standard.checkout.model.PaymentzResult;
import com.paymentz.standard.checkout.model.RequestParameters;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Unique key for each merchant
        final String secureKey = "bzI93aEQeYDeE50Pa929NiDk3us8XTbU";

        // Fill the payment parameters using this java class
        final RequestParameters requestParameters = new RequestParameters();

        // The merchantTransactionId parameter should be unique for each transaction
        String merchantTransactionId = Paymentz.generateMD5ChecksumDirectKit(String.valueOf(System.nanoTime()));

        // These are the parameters for the VISA brand payment. For the other ones, please check the documentation.
        requestParameters.setMemberId("10558");
        requestParameters.setPaymentMode(PaymentMode.CC);
        requestParameters.setTerminalId("664");
        requestParameters.setMerchantTransactionId(merchantTransactionId);
        requestParameters.setOrderDescription("Testing order");
        requestParameters.setAmount("50.00");
        requestParameters.setTmpl_amount("50.00");
        requestParameters.setCurrency("USD");
        requestParameters.setToType("paymentz");
        requestParameters.setCountry("IN");
        requestParameters.setTmpl_currency("USD");
        requestParameters.setStreet("69, Adity Estate");
        requestParameters.setCity("Mumbai");
        requestParameters.setState("MH");
        requestParameters.setPostCode("400064");
        requestParameters.setPhone("9503620750");
        requestParameters.setEmail("prashant.n@paymentz.com");
        requestParameters.setPaymentBrand(PaymentBrand.VISA);
        requestParameters.setMerchantRedirectUrl("www.paymentz.com");

        Button btnPayNow = (Button) findViewById(R.id.btn_payNow);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Just call this method and wait for the response on the method below(onActivityResult).
                   If you want to call this method from a Fragment, you can still do it without any problem,
                   just some changes will be necessary.*/
                Paymentz.initPayment(MainActivity.this,requestParameters,secureKey);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // First you need to check the request code
        if(requestCode == Paymentz.PAYMENT_REQUEST_CODE)
        {
            // After this you need to check the result code
            if(resultCode == RESULT_OK)
            {
                // If its ok, you can get the payment result as described below
                PaymentzResult paymentzResult = (PaymentzResult) data.getExtras().get(Paymentz.PAYMENT_RESULT);
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.putExtra("result", paymentzResult.toJsonString());
                startActivity(intent);
            }
        }
    }
}
