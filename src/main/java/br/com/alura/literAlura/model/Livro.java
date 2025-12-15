package br.com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String nomeAutor;
    private String idioma;
    private Integer nDownloads;

//    @OneToOne (mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autor;

    public Livro(){}

//    public Livro (DadosLivro dadosLivro){
//        this.titulo = dadosLivro.titulo();
//        this.nomeAutor = dadosLivro.autores().isEmpty()
//                ? "Desconhecido"
//                : dadosLivro.autores().get(0).nome();
//        this.idioma = dadosLivro.idiomas().get(0);
//        this.nDownloads = dadosLivro.nDownloads();
//    }

    public Livro (DadosLivro dadosLivro){
        this.titulo = dadosLivro.titulo();
        this.nomeAutor = dadosLivro.autores().isEmpty()
                ? "Desconhecido"
                : dadosLivro.autores().get(0).nome();
        this.idioma = dadosLivro.idiomas().get(0);
        this.nDownloads = dadosLivro.nDownloads();
        //this.autor = new Autor(dadosLivro.autores().get(0));
//        this.autor.setNome(dadosLivro.autores().get(0).nome());
//        this.autor.setNascimento(dadosLivro.autores().get(0).nascimento());
//        this.autor.setFalescimento(dadosLivro.autores().get(0).falescimento());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getnDownloads() {
        return nDownloads;
    }

    public void setnDownloads(Integer nDownloads) {
        this.nDownloads = nDownloads;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + nomeAutor + '\'' +
                ", idioma='" + idioma + '\'' +
                ", nDownloads=" + nDownloads +
                '}';
    }
}
