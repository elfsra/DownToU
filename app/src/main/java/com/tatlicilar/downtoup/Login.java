package com.tatlicilar.downtoup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "1" ;
    private EditText email;
    private EditText password;
    private Button kaydolBtn; // hesabı olmayanlar kaydol butonu ile kayitol.java ya geçer
    private Button girisBtn; // email ve şifre yazdıktan sonra girişBtn ile anasayfaya geçilir
    private SignInButton google2; // google ile oturum aç butonu
    private GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth; // authentication için
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebaseDatabase; // access database
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;
    private String userId;
    public static String mail_adres;
    Intent intent, intent20,intent21;
    private Object g;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        kaydolBtn = (Button)findViewById(R.id.kayitBtn);
        google2 = (SignInButton)findViewById(R.id.google2);
        girisBtn = (Button)findViewById(R.id.girisBtn);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users");
        //FirebaseAuth sınıfının referans olduğu nesneleri kullanabilmek için getIns
        mFirebaseAuth = FirebaseAuth.getInstance();
        kaydolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Login.this, KayitOl.class);
                startActivity(intent);
            }
        });
        //Google Sign in Options Yapılandırması
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Login.this, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        google2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        girisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2 = email.getText().toString();
                String password2 = password.getText().toString();
                email2 = email2.trim();
                password2 = password2.trim();
                //E-mail girilmemiş ise kullanıcıyı uyarıyoruz
                if (TextUtils.isEmpty(email2)) {
                    Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Parola girilmemiş ise kullanıcıyı uyarıyoruz.
                if (TextUtils.isEmpty(password2)) {
                    Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                mFirebaseAuth.signInWithEmailAndPassword(email2, password2)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(Login.this, HomePage.class));
                                } else {
                                    Log.e("Giriş Hatası", task.getException().getMessage());
                                }
                            }
                        });

                // responds to changes in the user's sign-in state
                mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());

                            // Authenticated successfully with authData
                            Intent intent = new Intent(Login.this, HomePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            // User is signed out
                            Log.d("TAG", "onAuthStateChanged:signed_out");
                        }
                    }
                };
            }

        });
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!= null){
                    //user is signed in
                    Toast.makeText(Login.this,"Senin adın" + user.getEmail().toString(), Toast.LENGTH_SHORT).show();
                    mail_adres = user.getEmail().toString();
                    //onSignedInInitialize(user.getDisplayName());
                }
                else{
                    //user is signed out
                }
            }
        };
    }
    //Oturum acma islemleri
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In basarili oldugunda Firebase ile yetkilendir
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In hatası.
                Log.e(TAG, "Google Sign In hatası.");
            }
        }
    }
    //GoogleSignInAccount nesnesinden ID token'ı alıp, bu Firebase güvenlik referansını kullanarak
    // Firebase ile yetkilendirme işlemini gerçekleştiriyoruz
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //             Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            //               Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Yetkilendirme hatası.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            final String userEmailRef2; // kişisinin mailindeki noktayı kaldırıp virgüle çevirdim çünkü key bilgisinde nokta kabul etmiyor
                            final String userEmailRef = user.getEmail().toString(); // kişinin db deki esas maili (noktalı)
//                          userEmailRef = emailToKey(mDatabaseReference.child(String.valueOf(email)).toString());
                            userEmailRef2 = emailToKey(userEmailRef.toString());
                            //               Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Mailin" + userEmailRef ,
                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(Login.this, "useremailref" + userEmailRef + userEmailRef2,
                                    Toast.LENGTH_SHORT).show();
                            userId = mDatabaseReference.push().getKey();
                            DatabaseReference rootRef = mFirebaseDatabase.getReference();
                            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
//                                    Toast.makeText(Login.this, "snapshot child bişiler" +snapshot.child("email")  ,
//                                            Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(Login.this, "snapshot" +snapshot.getValue().toString() ,
//                                            Toast.LENGTH_SHORT).show();
//                                                 Log.d(TAG, "snapshott" + snapshot.getValue().toString());

                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        Log.d(TAG, "======="+postSnapshot.child("email").getValue());
                                        if (postSnapshot.child("email").getValue().equals(userEmailRef) ){
                                            startActivity(new Intent(Login.this, HomePage.class));
                                            finish();
                                            break;
                                        }   else{
                                            startActivity(new Intent(Login.this, GoogleKayitOl.class));
                                            finish();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                });}

    private void signOut() {
        //Firebase sign out
        mFirebaseAuth.signOut();

        //  Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });

    }
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
//            status.setText(getString(R.string.google_status_fmt, user.getEmail()));
//            detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));

        } else {
            Toast.makeText(this, "Çıkış yaptınız!",
                    Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action1) {
            Toast.makeText(this, "action1",
                    Toast.LENGTH_SHORT).show();
            signOut();
            return true;
        }else     if (id == R.id.action2) {
            Toast.makeText(this, "action2",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showPressDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }
    public void hideProgressDialog(){
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.hide();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        hideProgressDialog();
    }
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    public String emailToKey(String emailAddress) {
        return emailAddress.replace('.', ',');
    }
}
