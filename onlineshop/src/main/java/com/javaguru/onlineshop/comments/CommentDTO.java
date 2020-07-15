package com.javaguru.onlineshop.comments;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class CommentDTO {

    private Long id;
    private Timestamp postedDate;
    @NotEmpty(message = "Message must not be empty.")
    @Size(min = 3, message = "Message must contain at least 3 characters")
    private String message;
    private Long productID;

    public CommentDTO() {
    }

    public CommentDTO(Long id, Timestamp postedDate, String message, Long productID) {
        this.id = id;
        this.postedDate = postedDate;
        this.message = message;
        this.productID = productID;
    }

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
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", postedDate=" + postedDate +
                ", message='" + message + '\'' +
                ", productID=" + productID +
                '}';
    }
}
