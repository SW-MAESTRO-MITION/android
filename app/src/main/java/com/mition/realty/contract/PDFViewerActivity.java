package com.mition.realty.contract;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.mition.realty.R;
import com.mition.realty.util.constant.Constant;
import com.mition.realty.util.model.PDFFile;

import java.io.File;

import im.delight.android.webview.AdvancedWebView;

public class PDFViewerActivity extends AppCompatActivity {

    Intent intent;

    PDFView pdfview;
    AdvancedWebView advancedWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        pdfview = findViewById(R.id.pdfview);

        intent = getIntent();
        advancedWebView = findViewById(R.id.webview);

        PDFFile pdfFile = (PDFFile) intent.getSerializableExtra("PDF_FILE");
        if (pdfFile != null) {
            File file = new File(pdfFile.absolute_path);
            pdfview.fromFile(file).load();
        }

        String pdfFilePath = intent.getStringExtra("PDF_FILE_PATH");
        if (pdfFilePath != null && !pdfFilePath.equals("")) {
//            advancedWebView.getSettings().setLoadWithOverviewMode(true);
//            advancedWebView.getSettings().setUseWideViewPort(true);
//            advancedWebView.getSettings().setJavaScriptEnabled(true);
//            Log.d("gunwoo", Constant.SERVER_URL + pdfFilePath);
//            advancedWebView.loadUrl(Constant.SERVER_URL + pdfFilePath);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse( Constant.SERVER_URL + pdfFilePath), "text/html");
            startActivity(intent);
        }
    }
}
