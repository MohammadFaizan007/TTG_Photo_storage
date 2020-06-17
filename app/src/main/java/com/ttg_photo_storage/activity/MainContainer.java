package com.ttg_photo_storage.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.ttg_photo_storage.Fragment.Dashboard;
import com.ttg_photo_storage.R;
import com.ttg_photo_storage.app.PreferencesManager;
import com.ttg_photo_storage.constants.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContainer extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public TextView profile_name, my_profile, contactUs, aboutUs, logout, capability, termCandition;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.title)
    TextView title_tv;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (PreferencesManager.getInstance(context).getType().equalsIgnoreCase("staff")) {
            title_tv.setText("Enter CRN");
        }else {
            title_tv.setText("Search Data");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
        };
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setHomeAsUpIndicator(R.drawable.menu);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        toggle.syncState();
        if (!PreferencesManager.getInstance(context).getFiledesc().equalsIgnoreCase("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainContainer.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview_two, viewGroup, false);
            builder.setView(dialogView);
            TextView heading = dialogView.findViewById(R.id.heading);
            TextView body = dialogView.findViewById(R.id.body);
            TextView ok = dialogView.findViewById(R.id.buttonOk);
            TextView cancel = dialogView.findViewById(R.id.cancel);
            TextView crnNO = dialogView.findViewById(R.id.crnNO);
            crnNO.setText(PreferencesManager.getInstance(context).getCrnID());
            heading.setText(R.string.dialog_heading);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToActivity(MainContainer.this, AssetIDScanActivity.class, null);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });



        }


        View hView = navView.getHeaderView(0);
        findIDs(hView);
        ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
    }


    public void findIDs(View view) {
        try {

            profile_name = view.findViewById(R.id.profile_name);
            my_profile = view.findViewById(R.id.myProfile);
            contactUs = view.findViewById(R.id.contactUs);
            aboutUs = view.findViewById(R.id.aboutUs);
            capability = view.findViewById(R.id.capability);
            termCandition = view.findViewById(R.id.termCandition);
            logout = view.findViewById(R.id.logout);

            profile_name.setText("Welcome " + PreferencesManager.getInstance(context).getNAME()+"!");

            profile_name.setOnClickListener(this);
            my_profile.setOnClickListener(this);
            contactUs.setOnClickListener(this);
            aboutUs.setOnClickListener(this);
            capability.setOnClickListener(this);
            termCandition.setOnClickListener(this);
            logout.setOnClickListener(this);

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (v.getId()) {
            case R.id.profile_name:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.myProfile:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(MainContainer.this, ViewProfileActivity.class, null);
                break;

            case R.id.aboutUs:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(MainContainer.this, AboutUsActivity.class, null);
                break;

            case R.id.contactUs:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(MainContainer.this, CntactUsActivity.class, null);
                break;
            case R.id.capability:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(MainContainer.this, GlobalCapabilityActivity.class, null);
                break;
            case R.id.termCandition:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(MainContainer.this, TermConditionActivity.class, null);
                break;
            case R.id.logout:
                drawerLayout.closeDrawer(GravityCompat.START);
                logoutDialog(context, LoginActivity.class);
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.myProfile:
                return true;

            case R.id.aboutUs:
                return true;

            case R.id.contactUs:
                return true;

            case R.id.logout:

                return true;

        }
        return false;
    }

    public void logoutDialog(final Context context, final Class activity) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you really want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                try {
                    PreferencesManager.getInstance(context).clear();
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                    finishActivity(MainContainer.this);

                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void ReplaceFragmentWithHome(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

    public void ReplaceFragment(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(title);
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() < 1) {
            if (currentFragment instanceof Dashboard) {
                super.onBackPressed();
                finish();
//                moveTaskToBack(true);
            } else {
                ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
            }
        } else {
            fm.popBackStack();
        }
    }

}
