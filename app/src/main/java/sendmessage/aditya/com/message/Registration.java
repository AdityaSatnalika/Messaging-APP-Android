package sendmessage.aditya.com.message;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText number;
    private EditText password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText)findViewById(R.id.etName);
        email = (EditText)findViewById(R.id.etEmail);
        password=(EditText)findViewById(R.id.etPassword);
        number=(EditText)findViewById(R.id.etContact) ;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Register_done(View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(Registration.this,"Please Wait ...","Processing ...",true);
        (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            String userId1 = email.getText().toString().replace(".",",");

                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

                            String userId = userId1;

                            User user = new User(name.getText().toString(),email.getText().toString(),password.getText().toString(),number.getText().toString());
                            mDatabase.child(userId).setValue(user);

                           Toast.makeText(Registration.this,"registered Successfully "+mDatabase,Toast.LENGTH_LONG).show();
                           Intent i = new Intent(Registration.this,MainActivity.class);
                           startActivity(i);
                        }
                        else
                        {
                            Log.e("Error",task.getException().toString());
                            Toast.makeText(Registration.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}
