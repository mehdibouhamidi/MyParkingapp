package com.example.myparking;

import android.content.Intent;
import androidx.annotation.NonNull;

/*import com.example.myparking.model.Adapter;*/
import com.example.myparking.userlogin.SaveSharedPreference;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.myparking.R.id.container_fragment;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    TextView but4;
    RecyclerView myrecycleview;
   /* Adapter adapter; */

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
        setContentView(R.layout.activity_home);



//pour entrer a l'aoolication sans s'enregister une autre fois
      /*  if(SaveSharedPreference.getUserName(Home.this).length()==0){
            Intent intent= new Intent(Home.this,LoginActivity.class);
            startActivity(intent);
        }else{
            finish();
            startActivity(getIntent());

        }
        */
        toolbar = findViewById(R.id.reservationDetailsTitle);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
/*
        //il faut changer le type list string to sting pour l'uttiliser en username,matricule,email ,et list sting pour les reservations
        List<String> mDatareceived = new ArrayList<>();
        mDatareceived.add("first user for app");
        adapter = new com.example.myparking.model.Adapter(mDatareceived);
        myrecycleview.setLayoutManager(new LinearLayoutManager(this));
        myrecycleview.setAdapter(adapter);
*/





        //fragment home or principal loading by default
        fragmentManager =getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container_fragment, new Fragment_home());
        fragmentTransaction.commit();
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        SaveSharedPreference shared = new SaveSharedPreference(getApplicationContext());
        shared.firsttime();

    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.home_but){
            fragmentManager =getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container_fragment, new Fragment_home());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.reservation){
            Intent mainIntent = new Intent(Home.this,MainReservation.class);
            startActivity(mainIntent);

        }


        if(menuItem.getItemId() == R.id.localisation){
            Intent mainIntent = new Intent(Home.this,UserLocalisationActivity.class);
            startActivity(mainIntent);


        }
        if(menuItem.getItemId() == R.id.monvehicule){
            fragmentManager =getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container_fragment, new Fragment_monvehicule());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() == R.id.profil){
            Intent mainIntent = new Intent(Home.this,Profilpage.class);
            startActivity(mainIntent);
        }


        if(menuItem.getItemId() == R.id.contact){
            fragmentManager =getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container_fragment, new Fragment_contact());
            fragmentTransaction.commit();


        }


        if(menuItem.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
            SaveSharedPreference shared =new SaveSharedPreference(getApplicationContext());
            shared.finishtask();
            finish();
            Toast.makeText(Home.this, "logout succesfull", Toast.LENGTH_SHORT).show();

    }
        return true;
    }

}