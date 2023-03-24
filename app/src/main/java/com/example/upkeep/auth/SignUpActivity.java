package com.example.upkeep.auth;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.models.RegisterModel;
import com.example.upkeep.models.RegisterResponseModel;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivityResultLauncher<String> launcher;
    private TextView btnAccount;
    private ImageView backBtn;
    private Button btnSignUp;
    private ProgressBar progressBar;

    private EditText username, emailAddress, password, confirmPassword, phoneNumber ;
    private Spinner typeOfUser;
    private RadioButton genderMale , genderFemale ;
    private RadioGroup genderGroup;
    private String user, pass, email, confirmPass, phone , gender, typeUser="--Select type--".toUpperCase();
    private ImageView image;
    private Uri default_user_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnAccount = findViewById(R.id.alreadyAccount);
        btnSignUp = findViewById(R.id.btnSignUp);
        username = findViewById(R.id.username);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);


        image=findViewById(R.id.imgUser);
        phoneNumber = findViewById(R.id.phoneNumber);
        genderMale = findViewById(R.id.maleRadio);
        genderFemale=findViewById(R.id.femaleRadio);
        typeOfUser = findViewById(R.id.type_of_user);
        genderGroup=findViewById(R.id.radio_group);
        progressBar = findViewById(R.id.progress_bar);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.maleRadio: gender = "male";
                                         break;
                    case R.id.femaleRadio: gender = "female";
                                         break;
                }
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        backBtn = findViewById(R.id.imgBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayList<String> arrayTypeOfUser = new ArrayList<>();
        arrayTypeOfUser.add("--Select type--");
        arrayTypeOfUser.add("Landlord");
        arrayTypeOfUser.add("Tenant");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayTypeOfUser);
        typeOfUser.setAdapter(adapter);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    launcher.launch("image/*");
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString().trim();
                email = emailAddress.getText().toString().trim();
                pass = password.getText().toString().trim();
                confirmPass = confirmPassword.getText().toString().trim();
                phone = phoneNumber.getText().toString().trim();
                typeUser = typeOfUser.getSelectedItem().toString().toUpperCase();
                if (TextUtils.isEmpty(user)) {
                    username.setError("Fields can't be empty");
                    username.requestFocus();
                }else if(user.length()<6){
                    username.setError("Username should be of minimum 6 characters");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    emailAddress.setError("Fields can't be empty");
                    emailAddress.requestFocus();
                } else if (TextUtils.isEmpty(pass)) {
                    password.setError("Fields can't be empty");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(confirmPass)) {
                    confirmPassword.setError("Fields can't be empty");
                    confirmPassword.requestFocus();
                } else if (TextUtils.isEmpty(phone)) {
                    phoneNumber.setError("Fields can't be empty");
                    phoneNumber.requestFocus();
                } else if (gender==null) {
                    Toast.makeText(SignUpActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                } else if(typeUser.equalsIgnoreCase("--Select type--")){
                    Toast.makeText(SignUpActivity.this, "Please select type", Toast.LENGTH_SHORT).show();
                }else {
                    if (isPasswordValid(pass)) {
                        if (!pass.equals(confirmPass)) {
                            confirmPassword.setError("Password not matched");
                            confirmPassword.requestFocus();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            RegisterModel model =new RegisterModel(user,email,pass,confirmPass,phone,gender,typeUser);
                            callRegistration(model);
                        }
                    } else {
                        password.requestFocus();
                        Toast.makeText(SignUpActivity.this, "Password must contains atleast 8 characters,one digit,one lowercase letter,one uppercase letter and atleast one special character.", Toast.LENGTH_LONG).show();
                    }

                }
            }

            private boolean isPasswordValid(String pass) {
                Pattern pattern;
                Matcher matcher;

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}$";

                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(pass);

                return matcher.matches();

            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            default_user_img = result;
                            image.setImageURI(result);
                        }
                    }
                });
    }


    public void callRegistration(RegisterModel model) {

        RequestBody user = RequestBody.create(MediaType.parse("multipart/form_data"),model.getUsername());
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form_data"),model.getEmail());
        RequestBody pass = RequestBody.create(MediaType.parse("multipart/form_data"),model.getPassword());
        RequestBody confirmPass = RequestBody.create(MediaType.parse("multipart/form_data"),model.getConfirmPassword());
        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form_data"),model.getPhone_number());
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form_data"),model.getGender());
        RequestBody typeOfUser = RequestBody.create(MediaType.parse("multipart/form_data"),model.getType_of_user());

        MultipartBody.Part image = null;

            File file=null;
            try {
                file = new File(getPath());
            }
            catch (NullPointerException e){}
            if (file != null) {
                RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form_data"), file);
                image = MultipartBody.Part.createFormData("image", file.getName(), imageFile);
            }
                Call<RegisterResponseModel> call = ApiController.getInstance().getApiSets().registerUser(user,email,pass,confirmPass,phone,typeOfUser,gender,image);
                call.enqueue(new Callback<RegisterResponseModel>() {
                    @Override
                    public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                        if(response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            RegisterResponseModel model1 = response.body();
                            Toast.makeText(SignUpActivity.this,model1.getMessage() , Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                        Log.d("error",t.getMessage());
                        progressBar.setVisibility(View.GONE);
                        if(t instanceof UnknownHostException)
                            Toast.makeText(SignUpActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                    }
                });

        }
    public String getPath() {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(default_user_img, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}