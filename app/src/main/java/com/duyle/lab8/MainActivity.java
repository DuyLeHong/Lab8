package com.duyle.lab8;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SachModel> arrSach = new ArrayList<>();
    SachAdapter sachAdapter;

    ActivityResultLauncher<Intent> getData = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        SachModel sachModel = (SachModel) result.getData().getSerializableExtra("sach");

                        arrSach.set(indexSachEdit, sachModel);
                        sachAdapter.notifyDataSetChanged();

                        luuListDulieu();
                    }
                }
            });

    int indexSachEdit = -1;
    String FILE_NAME = "sv.txt";

    public void luuListDulieu () {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(arrSach);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {

        }

    }

    public void docListDulieu () {
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            arrSach = (ArrayList<SachModel>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvSach = findViewById(R.id.lv_sach);

        docListDulieu();

        if (arrSach.size() == 0) {
            arrSach.add(new SachModel("Sách 1", 1998, "Tác giả 1"));
            arrSach.add(new SachModel("Sách 2", 2008, "Tác giả 2"));
            arrSach.add(new SachModel("Sách 3", 2012, "Tác giả 3"));
        }

        sachAdapter = new SachAdapter(MainActivity.this, arrSach);

        lvSach.setAdapter(sachAdapter);

        luuListDulieu();



//        lvSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//            }
//        });

    }

    private class SachAdapter extends BaseAdapter {

        Activity activity;
        ArrayList<SachModel> list;

        public SachAdapter(Activity activity, ArrayList<SachModel> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_sach, viewGroup, false);

            SachModel sachModel = list.get(i);

            TextView tvTenSach = view.findViewById(R.id.tv_tensach);
            TextView tvNamXB = view.findViewById(R.id.tv_namxb);
            TextView tvTacgia = view.findViewById(R.id.tv_tacgia);

            tvTenSach.setText(sachModel.getTenSach());
            tvNamXB.setText(sachModel.getYear() + "");
            tvTacgia.setText(sachModel.getTacGia());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indexSachEdit = i;

                    Intent intent = new Intent(getApplicationContext(), ActivityUpdateSach.class);
                    intent.putExtra("sach", arrSach.get(i));

                    getData.launch(intent);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    new AlertDialog.Builder(activity)
                            .setMessage("Bạn có chắc chắn muốn xóa?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int index) {
                            list.remove(i);
                            notifyDataSetChanged();

                            luuListDulieu();
                        }
                    })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();

                    return true;
                }
            });

            return view;
        }
    }
}