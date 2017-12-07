package com.mition.realty.contract;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mition.realty.R;
import com.mition.realty.util.model.PDFFile;
import com.mition.realty.util.model.User;
import com.mition.realty.util.singleton.SingletonNetwork;
import com.mition.realty.util.singleton.SingletonUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class ContractStateActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerview;
    ContractStateAdapter contractStateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_state);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(ContractStateActivity.this, 3));
        if (contractStateAdapter == null) {
            contractStateAdapter = new ContractStateAdapter(ContractStateActivity.this);
        }
        recyclerview.setAdapter(contractStateAdapter);

        Map field = new HashMap();
        field.put("is_checked_registered_contract", true);
        connectionModifyUser(SingletonUser.getInstance().getUser()._id, field);
        connectionGetRegisteredContract(SingletonUser.getInstance().getUser().email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void connectionModifyUser(String id, Map<String, Object> field) {
        SingletonNetwork.getInstance().getConnctionUser().modifyUser(id, field).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(User user) {
                        if (user != null)
                            SingletonUser.getInstance().setUser(user);
                    }
                });
    }

    public void connectionGetRegisteredContract(String email) {
        SingletonNetwork.getInstance().getConnectionTransaction().getRegisteredContract(email).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PDFFile>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<PDFFile> pdfFileList) {
                        contractStateAdapter.clearPDFFile();
                        if (pdfFileList != null && pdfFileList.size() != 0) {
                            for (PDFFile pdfFile : pdfFileList)
                                contractStateAdapter.addPDFFile(pdfFile);
                        }
                        contractStateAdapter.notifyDataSetChanged();
                    }
                });
    }
}
