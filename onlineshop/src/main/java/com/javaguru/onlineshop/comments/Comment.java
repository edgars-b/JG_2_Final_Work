package com.javaguru.onlineshop.comments;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private Timestamp postedDate;
    @Column(nullable = false)
    private String message;
    @Column(name = "product_id")
    private Long productID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(postedDate, comment.postedDate) &&
                Objects.equals(message, comment.message) &&
                Objects.equals(productID, comment.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postedDate, message, productID);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", postedDate=" + postedDate +
                ", message='" + message + '\'' +
                ", productID=" + productID +
                '}';
    }
}
