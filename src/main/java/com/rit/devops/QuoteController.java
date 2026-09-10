package com.rit.devops;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Random;
 
@RestController
public class QuoteController {
 
    private final List<String> quotes = List.of(
        "Automate everything that you do more than twice.",
        "It works on my machine is not a deployment strategy.",
        "<-- REPLACE THIS WITH A QUOTE OF YOUR OWN -->"
    );
 
    @GetMapping("/quote")
    public String quote() {
        return quotes.get(new Random().nextInt(quotes.size()));
    }
 
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
