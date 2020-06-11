package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.netmart.netmartseller.Model.Profile;
import com.netmart.netmartseller.Profile.BankAccActivity;
import com.netmart.netmartseller.Profile.BizHourActivity;
import com.netmart.netmartseller.Profile.CallSupportActivity;
import com.netmart.netmartseller.Profile.ContactCompActivity;
import com.netmart.netmartseller.Profile.EditProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

     GoogleSignInClient mGoogleSignInClient;
    BottomNavigationView bottomtabmenu;
    TextView SellerName;
    ImageView SellerNameBtn, callsupportbtn, contactusbtn, bizhourbtn, bankacctbn, logoutbtn;
    //SwitchCompat autoprintbtn, shostatusbtn;
    Switch autoprintbtn, shostatusbtn;
    String UserId;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    CircleImageView changesellerpic;
    ProgressDialog progressDialog;
    CollectionReference profileRef;
    //CollectionReference sellerInfo;
    //DocumentReference userProf;
    ArrayList<Profile> mProfile = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomtabmenu = findViewById(R.id.bottomtabmenu);
        bottomtabmenu.setSelectedItemId(R.id.profileFragment);

         changesellerpic = findViewById(R.id.changeSellerPic);
        SellerName = findViewById(R.id.seller_name);
        SellerNameBtn = findViewById(R.id.sellerNameBtn);
        callsupportbtn = findViewById(R.id.callSupportBtn);
        contactusbtn = findViewById(R.id.contactAgrocoreBtn);
        bizhourbtn = findViewById(R.id.businesshourBtn);
        bankacctbn = findViewById(R.id.bankacctBtn);
        logoutbtn = findViewById(R.id.logoutBtn);

        autoprintbtn = findViewById(R.id.autoprintbtn);
        shostatusbtn = findViewById(R.id.shopstatusbtn);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //sellerInfo = fstore.collection("Seller");
        //userProf = fstore.document()

        //obtain user data and put in the image
       UserId = fAuth.getCurrentUser().getUid();
       // UserId = fAuth.getCurrentUser().getUid().g;

        profileRef = FirebaseFirestore.getInstance().collection("Seller");

        profileRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String imgProfile = documentSnapshot.getString("image");
                    if(imgProfile != null){
                        Picasso.get().load(imgProfile).into(changesellerpic);
                    }
                    SellerName.setText(documentSnapshot.getString("Username"));
                }

                }
        });


//        DocumentReference userProf = fstore.collection("Sellers").document(UserId);
//        userProf.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                SellerName.setText(documentSnapshot.getString("Username"));
//                //changesellerpic.setImageResource(documentSnapshot.getPhotoUrl().toString);
//               // changesellerpic.setImageResource(documentSnapshot.);
//            }
//        });

        UserProfile();



        SellerNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We want to display what we actually click, so:
                //getRef(position) = to use position as reference to pass to another activity
                //getKey() = to get key from database, so that the next activity
                // will display data accurately instead of mixing with other user's order

                String uID = fAuth.getUid();

                Intent intent = new Intent(ProfileActivity.this, ProfileDetailActivity.class);
                intent.putExtra("Profile Primary Key", uID);
                startActivity(intent);
            }
        });

        callsupportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CallSupportActivity.class);
                startActivity(intent);
            }
        });

        contactusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ContactCompActivity.class);
                startActivity(intent);
            }
        });

        bankacctbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BankAccActivity.class);
                startActivity(intent);
            }
        });

        bizhourbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BizHourActivity.class);
                startActivity(intent);
            }
        });






        bottomtabmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.orderFragment:
                        startActivity(new Intent(getApplicationContext(), OrderListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myProductFragment:
                        startActivity(new Intent(getApplicationContext(), MyProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reportFragment:
                        startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.historyFragment:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profileFragment:
                        return true;
                }
                return false;
            }
        });



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Query sellerInfoQuery = sellerInfo.whereEqualTo("ID", UserId);
//        sellerInfoQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document: task.getResult()){
//                        Profile profile = document.toObject(Profile.class);
//                        mProfile.add(profile);
//                    }
//                }else{
//                    Toast.makeText(ProfileActivity.this, "Query Failed. Check logs", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }

    private void UserProfile() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personId = account.getId();
            String personEmail = account.getEmail();
            String personFamily = account.getFamilyName();
            Uri personPhoto = account.getPhotoUrl();

            //put it into the changesellerpic
            Picasso.get().load(personPhoto).into(changesellerpic);

            Toast.makeText(ProfileActivity.this,personName + personEmail ,Toast.LENGTH_SHORT).show();

            //store user google data in firestore
            Map<String, String> seller = new HashMap<>();
            seller.put("ID", personId);
            seller.put("Username", personName);
            seller.put("Email", personEmail);
            seller.put("Family_Name", personFamily);
            seller.put("User_Image", personPhoto.toString());

            fstore.collection("Seller").document(UserId).set(seller).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(ProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                    }
                   // progressDialog.dismiss();
                }
            });





        }
    }
}
