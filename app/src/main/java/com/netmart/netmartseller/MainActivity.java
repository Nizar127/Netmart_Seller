package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    String userId;
    private FirebaseFirestore fstore;


//    TextView textView;
//    BottomNavigationView bottomtabmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //userId = mAuth.getCurrentUser().getUid();

        createRequest();

        findViewById(R.id.google_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
//
//        bottomtabmenu = findViewById(R.id.bottomtabmenu);
//        bottomtabmenu.setSelectedItemId(R.id.orderFragment);
//
//        bottomtabmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.orderFragment:
//                        startActivity(new Intent(getApplicationContext(), OrderListActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.myProductFragment:
//                        startActivity(new Intent(getApplicationContext(), MyProductActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.reportFragment:
//                        startActivity(new Intent(getApplicationContext(), ReportActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.historyFragment:
//                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.profileFragment:
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });

       // textView = findViewById(R.id.texview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(MainActivity.this, "Welcome User", Toast.LENGTH_SHORT).show();
                    //userId = mAuth.getCurrentUser().get
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    //updateUI(user);
                    //DocumentReference documentReference = fstore.collection("sellers").document(userId);
                    //Map<String, Object> seller =new HashMap<>();
                    //seller.put("fname", fullname);
                    //Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }

                // ...
            }
        });
    }

//    //update data on user
//    private void updateUI(FirebaseUser user) {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        if(account !=  null){
//            final String personName = account.getDisplayName();
//            String personGivenName = account.getGivenName();
//            //String personGivenName = account.getGivenName();
//            String personFamilyName = account.getFamilyName();
//            final String personEmail = account.getEmail();
//            final String personId = account.getId();
//            final Uri personPhoto = account.getPhotoUrl();
//
//            Picasso.get().load(personPhoto).into();
//
//            Toast.makeText(MainActivity.this,personName + personEmail ,Toast.LENGTH_SHORT).show();
//
//            //store the google login data into firestore
//            Map<String, Object> seller = new HashMap<>();
//            seller.put("ID", personId);
//            seller.put("Username", personName);
//            seller.put("Email", personEmail);
//
//            fstore.collection("Sellers").document(userId).set(seller)
//            //DocumentReference documentReference = fstore.collection("sellers").document(String.valueOf(user));
////            Map<String, Object> seller = new HashMap<>();
////            seller.put("ID", personId);
////            seller.put("Username", personName);
////            seller.put("Email", personEmail);
////            seller.put("Profile Picture", personPhoto);
////            documentReference.set(seller).addOnSuccessListener(new OnSuccessListener<Void>() {
////                @Override
////                public void onSuccess(Void aVoid) {
////                    Toast.makeText(MainActivity.this,personId + personName + personEmail + personPhoto, Toast.LENGTH_SHORT).show();
////                }
////            });
////            startActivity(new Intent(getApplicationContext(), OrderListActivity.class));
//        }
//
//    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}
