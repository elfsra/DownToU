package com.tatlicilar.downtoup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KayitOl extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    private static final String TAG = "anonymous";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button kaydolBtn;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    public EditText ad;
    public EditText soyad;
    public EditText password;
    public EditText email;
    private Button kaydol;
    private Button date; // doğum tarihini değiştirmek için buton
    private TextView tarih;//üye ismi ve doğum tarihi bilgisi
    private String userId;
    private String uyelikTuru;
    private int year; // datepickerdaki yıl
    private int month; //datepickerdaki ay
    private int day; //datepickerdaki gün
    private String picker_tarih;
    private ExpandableListAdapter mExpandableListAdapter;
    private ExpandableListView mExpandableListView;
    static final int DATE_DIALOG_ID = 999;
    // Group/parent data
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deneme);
        mExpandableListView = (ExpandableListView) findViewById(R.id.explv1);
        // Preparing list data
        prepareListData();
        mExpandableListAdapter = new ExpandableListAdapters(this, listDataHeader, listDataChild);
        // Setting list adapter
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnChildClickListener(this);

        ad = (EditText) findViewById(R.id.ad);
        soyad = (EditText)findViewById(R.id.soyad);
        password = (EditText)findViewById(R.id.sifre);
        email = (EditText)findViewById(R.id.email);
        kaydol = (Button)findViewById(R.id.kaydol);
        tarih= (TextView) findViewById(R.id.dtarih); //doğum tarihi
        date = (Button)findViewById(R.id.tarihSec);//datepicker ı başlatmak için basılan buton

        tarih.setText("01.01.1990"); //homepage açıldığında önceki classtan gelen bilgileri bu ekranda atıyorum
        //doğum tarhi değiştirme butonuna basıldıysa handle ediyorum
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                String parola = password.getText().toString();
                String mail = email.getText().toString();
                Toast.makeText(KayitOl.this, "Kaydolan bastın",
                        Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(mail, parola)
                        .addOnCompleteListener(KayitOl.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    createUser();
                                    Toast.makeText(KayitOl.this, "Kullanıcınız açıldı",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(KayitOl.this, "kaydolun elsi",
                                            Toast.LENGTH_SHORT).show();
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        Toast.makeText(KayitOl.this, "Invalid Emaild Id",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(KayitOl.this, "Invalid Password",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseNetworkException e) {
                                        Toast.makeText(KayitOl.this, "No network",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
//                                        Log.e(LOG_TAG, e.getMessage());
                                        Toast.makeText(KayitOl.this, e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
//                                    Log.w(LOG_TAG, "signInWithEmail:failed", task.getException());
//                                    Toast.makeText(LoginActivity.this, R.string.login_error,
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                    Log.e("Yeni Kullanıcı Hatası", task.getException().getMessage());
                                }
                            }
                        });

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void createUser() {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        final String add = ad.getText().toString();
        final String soyadd = soyad.getText().toString();
        final String parola = password.getText().toString();
        final String mail = email.getText().toString();
        final String dtarihi = tarih.getText().toString();

        userId = mDatabaseReference.push().getKey();
                    Melek melek = new Melek(add, soyadd, parola, mail,uyelikTuru,dtarihi,0,0);
                    mDatabaseReference.child(userId).setValue(melek);
                    Toast.makeText(KayitOl.this, "Adım ." + add,
                            Toast.LENGTH_SHORT).show();
        addUserChangeListener();
    }

    // Preparing the list data
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding group/parent data
        listDataHeader.add("Üyelik Türü");

        // Adding child1 data
        List<String> uyelikTuru = new ArrayList<String>();
        uyelikTuru.add("Melek");
        uyelikTuru.add("Aile");
        uyelikTuru.add("Destekçi");
        uyelikTuru.add("Danışman");
        uyelikTuru.add("Admin");

        // Header, Child data
        listDataChild.put(listDataHeader.get(0), uyelikTuru);
    }
    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mDatabaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Melek melek = dataSnapshot.getValue(Melek.class);
                // Check for null
                if (melek == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                Log.e(TAG, "User data is changed!" + melek.getAd() + ", " + melek.getEmail());

//                // clear edit text
//                mail.setText("");
//                add.setText("");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        TextView mTextView = (TextView) view.findViewById(R.id.tvListItem);

        Toast.makeText(getBaseContext(), mTextView.getText().toString(), Toast.LENGTH_SHORT).show();
        uyelikTuru = mTextView.getText().toString();
        return false;
    }
    //datepicker objesi yaratıyorum
    protected Dialog onCreateDialog(int id){
        if(id == DATE_DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener, year,month,day);
        return null;
    }
    //datepickera basıldıysa handle ediyorum
    //ay bilgisini aldığımda mevcut aydan hep 1 ay az gösterdi o yüzden +1 diyerek kullandım
    private DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            if ((month+1) < 10){ //eğer ay tek haneli ise başına 0 ekledim
                picker_tarih = day + ".0" + (month+1) + "." + year;
            }
            else{ //ay çift haneli ise olduğu gibi aldım
                picker_tarih = day + "." + (month+1) + "." + year;
            }
            tarih.setText(picker_tarih) ;
        }
    };
}
