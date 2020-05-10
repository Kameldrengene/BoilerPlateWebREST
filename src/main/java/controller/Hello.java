package controller;

public class Hello {
    private String text = "controller.Hello JSON-world";

    //Names MUST match property name for serialization to work!
    public String getText() {
        return text;
    }

    //Names MUST match property name for serialization to work!
    public void setText(String text) {
        this.text = text;
    }
}
