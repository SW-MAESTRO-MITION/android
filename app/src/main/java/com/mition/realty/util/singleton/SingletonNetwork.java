package com.mition.realty.util.singleton;

import com.mition.realty.util.connection.ConnectionTransaction;
import com.mition.realty.util.connection.ConnectionUser;

/**
 * Created by dn2757 on 2017. 12. 6..
 */

public class SingletonNetwork {
    private volatile static SingletonNetwork single;

    private ConnectionUser connectionUser;
    private ConnectionTransaction connectionTransaction;

    public static SingletonNetwork getInstance() {
        if (single == null) {
            synchronized (SingletonNetwork.class) {
                if (single == null) {
                    single = new SingletonNetwork();
                }
            }
        }
        return single;
    }

    private SingletonNetwork() {
        connectionUser = new ConnectionUser();
    }

    public ConnectionUser getConnctionUser() {
        return connectionUser;
    }

    public ConnectionTransaction getConnectionTransaction() {
        return connectionTransaction;
    }
}
