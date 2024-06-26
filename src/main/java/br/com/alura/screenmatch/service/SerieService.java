package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

  @Autowired
  private SerieRepository serieRepository;

  public List<SerieDTO> getAllSeries() {
    return serieRepository.findAll()
        .stream()
        .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(),
            s.getAtor(), s.getPoster(), s.getSinopse()))
        .collect(Collectors.toList());
  }
}
