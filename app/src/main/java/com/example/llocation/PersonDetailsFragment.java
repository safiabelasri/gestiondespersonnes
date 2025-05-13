package com.example.llocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class PersonDetailsFragment extends Fragment {

    private String name, birthDate, phone, photoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView photoImageView;
    public PersonDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonDetailsFragment newInstance(String name, String birthDate, String phone, String photoPath) {
        PersonDetailsFragment fragment = new PersonDetailsFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("birthDate", birthDate);
        args.putString("phone", phone);
        args.putString("photoPath", photoPath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            birthDate = getArguments().getString("birthDate");
            phone = getArguments().getString("phone");
            photoPath = getArguments().getString("photoPath");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_details, container, false);

        TextView nameTextView = view.findViewById(R.id.detail_name);
        TextView birthDateTextView = view.findViewById(R.id.detail_birth_date);
        TextView phoneTextView = view.findViewById(R.id.detail_phone);
        photoImageView = view.findViewById(R.id.detail_photo);


        Button btnCall = view.findViewById(R.id.btnCall);
        Button btnSms = view.findViewById(R.id.btnSms);
        Button btnCamera = view.findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(getContext(), "Aucune application de caméra trouvée", Toast.LENGTH_SHORT).show();
            }
        });

        nameTextView.setText("Nom : " + name);
        birthDateTextView.setText("Date de naissance : " + birthDate);
        phoneTextView.setText("Téléphone : " + phone);

        if (photoPath != null && !photoPath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            photoImageView.setImageBitmap(bitmap);
        } else {
            photoImageView.setImageResource(R.drawable.ic_launcher_foreground); // image par défaut
        }

        // Vérifier et demander les permissions
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS},
                    1);
        }

        // Action appel
        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            } else {
                Toast.makeText(getContext(), "Permission d'appel non accordée", Toast.LENGTH_SHORT).show();
            }
        });

        // Action SMS
        btnSms.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, "Bonjour " + name + "!", null, null);
                Toast.makeText(getContext(), "SMS envoyé à " + phone, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission SMS non accordée", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photoImageView.setImageBitmap(imageBitmap);
        }
    }

}
