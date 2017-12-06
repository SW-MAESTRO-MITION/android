package com.mition.realty.util.singleton;

import com.mition.realty.util.model.User;

/**
 * Created by dn2757 on 2017. 12. 6..
 */

public class SingletonUser {
    private volatile static SingletonUser single;
    private volatile static User user;

    public static SingletonUser getInstance() {
        if (single == null) {
            synchronized (SingletonUser.class) {
                if (single == null) {
                    single = new SingletonUser();
                }
            }
        }
        return single;
    }

    private SingletonUser() {

    }

    public synchronized boolean setUser(User user) {
        try {
            this.user = user;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User getUser() {
        return user;
    }
}
