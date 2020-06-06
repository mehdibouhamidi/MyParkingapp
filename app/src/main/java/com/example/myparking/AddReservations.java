package com.example.myparking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddReservations extends AppCompatActivity {

    FirebaseFirestore fStore;

    TextView votreprix;
    EditText votreheure;
    Button genererprix;
    EditText parkingvaleur,heurevaleur,jourvaleur;
    TextView prixvaleur;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fStore= FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        parkingvaleur =findViewById(R.id.editTextTextPersonName);
        heurevaleur=findViewById(R.id.editTime);
        jourvaleur=findViewById(R.id.editDate);
        prixvaleur=findViewById(R.id.showprice);


        final Calendar c =Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute1 = c.get(Calendar.MINUTE);
        final EditText txtTime = (EditText) findViewById(R.id.editTime);
        final EditText txtDate = (EditText) findViewById(R.id.editDate);

        votreprix =findViewById(R.id.showprice);
         votreheure =findViewById(R.id.textHeure);

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepick =new TimePickerDialog(AddReservations.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                },hour,minute1,true
                );
                timepick.setTitle("Selectionner le temps");
                timepick.show();
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(AddReservations.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datepicker.setTitle("Selectionner la date");
                datepicker.show();

                genererprix = (Button) findViewById(R.id.generat);
                genererprix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int n1 = Integer.parseInt(votreheure.getText().toString());
                        int nr = n1 * 4;
                        votreprix.setText(String.valueOf(nr));
                    }
                });


                FloatingActionButton fab = findViewById(R.id.fabadd);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String rparking = parkingvaleur.getText().toString();
                        String rheure = heurevaleur.getText().toString();
                        String rjour = jourvaleur.getText().toString();
                        String rprix = prixvaleur.getText().toString();

                        if(rparking.isEmpty()||rheure.isEmpty()||rjour.isEmpty()||rprix.isEmpty()){
                            Toast.makeText(AddReservations.this,"veuillez remplir d'abord les champs",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DocumentReference doref =fStore.collection("reservations").document(user.getUid()).collection("myReservations").document();
                        Map<String,Object> reservation = new HashMap<>();
                        reservation.put("paking",rparking);
                        reservation.put("heure",rheure);
                        reservation.put("jour",rjour);
                        reservation.put("prix",rprix);

                        doref.set(reservation).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddReservations.this,"votre reservation a été ajoutée ",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddReservations.this,"Erreur veullez réssayer ",Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                });
            }

        });

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.close_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.close){
            onBackPressed();
            Toast.makeText(AddReservations.this,"la réservation est non enregistrée",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}