package com.example.royaljewels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {

    EditText entre;
    Button otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entre=findViewById(R.id.ednum);
        otp=findViewById(R.id.btnotp);




        ProgressBar progressBar=findViewById(R.id.prologin);

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!entre.getText().toString().trim().isEmpty()){
                    if ((entre.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        otp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + entre.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                        login.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);
                                        Toast.makeText(login.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);
                                        Intent intent=new Intent(getApplicationContext(),login_otp.class);
                                        intent.putExtra("mobile",entre.getText().toString());
                                        intent.putExtra("backenotp",backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );
                    }else {
                        Toast.makeText(login.this,"Please enter correct number",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(login.this,"Enter Mobile Number",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}