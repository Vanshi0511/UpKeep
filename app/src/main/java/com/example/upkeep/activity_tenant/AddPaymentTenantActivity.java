package com.example.upkeep.activity_tenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.activity_landlord.AddPaymentActivity;
import com.example.upkeep.models.AddPaymentModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPaymentTenantActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText expiry_tenant,card_tenant,cvc_tenant,zip_tenant;
    private Button btnSavePayment;

    private String expiry1,card1,cvc1,zip1;

    static boolean countValidation=false;

    private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char DIVIDER = '-';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_tenant);


      toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextAppearance(this,R.style.redHatFont);

        card_tenant=findViewById(R.id.card_tenant);
        expiry_tenant=findViewById(R.id.expiry_tenant);
        zip_tenant=findViewById(R.id.zip_tenant);
        cvc_tenant=findViewById(R.id.cvc_tenant);
        btnSavePayment=findViewById(R.id.btnSavePayment);

        btnSavePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });


super.onBackPressed();
        card_tenant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable s) {
                cardFormatting(s);
            }
        });



        expiry_tenant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int removed, int added) {}

            @Override
            public void afterTextChanged(Editable editable) {
                expiryFormatting(editable);
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    public void cardFormatting(Editable s)
    {
        if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
            s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER)); }
    }

    private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
        boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
        for (int i = 0; i < s.length(); i++) { // check that every element is right
            if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;

    }


    public void expiryFormatting(Editable editable)
    {
        if (editable.length() > 0 && (editable.length() % 3) == 0) {
            final char c = editable.charAt(editable.length() - 1);
            if ('/' == c) {
                editable.delete(editable.length() - 1, editable.length());
            }
        }
        if (editable.length() > 0 && (editable.length() % 3) == 0) {
            char c = editable.charAt(editable.length() - 1);
            if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                editable.insert(editable.length() - 1, String.valueOf("/"));
            }
        }
        if(!isExpiryValidate(editable.toString()))
        {
            expiry_tenant.setError("invalid");
            countValidation=false;
        }
        else{
            countValidation=true;
        }
    }
    public boolean isExpiryValidate(String expiryDate)
    {
        if(expiryDate.length()!=5 || Integer.parseInt(expiryDate.substring(0,2))>12 || !String.valueOf(expiryDate.charAt(2)).equals("/"))
            return false;
        else
            return true;
    }


    private void validateData()
    {
        card1=card_tenant.getText().toString().trim();
        cvc1=cvc_tenant.getText().toString().trim();
        expiry1=expiry_tenant.getText().toString().trim();
        zip1=zip_tenant.getText().toString().trim();

        if(TextUtils.isEmpty(card1))
        {
            card_tenant.setError("Fields can't be empty");
            card_tenant.requestFocus();
        }else if(card1.length()<19)
        {
            card_tenant.setError("Invalid");
            card_tenant.requestFocus();
        }else if(TextUtils.isEmpty(card1))
        {
            cvc_tenant.setError("Fields can't be empty");
            cvc_tenant.requestFocus();
        }else if(cvc_tenant.length()<3)
        {
            cvc_tenant.setError("Invalid");
            cvc_tenant.requestFocus();
        }else if(TextUtils.isEmpty(expiry1))
        {
            expiry_tenant.setError("Fields can't be empty");
            expiry_tenant.requestFocus();
        }else if(!countValidation){

            expiry_tenant.setError("Invalid");
            expiry_tenant.requestFocus();
        }else if(TextUtils.isEmpty(zip1))
        {
            zip_tenant.setError("Fields can't be empty");
            zip_tenant.requestFocus();
        }
        else
        {
            AddPaymentModel model=new AddPaymentModel(card1,cvc1,expiry1,zip1);
            adddata(model);
        }
    }
    private void adddata(AddPaymentModel model)
    {
        String token ="";
        Call<AddPaymentModel> call = ApiController.getInstance() // need a URL
                .getApiSets().addPaymentCard(token,model);

        call.enqueue(new Callback<AddPaymentModel>() {
            @Override
            public void onResponse(Call<AddPaymentModel> call, Response<AddPaymentModel> response) {
                AddPaymentModel model = response.body();
                Toast.makeText(AddPaymentTenantActivity.this, "Payment Added", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
            @Override
            public void onFailure(Call<AddPaymentModel> call, Throwable t) {
                Toast.makeText(AddPaymentTenantActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    }
