package ac.kr.kookmin.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

import ac.kr.kookmin.database.Z2DBHelper;

public class SignupActivity extends AppCompatActivity {

    Z2DBHelper z2DBHelper = new Z2DBHelper(SignupActivity.this);
    SQLiteDatabase db;

    EditText signId; // 가입 할 ID 입력
    EditText signPassword; // 가입 할 PW 입력
    EditText signName; // 가입 할 Name 입력
    EditText signPhone; // 가입 할 Phone 입력
    EditText signAddress; // 가입 할 Address 입력

    boolean isAccepted = false;
    boolean isCheckedId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Context ctx = getApplicationContext();



        Button signupButton = (Button) findViewById(R.id.signButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button checkIdButton = (Button) findViewById(R.id.checkIdButton);

        signId = (EditText) findViewById(R.id.editTextSignId);
        signPassword = (EditText) findViewById(R.id.editTextSignPw);
        signName = (EditText) findViewById(R.id.editTextSignName);
        signPhone = (EditText) findViewById(R.id.editTextSignPhone);
        signAddress = (EditText) findViewById(R.id.editTextSignAddress);
        RadioGroup signAgree = (RadioGroup) findViewById(R.id.radioSignAgree);

        signAgree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioAccept) isAccepted = true;
                else isAccepted = false;
            }
        });

        checkIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputId = signId.getText().toString().replaceAll(" ", "");
                boolean invalidDB = false;
                if (isStrEmpty(inputId)) { // 입력한 아이디 값이 비어있다면,
                    Toast.makeText(ctx, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputId.length() <= 6) {
                    Toast.makeText(ctx, "아이디는 최소 6자 이상입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = z2DBHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery(
                        "SELECT 1 FROM users WHERE id = '" + inputId + "'",
                        null
                );
                while(cursor.moveToNext()) {
                    if(cursor.getInt(0) == 1) {
                        invalidDB = true;
                        break;
                    }
                }
                if (!invalidDB) {
                    isCheckedId = true;
                    Toast.makeText(ctx, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ctx, "이미 이용중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = z2DBHelper.getWritableDatabase();
                try {
                    checkInput();
                } catch (Exception e) {
                    String msg = e.getMessage();
                    String toastMsg = "알 수 없는 오류가 발생하였습니다.";
                    if (msg.equals("EmptyInput"))
                        toastMsg = "모든 곳이 필수로 입력되어야 합니다.";
                    else if (msg.equals("NotCheckId"))
                        toastMsg = "아이디 확인을 해주세요.";
                    else if (msg.equals("InvalidPw"))
                        toastMsg = "비밀번호는 8자 이상의 문자와 숫자로만 구성되어야 합니다.";
                    else if (msg.equals("NotAccept"))
                        toastMsg = "회원약관 동의가 필요합니다.";
                    Toast.makeText(ctx, toastMsg, Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("id", signId.getText().toString());
                values.put("pw", signPassword.getText().toString());
                values.put("name", signName.getText().toString());
                values.put("phone", signPhone.getText().toString());
                values.put("address", signAddress.getText().toString());
                db.insert("users", null, values);
                db.close();
                Toast.makeText(ctx, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getIntent().getBooleanExtra("isMain", true)) {
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }
                finish();
            }
        });
    }

    protected void checkInput() throws Exception {
        if (
                isStrEmpty(signId.getText().toString())
                || isStrEmpty(signName.getText().toString())
                || isStrEmpty(signPhone.getText().toString())
                || isStrEmpty(signAddress.getText().toString())
        ) {
            throw new Exception("EmptyInput");
        }
        else if (!isCheckedId) throw new Exception("NotCheckId");
        else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", signPassword.getText().toString()))
            throw new Exception("InvalidPw");
        else if (!isAccepted) throw new Exception("NotAccept");
    }

    protected boolean isStrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public void onBackPressed() {
        if (!getIntent().getBooleanExtra("isMain", true)) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);
        }
        super.onBackPressed();
    }
}