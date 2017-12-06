package com.mition.realty.util.connection;

import com.mition.realty.util.model.User;

import java.util.Map;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by dn2757 on 2017. 10. 8..
 */

public class ConnectionUser {
    CSConnection conn = ServiceGenerator.createService(CSConnection.class);

    public Observable<User> createUser(Map<String, Object> field) {
        return conn.createUser(field).subscribeOn(Schedulers.newThread());
    }

    public Observable<User> getUser(String id) {
        return conn.getUser(id).subscribeOn(Schedulers.newThread());
    }

    public Observable<User> login(Map<String, Object> field) {
        return conn.login(field).subscribeOn(Schedulers.newThread());
    }
}
