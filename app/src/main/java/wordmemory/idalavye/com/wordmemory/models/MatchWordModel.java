package wordmemory.idalavye.com.wordmemory.models;

public class MatchWordModel {
    private String word;
    private String word_mean;
    private int location;
    private int correctAnswerLocation;

    public MatchWordModel(){

    }

    public MatchWordModel(String word, String word_mean, int location, int correctAnswerLocation) {
        this.word = word;
        this.word_mean = word_mean;
        this.location = location;
        this.correctAnswerLocation = correctAnswerLocation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_mean() {
        return word_mean;
    }

    public void setWord_mean(String word_mean) {
        this.word_mean = word_mean;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getCorrectAnswerLocation() {
        return correctAnswerLocation;
    }

    public void setCorrectAnswerLocation(int correctAnswerLocation) {
        this.correctAnswerLocation = correctAnswerLocation;
    }
}
