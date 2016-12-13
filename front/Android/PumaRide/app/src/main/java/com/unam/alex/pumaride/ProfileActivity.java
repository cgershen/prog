package com.unam.alex.pumaride;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.activity_profile_photo)
    ImageView photoProfile;
    final private String imageResourseName = "MyImage";
    private String imagePath;
    Uri selectedImageUri;
    @BindView(R.id.activity_profile_first_name)
    EditText etFristName;
    @BindView(R.id.activity_profile_last_name)
    EditText etLastName;
    @BindView(R.id.activity_profile_mail)
    TextView tvMail;
    @BindView(R.id.activity_profile_about)
    EditText etAbout;

    private static final int SELECT_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        init();
        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
    }
    public void init(){
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        String token = sp.getString("token", "");
        String email = sp.getString("email","");
        String first_name = sp.getString("first_name","");
        String last_name = sp.getString("last_name","");
        String about_me = sp.getString("aboutme","");
        etFristName.setText(first_name);
        etLastName.setText(last_name);
        tvMail.setText(email);
        etAbout.setText(about_me);
    }
    public void save(){
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("first_name", etFristName.getText().toString());
        editor.putString("last_name",etLastName.getText().toString());
        editor.putString("aboutme", etAbout.getText().toString());
        editor.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        if (selectedImageUri != null) {
            imagePath = selectedImageUri.toString();
            savedInstanceState.putString(imageResourseName, imagePath);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        imagePath = savedInstanceState.getString(imageResourseName);
        if(imagePath != null){
            selectedImageUri = Uri.parse(imagePath);
            photoProfile.setImageURI(selectedImageUri);
        }
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    //imagePath = getPathFromURI(selectedImageUri);
                    //Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    photoProfile.setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                save();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onDestroy() {
        save();
        setResult(Activity.RESULT_OK);
        super.onDestroy();
    }
}
