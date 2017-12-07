package com.mition.realty.contract;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mition.realty.R;
import com.mition.realty.util.model.PDFFile;

import java.io.File;

public class PDFFileStorageActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerview;
    public TextView tv_complete;

    PDFFileStorageAdapter pdfFileStorageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdffile_storage);

        toolbar = findViewById(R.id.toolbar);
        tv_complete = findViewById(R.id.tv_complete);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(PDFFileStorageActivity.this, 3));
        if (pdfFileStorageAdapter == null) {
            pdfFileStorageAdapter = new PDFFileStorageAdapter(PDFFileStorageActivity.this);
        }
        recyclerview.setAdapter(pdfFileStorageAdapter);

        pdfFileStorageAdapter.clearPdfFileList();
        recursiveScan(Environment.getExternalStorageDirectory());
        pdfFileStorageAdapter.notifyDataSetChanged();
    }

    private void recursiveScan(File listFile) {
        File[] listFiles = listFile.listFiles();
        if (listFiles == null) return;
        // permission 처리를 해야 한다.

        for (File file : listFiles) {
            if (file.isDirectory()) recursiveScan(file);
            if (file.isFile() && file.getPath().endsWith(".pdf")) {
                PDFFile pdfFile = new PDFFile();
                pdfFile.name=file.getName();
                pdfFile.absolute_path = file.getAbsolutePath();
                pdfFileStorageAdapter.addPdfFileList(pdfFile);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
