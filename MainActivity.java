package com.example.a90535.letgo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button kayitSifreButon;
    private EditText kayitEditText;
    private EditText kayitSifreEditText;
    private TextView textViewKayitOl;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        kayitSifreButon = (Button) findViewById(R.id.kayitSifreButon);

        kayitEditText= (EditText) findViewById(R.id.kayitEditText);
        kayitSifreEditText= (EditText) findViewById(R.id.kayitSifreEditText);
        textViewKayitOl= (TextView) findViewById(R.id.textViewKayitOl);

        kayitSifreButon.setOnClickListener(this);
        textViewKayitOl.setOnClickListener(this);
    }
    private void registerUser(){
        String email = kayitEditText.getText().toString().trim();
        String password = kayitSifreEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            //mail bos
            Toast.makeText(this,"Lütfen geçerli bir mail adresi girin",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            //sifre bos
            Toast.makeText(this,"Lütfen bir şifre girin",Toast.LENGTH_SHORT).show();
            return;
        }

        //doğrulama olunca
        progressDialog.setMessage("Kayıt Olunuyor ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            //kayit basarlı

                            Toast.makeText(MainActivity.this,"Başarıyla Kayıt Olundu",Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this,"Kayıt Olma Başarısız Tekrar Deneyin",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view==kayitSifreButon)
        {
            registerUser();
        }
        if (view==textViewKayitOl)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
