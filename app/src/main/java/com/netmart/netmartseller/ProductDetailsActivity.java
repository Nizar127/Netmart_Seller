package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.netmart.netmartseller.Model.Products;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView backBtn, productImgView, imgGallery1, imgGallery2, imgGallery3, addImg;
    TextView productName, categoryDetail, productDetailPrice, productDetailDesc, qtyDetail;
    Button editProductDetail;
    CollectionReference productDetailRef;
    private String productID = "";
    private String productPrice;
    private String prodImgUrl;
    Uri imageUri = null;
    StorageReference storageReference;

    Bitmap compressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        productImgView = findViewById(R.id.productImgView);
        imgGallery1 = findViewById(R.id.imgGallery1);
        imgGallery2 = findViewById(R.id.imgGallery2);
        imgGallery3 = findViewById(R.id.imgGallery3);
        addImg = findViewById(R.id.addImg);

        productName = findViewById(R.id.productName);
        categoryDetail = findViewById(R.id.categoryDetail);
        productDetailDesc = findViewById(R.id.productDetailDesc);
        productDetailPrice = findViewById(R.id.productDetailPrice);
        qtyDetail = findViewById(R.id.qtyDetail);
        editProductDetail = findViewById(R.id.editProductDetail);

        backBtn = findViewById(R.id.back_btn_product_details);



        storageReference = FirebaseStorage.getInstance().getReference();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getProductDetails(productID);

        //add qty in here
        qtyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductDetailsActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_qty_product);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                //initialize the variables
                final EditText editQty = bottomSheetDialog.findViewById(R.id.qtyInput);
                Button submitBtn = bottomSheetDialog.findViewById(R.id.btnInput);

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add the data if empty
                        if(editQty.getText().toString().equals("qty")){

                            productDetailRef.document(productID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        //check the data exist
                                        //if not add the data
                                        //if already exist, update it
                                        if (!task.getResult().exists()) {
                                           DocumentReference qtyRef =  productDetailRef.document();

                                            Products products = new Products();
                                            products.setQty(products.getQty());

                                            qtyRef.set(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(ProductDetailsActivity.this, "Quantity Stored in database",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            //update if already exist
                                          String updateQty = editQty.getText().toString();
                                            DocumentReference qtyRef =  productDetailRef.document();

                                            qtyRef.update("qty", updateQty);
                                        }

                                    }
                                }
                            });


                        }
                    }
                });

                //show bottom sheet dialog
          bottomSheetDialog.show();
            }

        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(ProductDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(ProductDetailsActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(ProductDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        choseImage();

                    }

                } else {

                    choseImage();

                }
            }
        });





    }

    private void choseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(ProductDetailsActivity.this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        //productDetailRef.get().notifyAll();
    }

    private void getProductDetails(String productID) {

        productDetailRef = FirebaseFirestore.getInstance().collection("Products");

        productDetailRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String imgProfile = documentSnapshot.getString("productImage");
                    if(imgProfile != null){
                        Picasso.get().load(imgProfile).into(productImgView);
                    }

                    productName.setText(documentSnapshot.getString("name"));
                    productDetailDesc.setText(documentSnapshot.getString("description"));
                    productDetailPrice.setText(documentSnapshot.getString("price"));
                    categoryDetail.setText(documentSnapshot.getString("category"));
                    qtyDetail.setText(documentSnapshot.getString("qty"));
                }
            }
        });



//        productDetailRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    //Products products = documentSnapshot.toObject(Products.class);
//                    productName.setText(documentSnapshot.getString("name"));
//                    //productImgView.setImageResource(documentSnapshot.getString("image").toString());
//                    //productImgView.setImageResource(documentSnapshot(products.getImage()));
//                    productDetailDesc.setText(documentSnapshot.getString("description"));
//                    productDetailPrice.setText(documentSnapshot.getString("price"));
//                    categoryDetail.setText(documentSnapshot.getString("catgeogry"));
//                    Picasso.get().load(prodImgUrl).into(productImgView);
//                    //Picasso.get().load()
//                }
//
//            }
//        });

//        productDetailRef
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                String data = "";
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    Products products = documentSnapshot.toObject(Products.class);
//                    products.setPid(documentSnapshot.getId());
//
//                    String name = products.getName();
//                    String image = products.getImage();
//                    String price = products.getPrice();
//                    String category = products.getCategory();
//                    int qty = products.getQty();
//                    Picasso.get().load(products.getImage()).into(productImgView);
//
//                    //name += productName;
//
//
//                }
//            }
//        });


//        productDetailRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                String data = "";
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    Products products = documentSnapshot.toObjects(Products.class);
//
//                }
//
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                addImg.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductDetailsActivity.this, MyProductActivity.class);
        startActivity(intent);
    }
}
