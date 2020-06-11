package com.netmart.netmartseller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.netmart.netmartseller.Profile.EditProfileActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ProfileDetailActivity extends AppCompatActivity {

    TextView usernameprofile, storenameprofile, descriptionprofile, storelocationprofile, emailprofile, websiteprofile;
    Button changeInfo, changepic;
    ImageView profile_pic;
    FirebaseFirestore fstoreprofile;
    FirebaseAuth fAuth;
    FirebaseStorage fStorage;
    StorageReference storeRef;
    CollectionReference profileRef;
   // DocumentReference docRef;
    String userId;
    String profilePrimaryKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        profilePrimaryKey = getIntent().getStringExtra("Profile Primary Key");

        usernameprofile = findViewById(R.id.usernameprofile);
        storenameprofile = findViewById(R.id.storenameprofile);
        descriptionprofile = findViewById(R.id.descriptionprofile);
        storelocationprofile = findViewById(R.id.storelocationprofile);
        emailprofile = findViewById(R.id.emailprofile);
        websiteprofile = findViewById(R.id.websiteprofile);

        profile_pic = findViewById(R.id.image_profile);
        changepic = findViewById(R.id.changeImageProfilePic);
        changeInfo = findViewById(R.id.changeInfoProfile);

        fAuth = FirebaseAuth.getInstance();
        fstoreprofile = FirebaseFirestore.getInstance();

        fStorage = FirebaseStorage.getInstance();
        storeRef = fStorage.getReference("User_Image");

        //userId = fAuth.getCurrentUser().getUid();
        userId = fAuth.getCurrentUser().getUid();


        DocumentReference docRef = fstoreprofile.collection("Seller").document(userId);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                usernameprofile.setText(documentSnapshot.getString("Username"));
                storenameprofile.setText(documentSnapshot.getString("StoreName"));
                storelocationprofile.setText(documentSnapshot.getString("Store_Location"));
                descriptionprofile.setText(documentSnapshot.getString("Store_Description"));
                websiteprofile.setText(documentSnapshot.getString("Website"));;
                emailprofile.setText(documentSnapshot.getString("Email"));

                String imgUrl = documentSnapshot.getString("User_Image");
                Picasso.get().load(imgUrl).placeholder(R.mipmap.ic_launcher).into(profile_pic);

            }
        });
       // userId = FirebaseAuth.getInstance();
        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}
