package com.mition.realty.contract;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mition.realty.R;
import com.mition.realty.model.PDFFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dn2757 on 2017. 12. 2..
 */

public class PDFFileStorageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    PDFFileStorageActivity activity;
    List<PDFFile> pdfFileList = new ArrayList<>();

    public PDFFileStorageAdapter(PDFFileStorageActivity activity) {
        this.activity = activity;
    }

    public void addPdfFileList(PDFFile pdfFile) {
        pdfFileList.add(pdfFile);
    }

    public void clearPdfFileList() {
        pdfFileList.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_pdffile, parent, false);
        return new PDFFileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PDFFileViewHolder viewHolder = (PDFFileViewHolder) holder;
        final PDFFile pdfFile = pdfFileList.get(position);
        viewHolder.tv_pdffile.setText(pdfFile.getName());

        if (pdfFile.isChecked) {
            viewHolder.iv_file_selected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_file_selected.setVisibility(View.GONE);
        }

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.tv_complete.setTextColor(ContextCompat.getColor(activity, R.color.complete));
                activity.tv_complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("PDF_FILE", pdfFile);
                        activity.setResult(100, intent);
                        activity.finish();
                    }
                });
                setAllFileUnchecked(position);
                notifyDataSetChanged();
            }
        });
    }

    private void setAllFileUnchecked(int position) {
        for (PDFFile pdfFile : pdfFileList) {
            pdfFile.isChecked = false;
        }
        pdfFileList.get(position).isChecked = true;
    }

    @Override
    public int getItemCount() {
        return pdfFileList.size();
    }

    public class PDFFileViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public ImageView iv_pdffile;
        public TextView tv_pdffile;
        public ImageView iv_file_selected;

        public PDFFileViewHolder(View itemView) {
            super(itemView);
            container = itemView;

            iv_pdffile = itemView.findViewById(R.id.iv_pdffile);
            tv_pdffile = itemView.findViewById(R.id.tv_pdffile);
            iv_file_selected = itemView.findViewById(R.id.iv_file_selected);
        }
    }
}
