package com.example.upkeep.activity_tenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.activity_landlord.EditProfileActivity;
import com.example.upkeep.activity_landlord.MainActivity;
import com.example.upkeep.activity_landlord.SupportActivity;
import com.example.upkeep.activity_landlord.TermsAndConditionsActivity;
import com.example.upkeep.auth.AccountLoginActivity;
import com.example.upkeep.fragments.BankingFragment;
import com.example.upkeep.fragments.ChatFragment;
import com.example.upkeep.fragments.HomeFragment;
import com.example.upkeep.fragments.MypropertyFragment;
import com.example.upkeep.fragments.RepairFragment;
import com.example.upkeep.fragments.RepairTenantFragment;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainTenantActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView toolbarTitle;
    FragmentManager fragmentManager=getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tenant);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationDrawer);
toolbarTitle = findViewById(R.id.toolbarTitle);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new HomeFragment(MainTenantActivity.this),0);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(!(bottomNavigationView.getSelectedItemId() == item.getItemId())) {
                    switch (id) {
                        case R.id.repair_bottom:
                            toolbarTitle.setText("REPAIR");
                            loadFragment(new RepairTenantFragment(MainTenantActivity.this), 1);
                            break;
                        case R.id.banking_bottom:
                            toolbarTitle.setText("BANKING");
                            loadFragment(new BankingFragment(), 1);
                            break;
                        case R.id.chat_bottom:
                            toolbarTitle.setText("CHAT");
                            loadFragment(new ChatFragment(MainTenantActivity.this), 1);
                            break;
                    }
                }
                return true;
            }

        });

        View header =  navigationView.getHeaderView(0);
        ImageView back=header.findViewById(R.id.navigationBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                Fragment fragment;
                switch (item.getItemId())
                {
                    case R.id.support:
                        intent=new Intent(MainTenantActivity.this, SupportActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.termsConditions:
                        intent=new Intent(MainTenantActivity.this, TermsAndConditionsActivity.class);
                        startActivity(intent);
                        break;


                    case R.id.signOut:
                        showSignOutDialog();
                        break;

                    case R.id.settings:
                        intent=new Intent(MainTenantActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.home:

                    case R.id.myProperties:
                        fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                        if(!(fragment instanceof MypropertyFragment))
                            loadFragment(new MypropertyFragment(MainTenantActivity.this),1);
                        onBackPressed();
                        break;

                    case R.id.chats:
                        fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                        if(!(fragment instanceof ChatFragment))
                            loadFragment(new ChatFragment(MainTenantActivity.this),1);
                        onBackPressed();
                        break;
                }
                return true;
            }
        });

    }
    public void loadFragment(Fragment fragment, int status) {

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        if(status==0)
        {   fragmentTransaction.add(R.id.frame,fragment);
            fragmentManager.popBackStack(R.id.rootFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.addToBackStack(null);
        }
        else
        { fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null); }
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

            super.onBackPressed();
        }
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame);
        if(f!=null)
        {
            if(f instanceof RepairFragment)
            {bottomNavigationView.getMenu().getItem(1).setChecked(true);
                toolbarTitle.setText("REPAIR");}
            else if(f instanceof BankingFragment)
            {   bottomNavigationView.getMenu().getItem(2).setChecked(true);
                toolbarTitle.setText("BANKING");}
            else
            { bottomNavigationView.getMenu().getItem(3).setChecked(true);
                toolbarTitle.setText("CHAT");}
        }
        else
            super.onBackPressed();
    }
    public void showSignOutDialog()
    {
        ImageView imageCross;
        Button btnNo;
        TextView textYes;
        Dialog dialog = new Dialog(MainTenantActivity.this);
        dialog.setContentView(R.layout.sign_out_dialog);

        imageCross = dialog.findViewById(R.id.imgCross);
        btnNo = dialog.findViewById(R.id.btnNo);
        textYes = dialog.findViewById(R.id.btnYes);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);

        imageCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForSignedAccount();
            }
        });
    }
    public void checkForSignedAccount() {

        SharedPref preferences= new SharedPref(MainTenantActivity.this,"unKnown");
        String account = preferences.getStatusOfAccountLogin();

        if(account.equals("Google"))
            signOutFromGoogle(preferences);
        else if(account.equals("Facebook"))
            signOutFromFacebook(preferences);
        else
            signOutFromEmail(preferences);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return true;
    }

    private void signOutFromFacebook(SharedPref pref)
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn)
        {   LoginManager.getInstance().logOut();
            pref.setStatusOfAccountLogin();
            pref.setToken("unKnown");
            Toast.makeText(MainTenantActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainTenantActivity.this, AccountLoginActivity.class));
            finish(); }
        else
            Toast.makeText(MainTenantActivity.this, "Failed to LogOut", Toast.LENGTH_SHORT).show();
    }
    private void signOutFromGoogle(SharedPref pref)
    {
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(MainTenantActivity.this);
        if(account!=null)
        {
            GoogleSignInOptions googleSignInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.server_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(MainTenantActivity.this,googleSignInOptions);

            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        pref.setStatusOfAccountLogin();
                        pref.setToken("unKnown");
                        Toast.makeText(MainTenantActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainTenantActivity.this,AccountLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(MainTenantActivity.this, "Failed to logOut", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void signOutFromEmail(SharedPref pref)
    {
        pref.setStatusOfAccountLogin();
        pref.setToken("unKnown");

        Intent intent =new Intent(MainTenantActivity.this,AccountLoginActivity.class);
        startActivity(intent);
        finish();
    }

}