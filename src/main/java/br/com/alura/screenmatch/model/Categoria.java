package br.com.alura.screenmatch.model;

public enum Categoria {
  ACAO("Action", "Ação"),
  ROMANCE("Romance", "Romance"),
  COMEDIA("Comedy", "Comédia"),
  DRAMA("Drama", "Drama"),
  CRIME("Crime", "Crime");

  private final String categoriaOmdb;

  private final String categoriaPt;

  Categoria(String categoriaOmdb, String categoriaPt) {
    this.categoriaOmdb = categoriaOmdb;
    this.categoriaPt = categoriaPt;
  }

  public static Categoria fromString(String text) {
    for (Categoria categoria : Categoria.values()) {
      if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
        return categoria;
      }
    }
    throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
  }

  public static Categoria fromPt(String text) {
    for (Categoria categoria : Categoria.values()) {
      if (categoria.categoriaPt.equalsIgnoreCase(text)) {
        return categoria;
      }
    }
    throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
  }
}
