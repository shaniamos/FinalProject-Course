package com.example.myprocedure;

public class ProcedureClass {
    int procedureImage;
    String procedureText;
    String description;
    String url;

    public ProcedureClass(int procedureImage, String procedureText, String description, String url) {
        this.procedureImage = procedureImage;
        this.procedureText = procedureText;
        this.description = description;
        this.url = url;
    }

    public void setProcedureImage(int procedureImage) {
        this.procedureImage = procedureImage;
    }

    public void setProcedureText(String procedureText) {
        this.procedureText = procedureText;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProcedureImage() {
        return procedureImage;
    }

    public String getProcedureText() {
        return procedureText;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
