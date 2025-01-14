package com.example.rapidaid;

public class FAQsModel {
    private String question, answer;
    private Boolean expandable;

    public FAQsModel() {}

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public FAQsModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.expandable = false;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "FAQsModel{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
