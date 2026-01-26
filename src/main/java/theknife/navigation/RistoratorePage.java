package theknife.navigation;

import theknife.models.Ristoratore;
import theknife.models.Utente;

import java.util.Scanner;

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
