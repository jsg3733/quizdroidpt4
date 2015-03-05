package jsg3733.washington.edu.quizdroidptfour;

/**
 * Created by jsg3733 on 2/16/2015.
 */
public class Question {

    private String questiontxt;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private int correctAnswer;


    public Question(String Q, String A1, String A2, String A3, String A4, int answer){
        questiontxt = Q;
        answerOne = A1;
        answerTwo = A2;
        answerThree = A3;
        answerFour = A4;
        correctAnswer = answer;
    }

    public String getQuestiontxt(){
        return questiontxt;
    }

    public String getAnswerOne(){
        return answerOne;
    }

    public String getAnswerTwo(){
        return answerTwo;
    }

    public String getAnswerThree(){
        return answerThree;
    }

    public String getAnswerFour(){
        return answerFour;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }


}
