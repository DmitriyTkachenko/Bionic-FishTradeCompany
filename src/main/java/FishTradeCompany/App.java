package FishTradeCompany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App 
{
    private static final String UNIT_NAME = "FTC";
    private static EntityManagerFactory factory;

    public static void main( String[] args ) {
        factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        EntityManager em = factory.createEntityManager();

    }
}
