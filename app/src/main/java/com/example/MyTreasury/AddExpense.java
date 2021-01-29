package com.example.MyTreasury;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddExpense extends AppCompatActivity {

    // request code
    private static final int IMAGE_PICK_CODE = 22;


    public EditText input_date, input_cause, input_amount;
    public RadioGroup radioGroup;
    public RadioButton radioCredit, radioDebit;
    public EditText input_comments;
    public Button load_button , btnUpload, save_button;
    private ImageView imageView;

    //Spinner currency
    public Spinner spinnerCurrency;
    public String selectedSpinnerCurr;

    //Spinner categories OK
    public Spinner spinnerCategory;
    public String selectedSpinnerCat;

    //Spinner state
    public Spinner spinnerState;
    public String selectedSpinnerState;

    //Spinner from (members)
    public Spinner spinnerPayer;
    public String selectedSpinnerPayer;

/*
    RelativeLayout rel_start_date;
    String startDate;
    Calendar myCalendar = Calendar.getInstance();
    */
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;



    public  DatabaseReference mDatabase;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String user_id = mUser.getUid();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenseview);

        mAuth = FirebaseAuth.getInstance();








        input_date = findViewById(R.id.input_date);
        input_cause = findViewById(R.id.input_cause);
        input_amount = findViewById(R.id.input_amount);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        radioGroup = findViewById(R.id.radio_type);
        radioCredit = findViewById(R.id.radioCredit);
        radioDebit = findViewById(R.id.radioDebit);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerPayer = findViewById(R.id.spinnerPayer);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        input_comments = findViewById(R.id.input_comments);
        load_button = findViewById(R.id.load_invoice_button);
        save_button = findViewById(R.id.save_button);
        //mImageView = findViewById(R.id.image_view);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageView = findViewById(R.id.imgView);


        // on pressing btnSelect SelectImage() is called
        load_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });



        createSpinnerCategory();
        createSpinnerPayer();

        //radio group type selection




        //db select

        mDatabase = FirebaseDatabase.getInstance().getReference(user_id).child("Expenses");



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = mDatabase.push().getKey();
                String date = input_date.getText().toString();
                String cause = input_cause.getText().toString();
                Double amount = Double.parseDouble(input_amount.getText().toString());
                String currency = "test";//spinnerCurrency.getSelectedItem().toString();
                String type = "test";


                /*
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioCredit)
                {
                    // do something
                }
                else if (radioGroup.getCheckedRadioButtonId() == R.id.radioDebit){

                }*/



                String state = "test";
                String payer = selectedSpinnerPayer;
                String category = selectedSpinnerCat;
                String subcategory = "test";
                String comments = input_comments.getText().toString();
                String img_id = UUID.randomUUID().toString();
                uploadImage(img_id);
                Data data = new Data(id, date, cause, amount, currency, type, state, payer, category, subcategory, comments, img_id);
                //.trim()
                mDatabase.child(id).setValue(data);
                Intent IntentBack = new Intent(AddExpense.this, MainActivity.class);
                IntentBack.putExtra("frgToLoad", 2);
                startActivity(IntentBack);
            }
        });
    }

    //setStartDate();
    // Select Image method
    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                IMAGE_PICK_CODE);
    }
    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is IMAGE_PICK_CODE and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == IMAGE_PICK_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage(String path)
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/"+ path);

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {


                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddExpense.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddExpense.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

        private void createSpinnerCategory() {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference fDatabaseRoot = database.getReference(user_id);

            final List<String> categoriesArrayList = new ArrayList<String>();

            fDatabaseRoot.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot addressSnapshot: dataSnapshot.getChildren()) {
                        String propertyAddress = addressSnapshot.child("noun").getValue(String.class);
                        if (propertyAddress!=null){
                            categoriesArrayList.add(propertyAddress);
                        }
                    }

                    System.out.println("list of categories : " +categoriesArrayList);

                    ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(AddExpense.this, android.R.layout.simple_spinner_item, categoriesArrayList);
                    addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(addressAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });

            spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedSpinnerCat = categoriesArrayList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });






        }

        private void createSpinnerPayer() {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference fDatabaseRoot = database.getReference(user_id);

            final List<String> membersArrayList = new ArrayList<String>();

            fDatabaseRoot.child("Members").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot addressSnapshot: dataSnapshot.getChildren()) {
                        String propertyAddress = addressSnapshot.child("noun").getValue(String.class);
                        if (propertyAddress!=null){
                            membersArrayList.add(propertyAddress);
                        }
                    }

                    System.out.println("list of categories : " +membersArrayList);

                    ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(AddExpense.this, android.R.layout.simple_spinner_item, membersArrayList);
                    addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPayer.setAdapter(addressAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });

            spinnerPayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedSpinnerPayer = membersArrayList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });






        }

    /*
        private void setStartDate(){
            Calendar startSelectionDate = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.FRANCE);
            startDate = sdf.format(startSelectionDate.getTime());
            input_date.setText(sdf.format(startSelectionDate.getTime()));

        }



        DatePickerDialog.OnDateSetListener dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelFrom();
            }

        };

        private void updateLabelFrom() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.FRANCE);
            startDate = sdf.format(myCalendar.getTime());
            input_date.setText(sdf.format(myCalendar.getTime()));
        }

        public void insertData(final String enter_type, final String enter_status, final String enter_member,
                               final String enter_category, final String enter_link, final String enter_account_name, final String enter_description,
                               final String enter_balance, final String enter_device, final String enter_address,
                               final String enter_parent_cat, final String enter_date){
            final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    Toast.makeText(AddExpense.this,response,Toast.LENGTH_LONG).show();
                    //empty();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(AddExpense.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action","submitData");
                    params.put("EnterType",enter_type);
                    params.put("EnterStatus",enter_status);
                    params.put("EnterMember",enter_member);
                    params.put("EnterCategory",enter_category);
                    params.put("EnterLink",enter_link);
                    params.put("EnterAccountNumber",enter_account_name);
                    params.put("EnterDescription",enter_description);
                    params.put("EnterBalance",enter_balance);
                    params.put("EnterDevice",enter_device);
                    params.put("EnterAddress",enter_address);
                    params.put("EnterParentCat",enter_parent_cat);
                    params.put("EnterDate",enter_date);
                    return params;
                }
            };

    //       int socketTimeOut = 50000;
    //       RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    //       stringRequest.setRetryPolicy(retryPolicy);

            RequestQueue addQueue = Volley.newRequestQueue(AddExpense.this);
            addQueue.add(stringRequest);
        }

     */
        /*
        public void empty(){
            enter_type.setText("");
            enter_status.setText("");
            enter_member.setText("");
            enter_category.setText("");
            enter_link.setText("");
            enter_account_name.setText("");
            enter_description.setText("");
            enter_balance.setText("");
            enter_device.setText("");
            enter_address.setText("");
            enter_parent_cat.setText("");
        }

         */

}

