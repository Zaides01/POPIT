package com.example.popit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText email, password;

    Button reg, sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        reg = findViewById(R.id.btn_registration);
        sign = findViewById(R.id.btn_sign_in);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                else Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(email.getText().toString(), password.getText().toString());
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signing(email.getText().toString(), password.getText().toString());
            }
        });
    }
    public void signing (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) Toast.makeText(MainActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();
            }
        });
   }

        public void registration (String email, String password){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) Toast.makeText(MainActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
        }

}