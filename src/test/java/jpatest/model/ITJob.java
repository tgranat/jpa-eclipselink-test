package jpatest.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * Making this an integration test since they rely on the JPA framework and
 * a running database. 
 */
class ITJob {
    private static final String PERSISTENCE_UNIT_NAME = "jpatest";
    private static EntityManagerFactory factory;

    @BeforeAll
    static void setUp() throws Exception {
    	// Override property in persistence.xml to drop-and-create tables when testing
    	Map<String, String> props = new HashMap<String, String>();
    	props.put("eclipselink.ddl-generation", "drop-and-create-tables");
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Job job = new Job();
        job.setJobDescr("A job");
        job.setSalery(1000);
        em.persist(job);
        em.getTransaction().commit();
        em.close();
    }
 
 
    @Test
    public void testGetJobs() {
    	EntityManager em = factory.createEntityManager();
    	Query q = em.createQuery("select j from Job j");
    	assertEquals(1, q.getResultList().size());
    	
    }

}
