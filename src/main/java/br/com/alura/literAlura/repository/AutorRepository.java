package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeContainingIgnoreCase(String nomeAutor);

//    @Query("SELECT a FROM Autor WHERE a.falescimento > :ano")
//    List<Autor> obterAutoresVivosAno(Integer ano);

    List<Autor> findByFalescimentoGreaterThan(Integer ano);
}

