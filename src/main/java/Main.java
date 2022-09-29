import javax.persistence.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
        public static void main(String[] args) {
            // Open a database connection
            // (create a new database if it doesn't exist yet):
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            emf.close();
        }
    }

