package bismsoft.benfalexparcel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText etName,etEmail,etPassword;
    Button btnSignup;
    TextView tvLogin;
    String name,email,password;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("SignUp");
        views();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Login.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=etName.getText().toString();
                email = etEmail.getText().toString();
                password=etPassword.getText().toString();
                if(name.length()<5)
                {
                    etName.setError("Name too short");
                }
                else if(email.length()<8)
                {
                    etEmail.setError("Invalid Email");
                }
                else if(password.length()<6)
                {
                    etPassword.setError("Password too short");
                }
                else{
                    customerSignUp();
                }

            }
        });
    }

    private void views() {
        etName=findViewById(R.id.et_name);
        etEmail =findViewById(R.id.et_email);
        etPassword=findViewById(R.id.et_password);
        btnSignup=findViewById(R.id.btn_signup);
        tvLogin=findViewById(R.id.tv_login);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Please Wait...");
    }

    public void customerSignUp()
    {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("customers");
                    mRef.child(mAuth.getCurrentUser().getUid()).setValue(new Customer(mAuth.getCurrentUser().getUid()+"",name,email,password,"token")).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUp.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this,Login.class));
                                progressDialog.dismiss();
                                finish();
                            }
                            else{
                                Toast.makeText(SignUp.this, "Filed to Signup try another email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAGGG",e.getMessage());
                Toast.makeText(SignUp.this, "Signup failed try again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}