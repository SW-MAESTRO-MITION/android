package com.mition.realty.util.connection;

import com.mition.realty.util.model.PDFFile;
import com.mition.realty.util.model.Transaction;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by dn2757 on 2017. 12. 6..
 */

public class ConnectionTransaction {
    CSConnection conn = ServiceGenerator.createService(CSConnection.class);

    public Observable<PDFFile> uploadFile(Map<String, RequestBody> file) {
        return conn.uploadFile(file).subscribeOn(Schedulers.newThread());
    }

    public Observable<Transaction> createTransaction(Map<String, Object> field) {
        return conn.createTransaction(field).subscribeOn(Schedulers.newThread());
    }

    public Observable<Transaction> acceptTransaction(String id) {
        return conn.acceptTransaction(id).subscribeOn(Schedulers.newThread());
    }

    public Observable<Transaction> getSenderTransaction(String id) {
        return conn.getSenderTransaction(id).subscribeOn(Schedulers.newThread());
    }

    public Observable<Transaction> getRecipientTransaction(String id) {
        return conn.getRecipientTransaction(id).subscribeOn(Schedulers.newThread());
    }

    public Observable<List<PDFFile>> getRegisteredContract(String email) {
        return conn.getRegisteredContract(email).subscribeOn(Schedulers.newThread());
    }
}

