package com.example.table;

import java.util.List;

public class List_documents {
    List<Document> documents;
    private Integer total;

    private class Document{
        private String $collectionId;
        private String createdAt;
        private Data data;
        private String databaseId;
        private String id;

        public Data getData(){
            return this.data;
        }
    }

    private class Data{
        public String name;
        private String surname;
        private Integer age;
        private Integer rate;
        private String email;
        private String about;
        private List<String> games;
        private List<String> hashtags;
        private List<Integer> hostedEvents;
        private List<Integer> currentEvents;
        private String phone;
        private String sex;
        private String userID;

        public String getName(){
            return this.name;
        }
    }

    public String getName(){
        return this.documents.get(0).getData().getName();
    }
}
