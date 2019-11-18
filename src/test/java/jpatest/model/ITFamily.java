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

class ITFamily {
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
        
        Query q = em.createQuery("select m from Person m");
        // Should be empty (drop-and-create)
        assertTrue(q.getResultList().size() == 0);
        
        Family family1 = new Family();
        family1.setDescription("Family Svensson");
        em.persist(family1);
        
        Family family2 = new Family();
        family2.setDescription("Family Jonsson");
        em.persist(family2);
        
        /*
        Job job1 = new Job();
        job1.setJobDescr("Job 1");
        job1.setSalery(10000);

        Job job2 = new Job();
        job2.setJobDescr("Job 1");
        job2.setSalery(10000);

        Job job3 = new Job();
        job3.setJobDescr("Job 1");
        job3.setSalery(10000);
*/

     // Create 20 persons (2 families x 10 persons)

        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.setFirstName("Sven" + i);
            person.setLastName("Svensson" + i);
            person.setFamily(family1);
            em.persist(person);
        }

        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.setFirstName("Jon" + i);
            person.setLastName("Jonsson" + i);
            person.setFamily(family2);
            em.persist(person);
        }
        
        em.getTransaction().commit();
        em.close();
    }
 

	@Test
	void testGetFamilies() {
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select f from Family f");
		assertTrue(q.getResultList().size() == 2);

		em.close();		
	}

	@Test
	void testGetPersons() {
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select m from Person m");
		assertTrue(q.getResultList().size() == 20);

		em.close();		
	}
	@Test
	void testRemoveFamilyConstraint() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Family family = (Family)em.createQuery("SELECT f FROM Family f WHERE f.description = :description")
				 .setParameter("description", "Family Jonsson")
				 .getSingleResult();

		assertEquals("Family Jonsson", family.getDescription());
		
		// Not allowed to remove family with persons in it
		assertThrows(javax.persistence.RollbackException.class, ()-> {	
			em.remove(family);		
			em.getTransaction().commit();
		});
		em.close();		

	}

}
