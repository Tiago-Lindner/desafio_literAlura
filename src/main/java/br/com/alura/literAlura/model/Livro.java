package br.com.alura.literAlura.model;

public class Livro {

    private String titulo;
    private String autor;
    private String idioma;
    private double nDownloads;

    public Livro (DadosLivro dadosLivro){
        this.titulo = dadosLivro.titulo();
        this.autor = dadosLivro.autor();
        this.idioma = dadosLivro.idiomas();
        this.nDownloads = dadosLivro.nDownloads();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getnDownloads() {
        return nDownloads;
    }

    public void setnDownloads(double nDownloads) {
        this.nDownloads = nDownloads;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idioma='" + idioma + '\'' +
                ", nDownloads=" + nDownloads +
                '}';
    }
}
