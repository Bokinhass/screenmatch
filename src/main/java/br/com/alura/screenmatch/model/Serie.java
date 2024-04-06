package br.com.alura.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {

  private String titulo;
  private Integer totalTemporadas;
  private Double avaliacao;
  private Categoria genero;
  private String ator;
  private String poster;
  private String sinopse;

  public Serie(DadosSerie dadosSerie) {
    this.titulo = dadosSerie.titulo();
    this.totalTemporadas = dadosSerie.totalTemporadas();
    this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
    this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
    this.ator = dadosSerie.ator();
    this.poster = dadosSerie.poster();
    this.sinopse = dadosSerie.sinopse();
    //this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Integer getTotalTemporadas() {
    return totalTemporadas;
  }

  public void setTotalTemporadas(Integer totalTemporadas) {
    this.totalTemporadas = totalTemporadas;
  }

  public Double getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(Double avaliacao) {
    this.avaliacao = avaliacao;
  }

  public Categoria getGenero() {
    return genero;
  }

  public void setGenero(Categoria genero) {
    this.genero = genero;
  }

  public String getAtor() {
    return ator;
  }

  public void setAtor(String ator) {
    this.ator = ator;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getSinopse() {
    return sinopse;
  }

  public void setSinopse(String sinopse) {
    this.sinopse = sinopse;
  }

  @Override
  public String toString() {
    return
        "genero=" + genero +
            ", titulo='" + titulo + '\'' +
            ", totalTemporadas=" + totalTemporadas +
            ", avaliacao=" + avaliacao +
            ", ator='" + ator + '\'' +
            ", poster='" + poster + '\'' +
            ", sinopse='" + sinopse + '\'' +
            '}';
  }
}
