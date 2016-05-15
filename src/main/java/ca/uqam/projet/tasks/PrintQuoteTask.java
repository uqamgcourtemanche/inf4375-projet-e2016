package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;

@Component
public class PrintQuoteTask {

  private static final Logger log = LoggerFactory.getLogger(PrintQuoteTask.class);

  @Autowired CitationRepository repository;

  // @Scheduled(cron="* * * * * ?") // à toutes les secondes.
  public void printQuote() {
    log.info(repository.findById(1).getContenu());
  }
}
