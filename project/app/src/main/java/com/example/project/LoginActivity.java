package com.example.project;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    int RC_SIGN_IN = 1000;

    GoogleSignInClient mGoogleSignInClient;
    SignInButton mSignInButton;
    Button mSignOutButton;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_linear);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignOutButton = findViewById(R.id.sign_out_button);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    // 구글 로그인을 시작하는 메소드입니다.
    public void signIn(){
        // 구글 로그인 클라이언트로부터 로그인 Intent를 얻습니다.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        // 로그인 화면을 시작하며, 결과는 onActivityResult에 전달됩니다.
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // 구글 로그아웃을 수행하는 메소드입니다.
    public void signOut(){
        // 구글 로그인 클라이언트를 이용하여 로그아웃을 시작합니다.
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // 로그아웃 결과를 처리합니다.
                        if (task.isSuccessful()) {
                            // 로그아웃이 성공하면 사용자에게 성공 메시지를 보여줍니다.
                            Toast.makeText(LoginActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            // 로그아웃이 실패하면 사용자에게 실패 메시지를 보여줍니다.
                            Toast.makeText(LoginActivity.this, "로그아웃 실패", Toast.LENGTH_SHORT).show();
                        }

                        // UI를 업데이트합니다. 이 메소드의 구현이 없으므로 실제로는 아무 일도 일어나지 않습니다.
                        updateUI(null);
                    }

                    private void updateUI(Object o) {
                        // 이 메소드는 비어있으므로 실제로는 아무 일도 하지 않습니다.
                    }
                });
    }

    // startActivityForResult로부터 결과를 받아 처리하는 메소드입니다.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);

        // requestCode가 RC_SIGN_IN인 경우, 즉 로그인 결과를 받은 경우를 처리합니다.
        if (requestCode == RC_SIGN_IN) {
            // Intent에서 로그인 결과를 얻습니다.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(Data);
            try {
                // 로그인이 성공했음을 확인하고, 로그인한 사용자의 계정 정보를 얻습니다.
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // 로그인이 성공했으므로 MainActivity로 이동하는 Intent를 생성하고 시작합니다.
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            } catch (ApiException e) {
                // 로그인이 실패했음을 확인하고, 실패 이유를 출력하고 사용자에게 로그인 실패 메시지를 보여줍니다.
                e.printStackTrace();
                String msg = "로그인에 실패하였습니다";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        }

    }
}
