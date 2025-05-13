package com.example.llocation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnAdd;
    private com.example.llocation.personAdapter personAdapter;
    private PersonDatabaseHelper databaseHelper;
    private List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewPersons);
        btnAdd = findViewById(R.id.btnAdd);

        databaseHelper = new PersonDatabaseHelper(this);
        personList = databaseHelper.getAllPersons();

        personAdapter = new personAdapter(this, personList);
        listView.setAdapter(personAdapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
            startActivity(intent);
        });
        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Person selectedPerson = personList.get(position);

            PersonDetailsFragment fragment = PersonDetailsFragment.newInstance(
                    selectedPerson.getName(),
                    selectedPerson.getBirthDate(),
                    selectedPerson.getPhone(),
                    selectedPerson.getPhotoPath()
            );

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        personList.clear();
        personList.addAll(databaseHelper.getAllPersons());
        personAdapter.notifyDataSetChanged();
    }
}
