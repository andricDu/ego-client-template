package bio.overture.egoclienttemplate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping
  public boolean testGet() {
    return true;
  }

  @PostMapping
  public boolean testPost() {
    return true;
  }

}
