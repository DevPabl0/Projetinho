package br.com.etecia.projetinho;

public class Bicicleta {

    private String modelo;
    private int imagem;

    public Bicicleta(String modelo, int imagem) {
        this.modelo = modelo;
        this.imagem = imagem;
    }

    public String getModelo() {
        return modelo;
    }

    public int getImagem() {
        return imagem;
    }
}
