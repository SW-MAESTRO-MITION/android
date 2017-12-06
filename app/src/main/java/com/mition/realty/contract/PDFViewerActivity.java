package com.mition.realty.contract;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.mition.realty.R;
import com.mition.realty.model.PDFFile;

import java.io.File;

public class PDFViewerActivity extends AppCompatActivity {

    Intent intent;

    PDFView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        intent = getIntent();
        PDFFile pdfFile = (PDFFile) intent.getSerializableExtra("PDF_FILE");
        pdfview = findViewById(R.id.pdfview);


        File file = new File(pdfFile.getAbsolutepath());
        pdfview.fromFile(file).load();
    }
}
