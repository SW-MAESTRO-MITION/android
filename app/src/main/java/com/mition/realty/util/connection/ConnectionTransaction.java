package com.mition.realty.util.connection;

import com.mition.realty.util.model.Transaction;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by dn2757 on 2017. 12. 6..
 */

public class ConnectionTransaction {
    CSConnection conn = ServiceGenerator.createService(CSConnection.class);

    public Observable<Transaction> getSenderTransaction(String id) {
        return conn.getSenderTransaction(id).subscribeOn(Schedulers.newThread());
    }

    public Observable<Transaction> getRecipientTransaction(String id) {
        return conn.getRecipientTransaction(id).subscribeOn(Schedulers.newThread());
    }

    public Observable<Transaction> acceptTransaction(String id) {
        return conn.acceptTransaction(id).subscribeOn(Schedulers.newThread());
    }
}

