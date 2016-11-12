package com.atomickitten.DB;

/**
 * Created by YANG-_-V on 2016-11-08.
 */

public class Item {
    int    _id;
    String product_name;
    String pic_uri;
    String product_category_first;
    String product_category_second;
    int enable;
    String register_date;
    String expire_date;

    public Item() {
    }

    public Item(Item item){
        this._id = item.get_id();
        this.product_name = item.getProduct_name();
        this.pic_uri = item.getPic_uri();
        this.product_category_first = item.getProduct_category_first();
        this.product_category_second = item.getProduct_category_second();
        this.enable = item.getEnable();
        this.register_date = item.getRegister_date();
        this.expire_date = item.getExpire_date();
    }

    public Item(int _id, String product_name, String pic_uri, String product_category_first, String product_category_second, String expire_date) {
        this._id = _id;
        this.product_name = product_name;
        this.pic_uri = pic_uri;
        this.product_category_first = product_category_first;
        this.product_category_second = product_category_second;
        this.expire_date = expire_date;
    }

    public Item(String product_name, String pic_uri, String product_category_first, String product_category_second, String expire_date) {
        this.product_name = product_name;
        this.pic_uri = pic_uri;
        this.product_category_first = product_category_first;
        this.product_category_second = product_category_second;
        this.expire_date = expire_date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPic_uri() {
        return pic_uri;
    }

    public void setPic_uri(String pic_uri) {
        this.pic_uri = pic_uri;
    }

    public String getProduct_category_first() {
        return product_category_first;
    }

    public void setProduct_category_first(String product_category_first) {
        this.product_category_first = product_category_first;
    }

    public String getProduct_category_second() {
        return product_category_second;
    }

    public void setProduct_category_second(String product_category_second) {
        this.product_category_second = product_category_second;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }
}
