package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.netmart.netmartseller.Profile.EditProfileActivity;
import com.theartofdev.edmodo.cropper.CropImage;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class AddProductActivity extends AppCompatActivity {

    private TextView getCategory;

    private String categoryName, desc, price, name, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImgUrl, productcategory, productQuantity;
    private Button addNewProdBtn;
    //private int  productQuantity;
    private ImageView inputProdImg, backBtn;
    private EditText inputProdName, inputProdDesc, inputProdPrice, get_category, productqty;
    private static final int GalleryPick = 1;
    private Uri imgUri;
    private StorageReference prodImgRef;
    //private DatabaseReference prodRef;
    FirebaseFirestore DB;
    FirebaseAuth firebaseAuth;
    FirebaseUser userAuth;
    String user_id;
   // CollectionReference prodRef;
    private ProgressDialog loadingBar;

    Bitmap compressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        //categoryName = getIntent().getExtras().get("category").toString();
        prodImgRef = FirebaseStorage.getInstance().getReference();

        DB = FirebaseFirestore.getInstance();
        //prodRef = DB.collection("Products");
       // prodRef = FirebaseDatabase.getInstance().getReference().child("Products");
        //prodRef = FirebaseFirestore.getInstance().collection("Product").document("SellerUpdate").set(prodRef);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        addNewProdBtn = findViewById(R.id.add_new_product);

        inputProdImg = findViewById(R.id.select_product_image);
        backBtn = findViewById(R.id.back_btn_add_new);
        inputProdName = findViewById(R.id.product_name);
        inputProdDesc = findViewById(R.id.product_desc);
        inputProdPrice = findViewById(R.id.product_price);
         get_category = findViewById(R.id.get_category);
         productqty = findViewById(R.id.product_qty);

        //getCategory.setText(categoryName.toUpperCase());

        loadingBar = new ProgressDialog(this);

        inputProdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openGallery()
                CropImage.activity(imgUri)
                        .setAspectRatio(1,1)
                        .start(AddProductActivity.this);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addNewProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imgUri = result.getUri();

            inputProdImg.setImageURI(imgUri);
        }
        else{
            Toast.makeText(this, "Error occurred, please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidateProductData() {
        name = inputProdName.getText().toString().trim();
        desc = inputProdDesc.getText().toString().trim();
        price = inputProdPrice.getText().toString().trim();
        productcategory = get_category.getText().toString().trim();
        productQuantity = productqty.getText().toString().trim();


        if (imgUri == null) {
            Toast.makeText(this, "Product image required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Product name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "Product description required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Product price required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productcategory)){
            Toast.makeText(this, "Category is needed", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productQuantity)){
            Toast.makeText(this, "Quantity is needed", Toast.LENGTH_SHORT).show();
        }

        else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

        compressed = ((BitmapDrawable) inputProdImg.getDrawable()).getBitmap();

        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //To create a unique product random key, so that it doesn't overwrite other product
        productRandomKey = saveCurrentDate + saveCurrentTime;



        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(desc)&&!TextUtils.isEmpty(price)&&!TextUtils.isEmpty(productcategory)&&imgUri!=null){


            File newFile = new File(imgUri.getPath());

            try {
                compressed = new Compressor(AddProductActivity.this)
                        .setMaxHeight(125)
                        .setMaxWidth(125)
                        .setQuality(50)
                        .compressToBitmap(newFile);

            } catch (IOException e){

                e.printStackTrace();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] thumbData = byteArrayOutputStream.toByteArray();
            UploadTask image_path = prodImgRef.child("user_image").child(user_id + ".jpg").putBytes(thumbData);
            image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        saveProduct(task,name,desc,price,productcategory,productQuantity);
                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(AddProductActivity.this,"(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                }
            });



            // final StorageReference filePath = prodImgRef.child(imgUri.getLastPathSegment() + productRandomKey + ".jpg");

       // final UploadTask uploadTask = filePath.putFile(imgUri);

//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                String message = e.toString();
//                Toast.makeText(AddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                loadingBar.dismiss();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(AddProductActivity.this, "Product Image uploaded", Toast.LENGTH_SHORT).show();
//
//                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                        if (!task.isSuccessful()) {
//                            throw task.getException();
//                        }
//
//                        downloadImgUrl = filePath.getDownloadUrl().toString();
//                        return filePath.getDownloadUrl();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        if (task.isSuccessful()) {
//                            downloadImgUrl = task.getResult().toString();
//
//                            Toast.makeText(AddProductActivity.this, "Product image url received", Toast.LENGTH_SHORT).show();
//
//                            SaveProductInfoToDatabase();
//                        }
//                    }
//                });
//            }
//        });
    }

    //save to firestore
//    private void SaveProductInfoToDatabase() {
//        HashMap<String, Object> productMap = new HashMap<>();
//        productMap.put("Pid", productRandomKey);
//        productMap.put("date", saveCurrentDate);
//        productMap.put("time", saveCurrentTime);
//        productMap.put("image", downloadImgUrl);
//        productMap.put("category", productcategory);
//        productMap.put("name", name);
//        productMap.put("nameLower", name.toLowerCase());
//        productMap.put("description", desc);
//        productMap.put("price", price);
//
//        //try add(new Product(time, image, category, name,
//
//        DB.collection("Products").document(user_id).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//
//                    loadingBar.dismiss();
//                    Toast.makeText(AddProductActivity.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();
//                    Intent mainIntent = new Intent(AddProductActivity.this, MyProductActivity.class);
//                    startActivity(mainIntent);
//                    finish();
//                } else {
//                    String error = task.getException().getMessage();
//                    Toast.makeText(AddProductActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
//                }
//                loadingBar.dismiss();
//            }
//
//        });
//    }



}

    private void saveProduct(Task<UploadTask.TaskSnapshot> task, String name, String desc, String price, String productcategory, String productQuantity) {
        Map<String, String> productData = new HashMap<>();
        productData.put("Pid", productRandomKey);
        productData.put("name",name);
        productData.put("description", desc);
        productData.put("price", price);
        productData.put("category",productcategory);
        productData.put("qty", productQuantity);
        productData.put("productImage", imgUri.toString());

        DB.collection("Products").add(productData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddProductActivity.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(AddProductActivity.this, MyProductActivity.class);
                        startActivity(mainIntent);
                        finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(AddProductActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
                loadingBar.dismiss();
            }
        });

//        DB.collection("Products").document(user_id).add(productRandomKey,name,desc,price,imgUri).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(AddProductActivity.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();
//                        Intent mainIntent = new Intent(AddProductActivity.this, MyProductActivity.class);
//                        startActivity(mainIntent);
//                        finish();
//                    } else{
//                        String error = task.getException().getMessage();
//                        Toast.makeText(AddProductActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
//
//                    }
//                    loadingBar.dismiss();
//            }
//        });
    }
}
