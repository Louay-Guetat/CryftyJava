package edu.esprit.cryfty.entity.blogs;

import javafx.scene.control.DatePicker;

import java.sql.Date;

public class BlogArticles {
    private int id;
    private String title;
    private String contents;
    private String image;
    private String category;
    private String author;
    private Date date;
    private String ratingMoy;

    public BlogArticles(int id, String title, String contents, String category, String author, Date date) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.author = author;
        this.date = date;
    }

    public BlogArticles(String title, String contents, String category,String author, DatePicker date, String image) {
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.author = author;
        this.date = Date.valueOf(date.getValue());
        this.image=image;
    }

    public BlogArticles(String btitle, String bcontents, String bcategory, String bauthor, java.util.Date bdate) {

    }

    public BlogArticles() {

    }

    public BlogArticles(String title, String contents, String category, String author, DatePicker date) {
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.author = author;
        this.date = Date.valueOf(date.getValue());
    }

    public BlogArticles(int idd, String btitle, String bcontents, String bcategory, String bauthor, java.util.Date bdate) {
    }

    public String getRatingMoy() {
        return ratingMoy;
    }

    public void setRatingMoy(String ratingMoy) {
        this.ratingMoy = ratingMoy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Articles{" + "id=" + id + ", title=" + title + ", contents=" + contents + ", category=" + category +", author=" + author +", date=" + date + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlogArticles other = (BlogArticles) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
