package com.netmart.netmartseller.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.netmart.netmartseller.ProfileActivity;
import com.netmart.netmartseller.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    Button changeImage, saveprofilebtn;
    EditText usernamechange, sellernamechange, descriptionchange, storelocationchange, emailchange, websitechange;
    ImageView editImgProfile;
    ProgressDialog progressDialog;
    Uri imageUri = null;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser userAuth;
    FirebaseFirestore firebaseFirestore;
    String user_id;
    ProgressBar progressBar;


    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;
    int TAKE_IMAGE_CODE = 10001;

    Bitmap compressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        changeImage = findViewById(R.id.changeImageProfilePic);
        editImgProfile = findViewById(R.id.edit_image_profile);
        usernamechange = findViewById(R.id.usernameChange);
        sellernamechange = findViewById(R.id.sellernameChange);
        descriptionchange = findViewById(R.id.descriptionChange);
        storelocationchange = findViewById(R.id.emailChange);
        websitechange = findViewById(R.id.websiteChange);
        emailchange = findViewById(R.id.emailChange);
        saveprofilebtn = findViewById(R.id.saveProfilebtn);
        progressBar = findViewById(R.id.progressbar);


        firebaseAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();

        //display profile first
        if(userAuth != null){
            Log.d(TAG, "onCreate:" + userAuth.getDisplayName());
            if(userAuth.getDisplayName() != null){
                sellernamechange.setText(userAuth.getDisplayName());
                sellernamechange.setSelection(userAuth.getDisplayName().length());
            }
            if(userAuth.getPhotoUrl() != null){
                //Glide.with(this).load(userAuth.getPhotoUrl()).into(editImgProfile);
                Picasso.get().load(userAuth.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(editImgProfile);
            }

            progressBar.setVisibility(View.GONE);
        }


        //user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Image");

        editImgProfile.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                 if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                                     Toast.makeText(EditProfileActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                                                     ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                                                 } else {
                                                     choseImage();
                                                 }

                                             } else {
                                                 choseImage();
                                             }
                                         }
                                     }
        );

        saveprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Storing Data...");
                progressDialog.show();

                final String username = usernamechange.getText().toString();

                final String sellername = sellernamechange.getText().toString();

                final String description = descriptionchange.getText().toString();

                final String storelocation = storelocationchange.getText().toString();

                final String website = websitechange.getText().toString();

                final String email = emailchange.getText().toString();

                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(sellername)&&!TextUtils.isEmpty(description)&&!TextUtils.isEmpty(storelocation)&&!TextUtils.isEmpty(website)&&!TextUtils.isEmpty(email)&&imageUri!=null){
                    File newFile = new File(imageUri.getPath());
                    try {

                        compressed = new Compressor(EditProfileActivity.this)

                                .setMaxHeight(125)

                                .setMaxWidth(125)

                                .setQuality(50)

                                .compressToBitmap(newFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] thumbData = byteArrayOutputStream.toByteArray();
                    UploadTask image_path = storageReference.child("user_image").child(user_id + ".jpg").putBytes(thumbData);
                    image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                storeData(task,username, sellername, description, storelocation, website, email);

                            } else {

                                String error = task.getException().getMessage();

                                Toast.makeText(EditProfileActivity.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();

                            }

                        }
                    });

            }
        };

    });
    }

    public void updateProfile(final View view){
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        DISPLAY_NAME = sellernamechange.getText().toString();

        userAuth = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(DISPLAY_NAME).build();

        userAuth.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                view.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this, "Succesfully updated profile", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: ", e.getCause());
            }
        });

    }

    public void handleImageClick(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    private void storeData(Task<UploadTask.TaskSnapshot> task, String username, String sellername, String description, String storelocation, String website, String email) {
         Uri download_uri;
        final long ONE_MEGABYTE = 1024 * 1024;
        if(task != null){

            //UploadTask.TaskSnapshot task = storageReference.getDownloadUrl();

            //try to insert into the imageview
              Glide.with(this).load(storageReference).into(editImgProfile);
              download_uri = task.getResult().getUploadSessionUri();
            // download_uri = task.getResult().getTotalByteCount(ONE_MEGABYTE);
         } else{
            download_uri = imageUri;
        }


        Map<String, String> userData = new HashMap<>();
        userData.put("Username", username);
        userData.put("StoreName", sellername);
        userData.put("Store_Description", description);
        userData.put("Store_Location", storelocation);
        userData.put("Website", website);
        userData.put("Email", email);
        userData.put("UserImage", download_uri.toString());

        firebaseFirestore.collection("Seller").document(user_id).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                editImgProfile.setImageURI(imageUri);
                uploadPicture();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
     }
    }

    //upload a picture
    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
                        // Handle unsuccessful uploads
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage("Percentage: " + (int)progressPercent + "%");
            }
        });
    }


    private void choseImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(EditProfileActivity.this);
    }
}
