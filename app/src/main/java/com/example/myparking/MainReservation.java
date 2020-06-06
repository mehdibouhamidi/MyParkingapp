package com.example.myparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myparking.model.Reservation;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class MainReservation extends AppCompatActivity {
    RecyclerView reservationLists;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Reservation,ReservationViewHolder> reservationAdapter;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reservation);

        reservationLists = findViewById(R.id.reservationList);


        Toolbar toolbar = findViewById(R.id.reservationDetailsTitle);
        setSupportActionBar(toolbar);
        //get the back button bar in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //and after that generate the method to use the button when you click in it the method called "onOptionItemSelected" and make condition if the user click

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

       Query query = fStore.collection("reservations").document(user.getUid()).collection("myReservations").orderBy("paking", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Reservation> allReservations;
        allReservations = new FirestoreRecyclerOptions.Builder<Reservation>()
                .setQuery(query, Reservation.class)
              .build();

        reservationAdapter = new FirestoreRecyclerAdapter<Reservation, ReservationViewHolder>(allReservations) {
            @NonNull
            @Override
            public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_view_layout, parent, false);
                return new ReservationViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull ReservationViewHolder reservationViewHolder, int i, @NonNull final Reservation reservation) {
                reservationViewHolder.parkingTitle.setText(reservation.getParking());
                reservationViewHolder.dayContent.setText(reservation.getJour());
                reservationViewHolder.timeContent.setText(reservation.getHeure());
                reservationViewHolder.priceContent.setText(reservation.getPrix());
                //si l'utilisateur clique sur un ITEM
                reservationViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "VOUS AVEZ CLIQUER SUR UN ITEM", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext(), ReservationDetails.class);
                        i.putExtra("parking", reservation.getParking());
                        i.putExtra("day", reservation.getJour());
                        i.putExtra("time", reservation.getHeure());
                        i.putExtra("price", reservation.getPrix());
                        v.getContext().startActivity(i);
                    }


                });


            }
        };


         /* pour tester avec la classe nomer Adpapter

                List<String> parking = new ArrayList<>();
                List<String> time = new ArrayList<>();
                List<String> day = new ArrayList<>();
                List<String> price = new ArrayList<>();

                ///juste des exemples pour des reservations il faut les remplacer par des données de la firebase apres note : attention le nombre de donnee ajoutee doit etre le meme
                parking.add("park 1");
                time.add("21:14:33");
                day.add("45/12/2021");
                price.add("25 dh");

                parking.add("park 2");
                time.add("08:20:33");
                day.add("01/12/2021");
                price.add("7 dh");

                parking.add("park 1");
                time.add("21:14:33");
                day.add("3/07/2021");
                price.add("2 dh");
                ////////cece en haut est juste un exemple , ensuite il faut instancier la class adapter que nous avons créer
                adapter = new Adapter(parking, time, day, price); */
        reservationLists.setLayoutManager(new LinearLayoutManager(MainReservation.this));//pour les cardView sous forme de ligne


        //reservationList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));//POUR 2COLONNE
        reservationLists.setAdapter(reservationAdapter);


        FloatingActionButton fab = findViewById(R.id.floatingmain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainReservation.this, "Sauvegarder", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(), AddReservations.class));
            }
        });

    }
    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView parkingTitle, timeContent, dayContent, priceContent;
        View view;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingTitle = itemView.findViewById(R.id.Res_Park);
            timeContent = itemView.findViewById(R.id.Time_Park);
            dayContent = itemView.findViewById(R.id.Date_Park);
            priceContent = itemView.findViewById(R.id.Price_Park);
            view = itemView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        reservationAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(reservationAdapter != null){
            reservationAdapter.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}