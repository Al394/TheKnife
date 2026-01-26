package theknife.navigation;

import java.util.Scanner;

import theknife.models.Ristoratore;
import theknife.models.Utente;

public class RistoratorePage extends Navigation {

  private Ristoratore ristoratore;

  public RistoratorePage(Scanner scanner, Utente ristoratore) {
    super(scanner);
    this.ristoratore = (Ristoratore) ristoratore;
  }

  @Override
  public void start() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'start'");
  }

}
