package com.example.table.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.table.R;
import com.example.table.databinding.TestNavBottomBinding;

public class test_nav_menu extends AppCompatActivity {

    TestNavBottomBinding binding;

    Fragment notificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = TestNavBottomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SearchFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.search_bnv:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.profile_bnv:
                    replaceFragment(new ProfileFragment());
                    Log.i("REPLACED","successfully");
                    break;
                case R.id.notification_bnv:
                    notificationFragment = new NotificationFragment();
                    replaceFragment(notificationFragment);

                    Log.i("TEST", "TEST_NAV_MENU");
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
