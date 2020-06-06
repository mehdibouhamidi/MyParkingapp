package com.example.myparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example.myparking.R.id.container_fragment;

public class Fragment_home extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView but1,but2,but3,but4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        /**********************************************************************************/
        /************************last changes*********************************************/
        but1 = view.findViewById(R.id.but1);
        but2 = view.findViewById(R.id.but2);
        but3 = view.findViewById(R.id.but3);
        but4 = view.findViewById(R.id.but4);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(),MainReservation.class);
                startActivity(mainIntent);

            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(),UserLocalisationActivity.class);
                startActivity(mainIntent);

            }
        });

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(container_fragment, new Fragment_monvehicule());
                fragmentTransaction.commit();
            }
        });


        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(),Profilpage.class);
                startActivity(mainIntent);

            }
        });



        /**********************************************************************************/
        return view;
    }

   /*
   FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextView userheader;
   userheader = findViewById(R.id.userheader);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID =fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Utilisateurs").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                userheader.setText(documentSnapshot.getString("username"));
            }
        });
 */
}
