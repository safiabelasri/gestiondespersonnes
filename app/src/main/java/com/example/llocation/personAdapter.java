package com.example.llocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class personAdapter extends BaseAdapter {

    private Context context;
    private List<Person> personList;

    public personAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return personList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = personList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.name_text);
        TextView phoneText = convertView.findViewById(R.id.phone_text);
        ImageView photoView = convertView.findViewById(R.id.photo_view);

        nameText.setText(person.getName());
        phoneText.setText(person.getPhone());

        if (person.getPhotoPath() != null) {
            photoView.setImageURI(android.net.Uri.parse(person.getPhotoPath()));
        }

        return convertView;
    }
}
