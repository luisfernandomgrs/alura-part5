package com.luisf.demo.alura.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguagesController {

  @Autowired
  private LanguageRepository repository;

  @GetMapping("/languages")
  public List<Language> getLanguages() {
    List<Language> languages = repository.findAll(Sort.by(Sort.Direction.DESC, "rating"));
    // languages.add(new Language("id", "title", "urlImage", "rating"));
    return languages;
  }

  @GetMapping("/languages-remove")
  public String getLanguagesRemove(String id) {
    try {
      repository.deleteById(id);
      return "Ok";
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  @PostMapping("/languages")
  public ResponseEntity<Language> postLanguages(@RequestBody Language language) {
    // languages.add(new Language("id", "title", "urlImage", "rating"));
    Language postLanguage = repository.save(language);
    return new ResponseEntity<Language>(postLanguage, HttpStatus.CREATED);
  }
}
