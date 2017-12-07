package com.mition.realty.contract;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mition.realty.R;
import com.mition.realty.util.constant.Constant;
import com.mition.realty.util.model.PDFFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dn2757 on 2017. 12. 2..
 */

public class ContractStateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ContractStateActivity activity;
    List<PDFFile> pdfFileList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_pdffile, parent, false);
        return new PDFFileViewHolder(v);
    }

    public void addPDFFile(PDFFile pdfFile) {
        pdfFileList.add(pdfFile);
    }

    public void clearPDFFile() {
        pdfFileList.clear();
    }

    public ContractStateAdapter(ContractStateActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PDFFileViewHolder viewHolder = (PDFFileViewHolder) holder;
        final PDFFile pdfFile = pdfFileList.get(position);

        String path = pdfFile.path.substring(13, pdfFile.path.length());
        viewHolder.tv_pdffile.setText(path);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse( Constant.SERVER_URL + pdfFile.path), "text/html");
                activity.startActivity(intent);
            }
        });
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
