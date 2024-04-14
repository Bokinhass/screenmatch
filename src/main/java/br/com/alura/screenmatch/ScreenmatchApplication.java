package br.com.alura.screenmatch;

import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication {

  @Autowired
  private SerieRepository serieRepository;

  public static void main(String[] args) {
    SpringApplication.run(ScreenmatchApplication.class, args);
  }
}
