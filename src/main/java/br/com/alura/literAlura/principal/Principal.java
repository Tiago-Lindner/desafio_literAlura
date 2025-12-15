package br.com.alura.literAlura.principal;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.DadosLivro;
import br.com.alura.literAlura.model.DadosResposta;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.repository.AutorRepository;
import br.com.alura.literAlura.repository.LivroRepository;
import br.com.alura.literAlura.service.ConsumoApi;
import br.com.alura.literAlura.service.ConverteDados;

import javax.sound.midi.SysexMessage;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<DadosLivro> dadosLivros = new ArrayList<>();

    private LivroRepository repositorioLivro;
    private AutorRepository repositorioAutor;

    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    private Optional<Livro> livroBusca;
    private Optional<Autor> autorBusca;

    public Principal(LivroRepository repositorioLivro) {
        this.repositorioLivro = repositorioLivro;
    }

    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }



    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livros pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroTitulo();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosData();
                    break;
                case 5:
                    listarLivrosIdioma();
                    break;
               
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

//    private void buscarLivroTitulo() {
//        DadosLivro dados = getDadosLivro();
//        if(!checaLivroExiste(dados.titulo())){
//            Livro livro = new Livro(dados);
//            repositorioLivro.save(livro);
//            System.out.println(livro);
//
//            Autor autor = new Autor(dados.autores().get(0));
//            livro.setAutor(autor);
//
//                autor.setLivro(livro);
//
//            //repositorioAutor.save(autor);
//            repositorioLivro.save(livro);
//            System.out.println(autor + "\n");
//        } else {
//            System.out.println("Livro já existente no banco. Tente adicionar outro título. \n");
//        }
//
//    }


    private void buscarLivroTitulo() {
        DadosLivro dados = getDadosLivro();
        if(!checaLivroExiste(dados.titulo())){
            Livro livro = new Livro(dados);
            System.out.println(livro);

            autorBusca = repositorioAutor.findByNomeContainingIgnoreCase(dados.autores().get(0).nome());
            if(autorBusca.isPresent()){
                livro.setAutor(autorBusca.get());

            } else {
                Autor autor = new Autor(dados.autores().get(0));

                livro.setAutor(autor);

                List<Livro> listaLivros = new ArrayList<>();
                listaLivros.add(livro);
                livro.getAutor().setLivros(listaLivros);

            }


            repositorioAutor.save(livro.getAutor());
            repositorioLivro.save(livro);
            System.out.println(livro.getAutor() + "\n");
        } else {
            System.out.println("Livro já existente no banco. Tente adicionar outro título. \n");
        }

    }

    private DadosLivro getDadosLivro() {
        System.out.println("Digite o nome do livro para busca");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        System.out.println(json);
        DadosResposta dados = conversor.obterDados(json, DadosResposta.class);

        for (DadosLivro dadosLivros : dados.resultados()) {

            String titulo = dadosLivros.titulo();
            System.out.println(titulo);
        }
        return dados.resultados().get(0);
    }

    private void listarLivrosIdioma() {
        System.out.println("Digite o idioma para a busca: ");
        var idioma = leitura.nextLine();
        List<Livro> livrosIdioma = repositorioLivro.findByIdiomaContainingIgnoreCase(idioma);
        System.out.println("Livros com o idioma" + idioma + ": ");
        livrosIdioma.forEach(System.out::println);
    }

    private void listarAutoresVivosData() {

        System.out.println("Digite o ano para a busca: ");
        Integer ano = leitura.nextInt();
        List<Autor> autoresVivos = repositorioAutor.findByFalescimentoGreaterThan(ano);
        System.out.println("Autores Vivos em " + ano + ": ");
        autoresVivos.forEach(System.out::println);
    }

    private void listarAutores() {
        livros = repositorioLivro.findAll();


        livros.forEach(a ->
                System.out.println(a.getAutor()));

        autores = repositorioAutor.findAll();
        System.out.println(autores.get(0).getLivros());
        System.out.println("aaa");
        autores.forEach(a -> System.out.println(a.getLivros()));

    }

    private void listarLivros() {
        livros = repositorioLivro.findAll();
        livros.forEach(System.out::println);
    }

    private boolean checaLivroExiste (String nomeLivro){
        livroBusca = repositorioLivro.findByTituloContainingIgnoreCase(nomeLivro);

        if (livroBusca.isPresent()){
            return true;
        } else {
            return false;

        }
    }

    private boolean checaAutorExiste (String nomeAutor){
        autorBusca = repositorioAutor.findByNomeContainingIgnoreCase(nomeAutor);

        if (autorBusca.isPresent()){
            return true;
        } else {
            return false;

        }
    }

    private void listarLivrosBuscados(){
        livros = repositorioLivro.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

}
