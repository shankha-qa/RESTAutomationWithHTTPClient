package com.qa.model;

import java.util.List;
import java.util.ArrayList;

public class ResponseBody {

    public int per_page;
    public List<Data> data = new ArrayList<>();
    public int total;
    public int page;
    public int total_pages;

    public int getPer_page() {
        return per_page;
    }

    public List<Data> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public static class Data {

        public String last_name;
        public int id;
        public String avatar;
        public String first_name;
        public String email;

        public String getLast_name() {
            return last_name;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getEmail() {
            return email;
        }

    }
}
