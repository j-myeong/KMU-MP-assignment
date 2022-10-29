package ac.kr.kookmin.assignment;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    String name;
    String phone;
    String address;

    BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.userInfo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("회원 정보");

            if (name != null) {
                builder.setMessage(String.format("이름: %s\n전화번호: %s\n주소: %s", name, phone, address));
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            } else {
                builder.setMessage("회원가입 하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"회원가입으로 이동합니다.",Toast.LENGTH_SHORT).show();
                                Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                                signupIntent.putExtra("isMain", false);
                                startActivity(signupIntent);
                                finish();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
            }
            builder.show();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent homeIntent = getIntent();
        if (homeIntent.hasExtra("name")) {
            name = homeIntent.getStringExtra("name");
            phone = homeIntent.getStringExtra("phone");
            address = homeIntent.getStringExtra("address");
        }
    }

    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed();
    }
}