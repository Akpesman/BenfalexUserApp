package bismsoft.benfalexparcel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText etEmail,etPassword;
    Button btnLogin;
    TextView tvSignUp;
    String email,password;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference mRef;
    SharedPreferences.Editor sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("LOGIN");
        views();
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp.class));
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                password=etPassword.getText().toString();
                if(email.length()<8)
                {
                    etEmail.setError("Invalid Number");
                }
                else if(password.length()<6)
                {
                    etPassword.setError("Password too short");
                }
                else{

                    loginUser();
                }
            }
        });
    }

    private void views() {
        etEmail =findViewById(R.id.et_email);
        etPassword=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btn_login);
        tvSignUp=findViewById(R.id.tv_signup);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Please Wait...");
        mRef= FirebaseDatabase.getInstance().getReference("customers");
        sharedPref=getSharedPreferences("customer",MODE_PRIVATE).edit();
    }

    public void loginUser()
    {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    sharedPref.putString("userId",user.getUid()+"");
                    sharedPref.apply();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    progressDialog.dismiss();
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "An error occured!!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Login.this, "No account found", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }
}