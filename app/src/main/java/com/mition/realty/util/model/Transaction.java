package com.mition.realty.util.model;

import java.io.Serializable;

/**
 * Created by dn2757 on 2017. 12. 6..
 */

public class Transaction implements Serializable {
    public String _id;
    public String sender;
    public String sender_name;
    public String recipient;
    public String path;
    public String file_name;
}
