package ca.uqam.projet.tasks;

import org.slf4j.*;

public class PrintQuoteTask {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public void printQuote() {
    log.info("Insérer citation ici...");
  }
}
