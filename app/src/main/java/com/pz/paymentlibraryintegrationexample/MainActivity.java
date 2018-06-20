package com.pz.paymentlibraryintegrationexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.pz.standard.checkout.Payment;
import com.pz.standard.checkout.model.PaymentBrand;
import com.pz.standard.checkout.model.PaymentMode;
import com.pz.standard.checkout.model.PaymentResult;
import com.pz.standard.checkout.model.RequestParameters;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String secureKey = "9P8vdzPP4oL9BeDgko3ti6HGnou59LEB";

        // Fill the payment parameters using this java class
        final RequestParameters requestParameters = new RequestParameters();

        // The merchantTransactionId parameter should be unique for each transaction
        String merchantTransactionId = Payment.generateMD5ChecksumDirectKit(String.valueOf(System.nanoTime()));

        // These are the parameters for the VISA brand payment. For the other ones, please check the documentation.
        requestParameters.setMemberId("11344");
        requestParameters.setTerminalId("");
        requestParameters.setMerchantTransactionId(merchantTransactionId);
        requestParameters.setOrderDescription("Testing order");
        requestParameters.setAmount("50.00");
        requestParameters.setTmpl_amount("50.00");
        requestParameters.setCurrency("USD");
        requestParameters.setToType("docspartner");
        requestParameters.setCountry("IN");
        requestParameters.setTmpl_currency("USD");
        requestParameters.setStreet("69, Adity Estate");
        requestParameters.setCity("Mumbai");
        requestParameters.setState("MH");
        requestParameters.setPostCode("400064");
        requestParameters.setPhone("9503620750");
        requestParameters.setEmail("prashant.n@xyz.com");
        requestParameters.setTelnocc("+91");
        requestParameters.setPaymentBrand(PaymentBrand.VISA);
        requestParameters.setPaymentMode(PaymentMode.CC);
        requestParameters.setMerchantRedirectUrl("www.merchantredirecturl.com");
        requestParameters.setHostUrl("https://testurl.com/transaction/PayProcessController");

        Button btnPayNow = (Button) findViewById(R.id.btn_payNow);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Just call this method and wait for the response on the method below(onActivityResult).
                   If you want to call this method from a Fragment, you can still do it without any problem,
                   just some changes will be necessary.*/
                Payment.initPayment(MainActivity.this,requestParameters,secureKey);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // First you need to check the request code
        if(requestCode == Payment.PAYMENT_REQUEST_CODE)
        {
            // After this you need to check the result code
            if(resultCode == RESULT_OK)
            {
                // If its ok, you can get the payment result as described below
                PaymentResult paymentzResult = (PaymentResult) data.getExtras().get(Payment.PAYMENT_RESULT);
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.putExtra("result", paymentzResult.toJsonString());
                startActivity(intent);
            }
        }
    }
}
