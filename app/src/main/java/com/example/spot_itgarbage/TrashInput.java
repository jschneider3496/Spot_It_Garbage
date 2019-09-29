package com.example.spot_itgarbage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrashInput extends AppCompatActivity {
    private static SeekBar seekbar;
    private static TextView textview;

    private static DatabaseReference Database;
    private static EditText editText;

    private StorageReference storageRef;
    private FirebaseStorage firebaseStorage;
    private static ImageView imageView;
    private ProgressDialog progressDialog;
    private static final String TAG = "TrashInput";
    private Uri ImageUri;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_input);
        seekBar();
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        Database = FirebaseDatabase.getInstance().getReference();

        firebaseStorage = FirebaseStorage.getInstance();
        ImageUri = null;
        storageRef = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        // SUBMIT BUTTON
        Button submit = (Button) findViewById(R.id.button2);

        imageButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                               startActivityForResult(intent, 0);
//                                               dispatchTakePictureIntent();

                                           }

                                       }
        );

        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          //postPic();
                                          String description = editText.getText().toString().trim();
                                          int rating = seekbar.getProgress();

                                          Bundle location = getIntent().getExtras();
                                          double lat = location.getDouble("lat");
                                          double lng = location.getDouble("lng");
//                                          System.out.println("lat: " + lat + " lng: " + lng);


                                          MarkerData dataEntry = new MarkerData(description, rating, lat, lng, url);
                                          Database.push().setValue(dataEntry);

                                      }
                                  }
        );
    }


//    public String getUrl(){
//        return url;
//    }
//
//    public void setUrl(String url){
//        this.url = url;
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        upload(bitmap);
//        ImageUri = data.getData();

    }


    private void upload(Bitmap photo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        String picName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        byte[] b = stream.toByteArray();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("documentImages").child(picName);


//        String downloadUrl = storageReference.getDownloadUrl().toString();
//        System.out.println("DownloadURL 123: " + downloadUrl);



        //StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);

        storageReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println("NEw thing " + uri.toString());
                        url = (uri.toString());

                    }
                });
                Toast.makeText(TrashInput.this, "uploaded", Toast.LENGTH_SHORT).show();
//                System.out.println("DownloadURL: " + downloadUrl);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TrashInput.this, "failed", Toast.LENGTH_LONG).show();


            }
        });
//        Getting url
//        storageRef.child(picName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.png'
//
////                System.out.println("Download URL NEW: " + );
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
    }

//    private void postPic() {
//        System.out.println("Uploading to Cloud...");
//
//        StorageReference filePath = storageRef.child("Trash_Images/" + UUID.randomUUID().toString());
//
//        filePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//            }
//        });
    //  }

//    static final int REQUEST_TAKE_PHOTO = 1;
//    String currentPhotoPath;
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//    Uri imgUri;
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri imgUri = FileProvider.getUriForFile(this,
//                        "com.example.spot_itgarbage",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//    private String getExtension(Uri uri) {
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
//    }
//    private void fileUploader() {
//        StorageReference Ref = storageRef.child(System.currentTimeMillis()+ "." + getExtension(imgUri));
//
//        Ref.putFile(imgUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                      //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Toast.makeText(TrashInput.this, "Image uploaded", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });
//    }
//public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//    String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//    DatabaseReference ref = FirebaseDatabase.getInstance();
//
//    ref.setValue(imageEncoded);
//}

    public void seekBar() {
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        textview = (TextView) findViewById(R.id.textView2);
        textview.setText(seekbar.getProgress() + "/10");


        seekbar.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textview.setText(progress + "/10");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textview.setText(progress_value + "/10");

                    }
                }
        );
    }
}
