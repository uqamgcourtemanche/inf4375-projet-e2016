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

  @Scheduled(cron="*/2 * * * * ?") // à toutes les 2 secondes.
  public void execute() {
  }
}
