package com.tesla.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tesla.mynotes.fragment.BaseViewNoteFragment;
import com.tesla.mynotes.fragment.CreateNotesFragment;
import com.tesla.mynotes.fragment.ListNotesFragment;

public class BaseActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        hiddenActionBar();
        initializeID();
        swithcToFragment(getSupportFragmentManager());

    }
    void hiddenActionBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(this.getSupportActionBar() != null){
            this.getSupportActionBar().hide();
        }
    }

    void initializeID(){
        bottomNavigationView = this.findViewById(R.id.id_nav_menu);
        bottomNavigationView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        frameLayout = this.findViewById(R.id.id_frame_layout);
    }
    void swithcToFragment(FragmentManager fmg){
        FragmentTransaction fmt = fmg.beginTransaction();;
        fmt.replace(frameLayout.getId(), new CreateNotesFragment());
        fmt.commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.id_nav_create_note:
                        FragmentTransaction fragmentTransaction = fmg.beginTransaction();
                        fragmentTransaction.replace(frameLayout.getId(),new CreateNotesFragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.id_nav_list_note:
                        FragmentTransaction fragmentTransaction1 = fmg.beginTransaction();
                        fragmentTransaction1.replace(frameLayout.getId(), new BaseViewNoteFragment());
                        fragmentTransaction1.commit();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
}