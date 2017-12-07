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
import android.widget.Toast;

import com.mition.realty.R;
import com.mition.realty.util.model.PDFFile;
import com.mition.realty.util.model.Transaction;
import com.mition.realty.util.model.User;
import com.mition.realty.util.singleton.SingletonNetwork;
import com.mition.realty.util.singleton.SingletonUser;

import java.io.File;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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
                if (email == null || email.equals("")) {
                    Toast.makeText(ContractRequestActivity.this, "보내실 이메일을 입력해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }

                String pdfFileName = tv_file.getText().toString();
                if (pdfFileName == null || pdfFileName.equals("")) {
                    Toast.makeText(ContractRequestActivity.this, "보내실 계약서 파일을 첨부해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }

                String date = tv_date.getText().toString();
                if (date == null || date.equals("")) {
                    Toast.makeText(ContractRequestActivity.this, "계약 일자를 입력해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = SingletonUser.getInstance().getUser();
                File file = new File(pdfFile.absolute_path);
                String mimeType = URLConnection.guessContentTypeFromName(file.getName());

                Map field = new HashMap();
                field.put("sender_name", user.name);
                field.put("sender", user.email);
                field.put("recipient", et_email.getText().toString());
                field.put("date", "" + contractYear + contractMonth + contractDay);

                connectionUploadFile(field, file, mimeType);
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
            tv_file.setText(pdfFile.name);
        }

        // MultipartBody.Part is used to send also the actual file name
    }
//                uploadFile(RequestBody.create(MediaType.parse("application/pdf"), file)).

    public void connectionUploadFile(final Map<String, Object> field, File file, String mimeType) {
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), file);
        map.put("contract_file\"; filename=\"" + file.getName(), fileBody);

        SingletonNetwork.getInstance().getConnectionTransaction().uploadFile(map).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PDFFile>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(PDFFile pdfFile) {
                        connectionCreateTransaction(field, pdfFile);
                    }
                });
    }

    public void connectionCreateTransaction(Map<String, Object> field, PDFFile pdfFile) {
        field.put("absolute_path", pdfFile.absolute_path);
        field.put("file_name", pdfFile.name);
        SingletonNetwork.getInstance().getConnectionTransaction().
                createTransaction(field).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Transaction>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Transaction user) {
                        setResult(120);
                        finish();
                    }
                });
    }
}
