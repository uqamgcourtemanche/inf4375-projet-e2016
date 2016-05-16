package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.*;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import org.jsoup.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.*;

@Component
public class FetchRandomQuoteTask {

  private static final Logger log = LoggerFactory.getLogger(FetchRandomQuoteTask.class);
  private static final String URL = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=3";

  @Scheduled(cron="*/2 * * * * ?") // à toutes les 2 secondes.
  public void execute() {
    Arrays.asList(new RestTemplate().getForObject(URL, RandomQuote[].class)).stream()
      .map(this::asCitation)
      .map(Citation::toString)
      .forEach(log::info)
      ;
  }

  private Citation asCitation(RandomQuote q) {
    return new Citation(q.id, Jsoup.parse(q.content).text(), q.author);
  }
}

class RandomQuote {
  @JsonProperty("ID") int id;
  @JsonProperty("title") String author;
  @JsonProperty("content") String content;
}
