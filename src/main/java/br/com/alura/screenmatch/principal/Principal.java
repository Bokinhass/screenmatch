package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;

public class Principal {

  private final String ENDERECO = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=6585022c";
  private final Scanner leitura = new Scanner(System.in);
  private final ConsumoApi consumo = new ConsumoApi();
  private final ConverteDados conversor = new ConverteDados();

  private final SerieRepository serieRepository;

  private List<Serie> series = new ArrayList<>();

  public Principal(SerieRepository serieRepository) {
    this.serieRepository = serieRepository;
  }

  public void exibeMenu() {
    var opcao = -1;

    while (opcao != 0) {
      var menu = """
          1 - Buscar séries
          2 - Buscar episódios
          3 - Listar séries buscadas
          4 - Buscar série por titulo
          5 - Buscar séries por ator
          6 - Listar top 5 séries
          7 - Listar séries por categoria
          8 - Filtar séries por temporada e avaliação
                          
          0 - Sair                                 
          """;

      System.out.println(menu);
      opcao = leitura.nextInt();
      leitura.nextLine();

      switch (opcao) {
        case 1:
          buscarSerieWeb();
          break;
        case 2:
          buscarEpisodioPorSerie();
          break;
        case 3:
          listarSeriesBuscadas();
          break;
        case 4:
          buscarSeriePorTitulo();
          break;
        case 5:
          buscarSeriePorAtor();
          break;
        case 6:
          buscarTop5Series();
          break;
        case 7:
          buscarSeriePorCategoria();
          break;
        case 8:
          filtrarSeriesPorTemporadaEAvaliacao();
          break;
        case 0:
          System.out.println("Saindo...");
          break;
        default:
          System.out.println("Opção inválida");
      }
    }
  }

  private void buscarSerieWeb() {
    DadosSerie dados = getDadosSerie();
    Serie serie = new Serie(dados);

    serieRepository.save(serie);

    //dadosSeries.add(dados);
    System.out.println(dados);
  }

  private DadosSerie getDadosSerie() {
    System.out.println("Digite o nome da série para busca");
    var nomeSerie = leitura.nextLine();
    var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
    return dados;
  }

  private void buscarEpisodioPorSerie() {
    listarSeriesBuscadas();
    System.out.println("Escolha uma série pelo nome:");
    var nomeSerie = leitura.nextLine();

    Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

    if (serie.isPresent()) {
      var serieEncontrada = serie.get();

      List<DadosTemporada> temporadas = new ArrayList<>();

      for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
        var json =
            consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
        DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
        temporadas.add(dadosTemporada);
      }

      temporadas.forEach(System.out::println);

      List<Episodio> episodios = temporadas.stream()
          .flatMap(d -> d.episodios().stream()
              .map(e -> new Episodio(d.numero(), e)))
          .toList();

      serieEncontrada.setEpisodios(episodios);

      serieRepository.save(serieEncontrada);
    }

    System.out.println("Série não encontrada!");
  }

  private void listarSeriesBuscadas() {
    series = serieRepository.findAll();

    series.stream()
        .sorted(Comparator.comparing(Serie::getGenero))
        .forEach(System.out::println);
  }

  private void buscarSeriePorTitulo() {
    System.out.println("Escolha uma serie pelo nome:");
    var nomeSerie = leitura.nextLine();

    Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

    if (serie.isPresent()) {
      System.out.println("Dados da serie: " + serie.get());
    }

    System.out.println("Série não encontrada!");
  }

  private void buscarSeriePorAtor() {
    System.out.println("Qual nome para busca?");
    var nomeAtor = leitura.nextLine();

    List<Serie> series = serieRepository.findByAtorContainingIgnoreCase(nomeAtor);

    series.forEach(s ->
        System.out.println(s.getTitulo() + " - " + s.getAtor()));
  }

  private void buscarTop5Series() {
    List<Serie> serieTop = serieRepository.findTop5ByOrderByAvaliacaoDesc();
    serieTop.forEach(s ->
        System.out.println(s.getTitulo() + " - " + s.getAvaliacao()));
  }

  private void buscarSeriePorCategoria() {
    System.out.println("Digite a categoria/genero:");
    var nomeGenero = leitura.nextLine();

    Categoria categoria = Categoria.fromPt(nomeGenero);
    List<Serie> series = serieRepository.findByGenero(categoria);

    series.forEach(s ->
        System.out.println(s.getTitulo()));
  }

  private void filtrarSeriesPorTemporadaEAvaliacao() {
    System.out.println("Filtrar séries até quantas temporadas? ");
    var totalTemporadas = leitura.nextInt();
    leitura.nextLine();

    System.out.println("Com avaliação a partir de que valor? ");
    var avaliacao = leitura.nextDouble();
    leitura.nextLine();

    List<Serie> filtroSeries = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);

    filtroSeries.forEach(s ->
        System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
  }
}
