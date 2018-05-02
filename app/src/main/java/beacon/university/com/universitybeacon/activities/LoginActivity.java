package beacon.university.com.universitybeacon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import beacon.university.com.universitybeacon.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void Login(View v) {
//        Intent intent = new Intent(this,InformationPage.class);
        Intent intent = new Intent(this, AnnouncementActivity.class);
//        Intent intent = new Intent(this,AttendancePage.class);
//        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        startActivity(intent);

    }

    public void Attendance(View v) {
        Intent intent = new Intent(this, AttendancePage.class);
        startActivity(intent);

    }

    public void InformationPage(View v) {
        Intent intent = new Intent(this, InformationPage.class);
        startActivity(intent);

    }

    public void NoticePage(View v) {
        Intent intent = new Intent(this, AnnouncementActivity.class);
        startActivity(intent);

    }
}
