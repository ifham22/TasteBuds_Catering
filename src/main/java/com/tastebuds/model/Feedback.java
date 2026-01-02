package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "feedback")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feedback {
    @XmlElement(required = true)
    private String orderNo;

    @XmlElement
    private int rating;

    @XmlElement
    private String comment;

    public Feedback() {}

    public Feedback(String orderNo, int rating, String comment) {
        this.orderNo = orderNo;
        setRating(rating); 
        this.comment = comment;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Feedback{orderNo='" + orderNo + "', rating=" + rating +
                "/5, comment='" + comment + "'}";
    }
}