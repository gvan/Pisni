package ua.in.pisni.data.model;

public class Song {

    private int id;
    private String title;
    private String text;
    private String author;
    private String audio_file_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAudio_file_name() {
        return audio_file_name;
    }

    public void setAudio_file_name(String audio_file_name) {
        this.audio_file_name = audio_file_name;
    }
}
