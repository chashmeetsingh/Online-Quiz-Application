package com.online.quiz;

public class QuizQuestion {

    int questionNumber;
    String question;
    String questionOptions[];
    int correctOptionIndex;
    int userSelected = -1;

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

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String[] getQuestionOptions() {
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
