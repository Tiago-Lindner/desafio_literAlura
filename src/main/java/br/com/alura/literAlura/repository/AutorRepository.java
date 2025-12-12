package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Livro> findByNomeContainingIgnoreCase(String nomeAutor);
}

