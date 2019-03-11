package com.example.news_api_app;

public class item {
    String img;
    String title;
    String url;
    String publishedAt;
     public item(String img,String title,String url,String publishedAt){

         this.title=title;
         this.img=img;
         this.url=url;
         this.publishedAt=publishedAt;
     }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String gettitle() {
        return title;
    }
}
