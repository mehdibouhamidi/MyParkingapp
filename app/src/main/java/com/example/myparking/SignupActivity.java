package com.example.myparking;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText signup_username, signup_email,signup_pass,signup_pass_confim,matricule;
    ImageView butlogin;
    TextView sloginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;


    private long backPressedTime;
    private Toast backToast;


    @Override
    public void onBackPressed() {


        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();

            return;

        }else{
            backToast = Toast.makeText(getBaseContext(),"Tapez une autre fois pour quitter",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_username = findViewById(R.id.signup_username);
        signup_email = findViewById(R.id.signup_email);
        signup_pass = findViewById(R.id.signup_pass);
        signup_pass_confim = findViewById(R.id.signup_pass_confim);
        matricule = findViewById(R.id.matricule);
        butlogin = findViewById(R.id.butlogin);
        sloginBtn = findViewById(R.id.sloginBtn);

        progressBar = findViewById(R.id.progressBar_log);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        sloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(myIntent);
            }
        });

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }

        butlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = signup_email.getText().toString();
                String password = signup_pass.getText().toString().trim();
                String password_conf = signup_pass_confim.getText().toString().trim();
                final String username = signup_username.getText().toString();
                final String matricule_vehicule = matricule.getText().toString();
                if(TextUtils.isEmpty(email)){
                    signup_email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    signup_pass.setError("password is required");
                    return;
                }
                if(TextUtils.isEmpty(password_conf)){
                    signup_pass_confim.setError("password confirmation is required");
                    return;
                }
                if(password.length()<6){
                    signup_pass.setError("password must be >6 characters");
                    return;
                }
              if(!password.equals(password_conf)){
                    signup_pass_confim.setError("password is not the same ");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //enregistrement d'utilisateur

                fAuth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "succues", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("Utilisateurs").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("username",username);
                                user.put("matricule",matricule_vehicule);
                                user.put("email",email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignupActivity.this, "success create", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),Home.class));
                                finish();
                            }else{
                                Toast.makeText(SignupActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                    }
                });


                }

            }
        );
    }}


