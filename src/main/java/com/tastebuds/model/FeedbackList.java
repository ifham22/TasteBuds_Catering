package com.tastebuds.model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "feedbacks")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeedbackList {

    @XmlElement(name = "feedback")
    private List<Feedback> feedbacks;

    public FeedbackList() {
        this.feedbacks = new ArrayList<>();
    }

    public FeedbackList(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
    }
}