package com.duyle.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityUpdateSach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sach);

        EditText edtTenSach = findViewById(R.id.edt_ten_sach);
        EditText edtNamXB = findViewById(R.id.edt_nam_xb);
        EditText edtTacgia = findViewById(R.id.edt_tac_gia);

        SachModel sachModel = (SachModel) getIntent().getSerializableExtra("sach");

        edtTenSach.setText(sachModel.getTenSach());
        edtNamXB.setText(sachModel.getYear() + "");
        edtTacgia.setText(sachModel.getTacGia());

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSach = edtTenSach.getText().toString();
                String namXB = edtNamXB.getText().toString();
                String tacGia = edtTacgia.getText().toString();

                // validate khac rong
                int yearXB = Integer.parseInt(namXB);

                SachModel sachModel1 = new SachModel(tenSach, yearXB, tacGia);

                Intent data = new Intent();
                data.putExtra("sach", sachModel1);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}