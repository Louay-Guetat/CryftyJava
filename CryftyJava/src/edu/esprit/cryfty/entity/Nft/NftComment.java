//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.entity.Nft;

import edu.esprit.cryfty.entity.User;
import java.util.Date;

public class NftComment {
    int id;
    String content;
    Date postDate;
    int likes;
    int dislikes;
    Nft nft;
    User user;

    public NftComment() {
    }

    public NftComment(int id, String content, Date postDate, int likes, int dislikes, Nft nft, User user) {
        this.id = id;
        this.content = content;
        this.postDate = postDate;
        this.likes = likes;
        this.dislikes = dislikes;
        this.nft = nft;
        this.user = user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return this.postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return this.dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Nft getNft() {
        return this.nft;
    }

    public void setNft(Nft nft) {
        this.nft = nft;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "NftComment{content='" + this.content + '\'' + ", postDate=" + this.postDate + ", likes=" + this.likes + ", dislikes=" + this.dislikes + ", nft=" + this.nft.title + ", user=" + this.user + '}';
    }
}
