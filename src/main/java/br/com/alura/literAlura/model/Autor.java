package br.com.alura.literAlura.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer nascimento;
    private Integer falescimento;

//    @OneToOne
//    private Livro livro;

    @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros;

    public Autor(){}

    public Autor (DadosAutor dadosAutor){
        this.nome = dadosAutor.nome();
        this.nascimento = dadosAutor.nascimento();
        this.falescimento = dadosAutor.falescimento();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getFalescimento() {
        return falescimento;
    }

    public void setFalescimento(Integer falescimento) {
        this.falescimento = falescimento;
    }

//    public Livro getLivro() {
//        return livro;
//    }
//
//    public void setLivro(Livro livro) {
//        this.livro = livro;
//    }

        public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", falescimento=" + falescimento +
                '}';
    }
}
