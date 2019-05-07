package com.example.a90535.letgo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button buttonGirisYap;
    private EditText editTextMail;
    private EditText editTextSifre;
    private TextView textViewGiris;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }



        editTextMail = (EditText) findViewById(R.id.girisMailEditText);
        editTextSifre = (EditText) findViewById(R.id.girisSifreEditText);
        buttonGirisYap= (Button) findViewById(R.id.girisButon);
        textViewGiris= (TextView) findViewById(R.id.textView);
        progressDialog=new ProgressDialog(this);

        buttonGirisYap.setOnClickListener(this);
        textViewGiris.setOnClickListener(this);
    }
    private void userLogin(){
        String email=editTextMail.getText().toString().trim();
        String sifre=editTextSifre.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
            //mail bos
            Toast.makeText(this,"Lütfen geçerli bir mail adresi girin",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(sifre))
        {
            //sifre bos
            Toast.makeText(this,"Lütfen bir şifre girin",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Giris Yapılıyor ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v==buttonGirisYap)
        {
            userLogin();
        }
        if(v==textViewGiris)
        {

            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
