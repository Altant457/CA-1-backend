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

            // Store 1000 Point objects in the database:
            em.getTransaction().begin();

            em.getTransaction().commit();

            // Close the database connection:
            em.close();
            emf.close();
        }
    }

