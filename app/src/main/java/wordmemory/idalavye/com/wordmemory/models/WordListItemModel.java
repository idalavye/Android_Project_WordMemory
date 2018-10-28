package wordmemory.idalavye.com.wordmemory.models;

public class WordListItemModel {
    private String word;
    private String word_mean;
    private String date;
    private int img;

    public WordListItemModel(String word, String word_mean, String date, int img) {
        this.word = word;
        this.word_mean = word_mean;
        this.date = date;
        this.img = img;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
