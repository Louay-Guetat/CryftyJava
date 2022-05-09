package edu.esprit.cryfty.entity.blogs;
import java.util.Date;
import edu.esprit.cryfty.entity.User;

public class BlogComment {
    int id;
    String comment;
    Date postDate;
    int likes;
    int dislikes;
    BlogArticles article;
    User user;

    public BlogComment() {
    }

    public BlogComment(int id, String content, Date postDate, int likes, int dislikes, BlogArticles article, User user) {
        this.id = id;
        this.comment = comment;
        this.postDate = postDate;
        this.likes = likes;
        this.dislikes = dislikes;
        this.article = article;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
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
        return "BlogComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", postDate=" + postDate +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", article=" + article +
                ", user=" + user +
                '}';
    }
}
