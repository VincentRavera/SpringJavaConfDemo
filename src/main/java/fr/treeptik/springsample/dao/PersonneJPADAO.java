package fr.treeptik.springsample.dao;

import javax.persistence.EntityManager;

import fr.treeptik.springsample.model.Personne;

public class PersonneJPADAO implements PersonneDAO {
	
	private EntityManager em;
	
//	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Personne save(Personne personne) {
		em.persist(personne);
		System.out.println("methode JPA");
		return null;
	}

}
