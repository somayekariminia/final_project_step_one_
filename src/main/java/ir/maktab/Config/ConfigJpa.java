package ir.maktab.Config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConfigJpa {
    private ConfigJpa() {
    }

    public static EntityManagerFactory getInstance() {
        return Persistence.createEntityManagerFactory("PU");
    }
}
