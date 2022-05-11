package edu.esprit.cryfty.entity.blogs;

import edu.esprit.cryfty.entity.User.User;

public class BlogRating {
    int id;
   Double rating;
    BlogArticles article;
    User user;

    public BlogRating(int id, Double rating, BlogArticles article,User user) {
        this.id = id;
        this.rating=rating;
        this.article = article;
        this.user = user;
    }

    public BlogRating() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public BlogArticles getArticle() {
        return article;
    }

    public void setArticle(BlogArticles article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BlogRating{" +
                "id=" + id +
                ", rating=" + rating +
                ", article=" + article +
                ", user=" + user +
                '}';
    }
}
