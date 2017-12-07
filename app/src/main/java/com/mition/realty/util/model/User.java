package com.mition.realty.util.model;

import java.io.Serializable;

/**
 * Created by dn2757 on 2017. 12. 6..
 */

public class User implements Serializable {
    public String _id;
    public String createdAt;
    public String updatedAt;

    public String name;
    public String email;
    public String password;

    //    public String address;
    public String transaction_id;
    public String type_of_party;
    public boolean is_checked_registered_contract;
}
