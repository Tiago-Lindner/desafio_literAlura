package br.com.alura.literAlura.principal;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.DadosLivro;
import br.com.alura.literAlura.model.DadosResposta;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.repository.AutorRepository;
import br.com.alura.literAlura.repository.LivroRepository;
import br.com.alura.literAlura.service.ConsumoApi;
import br.com.alura.literAlura.service.ConverteDados;

import java.util.*;


public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<DadosLivro> dadosLivros = new ArrayList<>();

    private LivroRepository repositorioLivro;
    //private AutorRepository repositorioAutor;

    private List<Livro> livros = new ArrayList<>();

    private Optional<Livro> livroBusca;

    public Principal(LivroRepository repositorioLivro) {
        this.repositorioLivro = repositorioLivro;
    }

//    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
//        this.repositorioLivro = repositorioLivro;
//        this.repositorioAutor = repositorioAutor;
//    }



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

    private void buscarLivroTitulo() {
        DadosLivro dados = getDadosLivro();
        if(!checaLivroExiste(dados.titulo())){
            Livro livro = new Livro(dados);
            repositorioLivro.save(livro);
            System.out.println(livro);

            Autor autor = new Autor(dados.autores().get(0));
            livro.setAutor(autor);
            if (autor.getLivros() == null){
                List<Livro> listaLivros = new ArrayList<>();
                listaLivros.add(livro);
                autor.setLivros(listaLivros);
            } else {
                autor.getLivros().add(livro);
            }
            //repositorioAutor.save(autor);
            repositorioLivro.save(livro);
            System.out.println(autor + "\n");
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
//            System.out.println(dados.resultados().get(0));
//            System.out.println(dados.resultados().get(0).autores());
        }
        return dados.resultados().get(0);
    }

    private void listarLivrosIdioma() {
    }

    private void listarAutoresVivosData() {
    }

    private void listarAutores() {

    }

    private void listarLivros() {
    }

    private boolean checaLivroExiste (String nomeLirvro){
        livroBusca = repositorioLivro.findByTituloContainingIgnoreCase(nomeLirvro);

        if (livroBusca.isPresent()){
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
