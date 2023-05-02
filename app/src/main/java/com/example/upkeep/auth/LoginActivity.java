package com.example.upkeep.auth;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.activity_landlord.DashboardActivity;
import com.example.upkeep.activity_landlord.MainActivity;
import com.example.upkeep.models.LoginModel;
import com.example.upkeep.models.LoginResponseModel;
import com.example.upkeep.models.SendResetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class LoginActivity extends AppCompatActivity {

    private TextView signUp, forgotPassword;
    private Dialog dialog;
    private ImageView imgCross;
    private EditText emailAddress, password, forgotEmail;
    private Button btnLogin, buttonOkSendReset;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    ProgressBar progressBar;
    String username, pass, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.signUp);
        forgotPassword = findViewById(R.id.forgotPassword);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progress_bar);

        auth = FirebaseAuth.getInstance();
//firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgot_pass_dialog);

        imgCross = dialog.findViewById(R.id.imgCross);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCancelable(false);
            }
        });
        buttonOkSendReset = dialog.findViewById(R.id.buttonOkSendReset);
        forgotEmail = dialog.findViewById(R.id.email);
        buttonOkSendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgotEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    forgotEmail.setError("Please enter email");
                    forgotEmail.requestFocus();
                } else {
                    SendResetModel model = new SendResetModel(email);
                    callSendReset(model);
                }
            }
        });
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = emailAddress.getText().toString().trim();
                pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    emailAddress.setError("Fields can't be empty");
                    emailAddress.requestFocus();
                } else if (TextUtils.isEmpty(pass)) {
                    password.setError("Fields can't be empty");
                    password.requestFocus();
                } else {

//                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                    progressBar.setVisibility(View.VISIBLE);
                     LoginModel model=new LoginModel(username,pass);
                     loginCredentials(model);

                }
            }
        });
    }

    public void callSendReset(SendResetModel model) {

        // MultipartBody.Part image = null;

        Call<SendResetModel> call = ApiController.getInstance().getApiSets()
                .sendReset(model);

//        if (default_user_img == null) {
//            Toast.makeText(this, "Insert image also", Toast.LENGTH_SHORT).show();
//        } else {
        //  File file = new File(getPath());
        //  if (file != null) {
        //     RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form_data"), file);
        //      image = MultipartBody.Part.createFormData("image", file.getName(), imageFile);
        // }
        //  }

        call.enqueue(new Callback<SendResetModel>() {
            @Override
            public void onResponse(Call<SendResetModel> call, Response<SendResetModel> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    SendResetModel resetResponseModel = response.body();
                    if (resetResponseModel.getEmail().equals("Email sent")) {
                        Toast.makeText(LoginActivity.this, "email sent successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(LoginActivity.this, resetResponseModel.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SendResetModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginCredentials(LoginModel model) {
        Call<LoginResponseModel> call = ApiController.getInstance()
                .getApiSets().loginUser(model);

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                LoginResponseModel responseModel = response.body();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    SharedPref pref = new SharedPref(LoginActivity.this, "Email");

                    pref.setStatusOfAccountLogin();
                    pref.setValueForAppIsOpened();
                    pref.setToken(responseModel.getToken().getAccess());


                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    progressBar.setVisibility(View.GONE);
                    String err = response.message();
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (t instanceof UnknownHostException)
                    Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}