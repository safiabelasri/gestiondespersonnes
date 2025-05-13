package com.example.llocation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.llocation.PersonDatabaseHelper;
import com.example.llocation.Person;

import java.io.File;
import java.io.IOException;

public class AddPersonActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private EditText nameEditText, birthEditText, phoneEditText;
    private ImageView photoImageView;
    private Uri imageUri = null;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        nameEditText = findViewById(R.id.input_name);
        birthEditText = findViewById(R.id.input_birth);
        phoneEditText = findViewById(R.id.input_phone);
        photoImageView = findViewById(R.id.photo_view);
        btnSave = findViewById(R.id.btn_save);

        photoImageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE);
        });

        btnSave.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String birth = birthEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String photoPath = imageUri != null ? imageUri.toString() : "";

            if (!name.isEmpty() && !birth.isEmpty()) {
                Person person = new Person(0, name, birth, phone, photoPath);
                new PersonDatabaseHelper(this).insertPerson(person);
                Toast.makeText(this, "Ajouté avec succès", Toast.LENGTH_SHORT).show();
                finish(); // Retour à MainActivity
            } else {
                Toast.makeText(this, "Champs obligatoires manquants", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            photoImageView.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
