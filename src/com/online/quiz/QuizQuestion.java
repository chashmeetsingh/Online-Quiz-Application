package com.online.quiz;

public class QuizQuestion {

    private int questionNumber;
    private String question;
    private String questionOptions[];
    private int correctOptionIndex;
    private int userSelected = -1;

    //Status of Current Question
    public int getUserSelected() {
        return userSelected;
    }

    //Setting Question Selection
    public void setUserSelected(int userSelected) {
        this.userSelected = userSelected;
    }

    //Getting current Question
    public String getQuestion() {
        return question;
    }

    //Getting Current Question Number
    public int getQuestionNumber() {
        return questionNumber;
    }

    //Setting Question Number
    public void setQuestionNumber(int i) {
        questionNumber = i;
    }

    int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    private String[] getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestion(String s) {
        question = s;
    }

    public void setCorrectOptionIndex(int i) {
        correctOptionIndex = i;
    }

    public void setQuestionOptions(String[] s) {
        questionOptions = s;
    }

    //Converting to String
    public String toString() {
        String str = questionNumber + "." + getQuestion();
        for (String option : getQuestionOptions()) {
            str = str + option + "  ";
        }
        str = str + "\n Correct Answer : " + getCorrectOptionIndex();
        return str;
    }

}
