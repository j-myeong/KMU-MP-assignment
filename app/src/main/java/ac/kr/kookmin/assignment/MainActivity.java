package ac.kr.kookmin.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ac.kr.kookmin.database.Z2DBHelper;

public class MainActivity extends AppCompatActivity {

    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);

    Z2DBHelper z2DBHelper = new Z2DBHelper(MainActivity.this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signupButton = (Button) findViewById(R.id.signupButton);
        Intent signupIntent = new Intent(this, SignupActivity.class);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button notSignupButton = (Button) findViewById(R.id.notSignupButton);
        Intent mainIntent = new Intent(this, HomeActivity.class);

        EditText id = (EditText) findViewById(R.id.editTextUserId);
        EditText pw = (EditText) findViewById(R.id.editTextPw);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context ctx = getApplicationContext();
                if (isStrEmpty(id.getText().toString()) || isStrEmpty(pw.getText().toString())) {
                    Toast.makeText(ctx, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = z2DBHelper.getReadableDatabase();
                boolean validId = false;
                boolean validPw = false;
                Cursor cursor = db.rawQuery(
                        "SELECT * FROM users WHERE id = '" + id.getText().toString() + "'",
                        null
                );
                while (cursor.moveToNext()) {
                    if (cursor.getString(0).equals(id.getText().toString())) {
                        validId = true;
                        break;
                    }
                }
                if (validId) {
                    validPw = cursor.getString(1).equals(pw.getText().toString());
                    if (validPw) {
                        mainIntent.putExtra("name", cursor.getString(2));
                        mainIntent.putExtra("phone", cursor.getString(3));
                        mainIntent.putExtra("address", cursor.getString(4));
                        startActivity(mainIntent);
                        finish();
                    }
                }
                if (!validId || !validPw) Toast.makeText(ctx, "아이디 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signupIntent);
                signupIntent.putExtra("isMain", true);
            }
        });

        notSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainIntent);
                finish();
            }
        });
    }

    protected boolean isStrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed();
    }
}