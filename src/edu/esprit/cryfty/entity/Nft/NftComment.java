package edu.esprit.cryfty.entity.Nft;

import java.util.Date;

public class NftComment {
    int id;
    String content;
    Date postDate;
    int likes;
    int dislikes;
    Nft nft;
    int user;

    public NftComment(){

    }

    public NftComment(int id, String content, Date postDate, int likes, int dislikes, Nft nft, int user) {
        this.id = id;
        this.content = content;
        this.postDate = postDate;
        this.likes = likes;
        this.dislikes = dislikes;
        this.nft = nft;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Nft getNft() {
        return nft;
    }

    public void setNft(Nft nft) {
        this.nft = nft;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NftComment{" +
                "content='" + content + '\'' +
                ", postDate=" + postDate +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", nft=" + nft.title +
                ", user=" + user +
                '}';
    }
}
