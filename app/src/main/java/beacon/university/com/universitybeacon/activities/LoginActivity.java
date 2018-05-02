package beacon.university.com.universitybeacon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import beacon.university.com.universitybeacon.R;

public class    LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);

    }

    public void Login(View v){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("USER_EMAIL", editTextEmail.getText().toString());
        Log.e("AA", "Sending email" + editTextEmail.getText().toString());
        startActivity(intent);
    }

    public void fillProfCredential(View v) {
        editTextEmail.setText("fengqian@indiana.edu");
    }
}
