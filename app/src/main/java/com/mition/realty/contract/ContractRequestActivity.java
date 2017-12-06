package com.mition.realty.contract;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mition.realty.R;
import com.mition.realty.model.PDFFile;

import java.util.Calendar;

public class ContractRequestActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout ll_mail, ll_date, ll_file;
    ImageView iv_date;
    EditText et_email;
    ImageView iv_file;
    TextView tv_date;
    TextView tv_send;
    TextView tv_file;

    int contractYear;
    int contractMonth;
    int contractDay;

    PDFFile pdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_request);

        toolbar = findViewById(R.id.toolbar);

        iv_date = findViewById(R.id.iv_date);
        et_email = findViewById(R.id.et_email);
        tv_date = findViewById(R.id.tv_date);
        iv_file = findViewById(R.id.iv_file);
        tv_file = findViewById(R.id.tv_file);
        tv_send = findViewById(R.id.tv_send);

        ll_mail = findViewById(R.id.ll_mail);
        ll_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ll_date = findViewById(R.id.ll_date);
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv_date.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                contractYear = year;
                contractMonth = monthOfYear - 1;
                contractDay = dayOfMonth;
            }
        };
        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(ContractRequestActivity.this,
                        listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE));
                dialog.show();
            }
        });

        ll_file = findViewById(R.id.ll_file);
        iv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContractRequestActivity.this, PDFFileStorageActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
//                contractYear;
//                contractMonth;
//                contractDay;
//                pdfFile;
                setResult(120);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == 100) {
            pdfFile = (PDFFile) data.getSerializableExtra("PDF_FILE");
            if (pdfFile == null) return;
            tv_file.setText(pdfFile.getName());
        }
    }
}
