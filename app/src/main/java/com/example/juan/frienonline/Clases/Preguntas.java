package com.example.juan.frienonline.Clases;

public class Preguntas {

    private String pregunta;
    private int categoria;
    private boolean isCheck;
    private String descripcionCategoria;
    private String nombreCategoria;

    public Preguntas(String pregunta, int categoria, boolean isCheck, String descripcionCategoria, String nombreCategoria) {
        this.pregunta = pregunta;
        this.categoria = categoria;
        this.isCheck = isCheck;
        this.descripcionCategoria = descripcionCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Preguntas() {
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }
}
