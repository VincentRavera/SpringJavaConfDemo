package fr.treeptik.springsample.dao;

import fr.treeptik.springsample.model.Personne;

public class PersonneJDBCDAO implements PersonneDAO {

	@Override
	public Personne save(Personne personne) {
		System.out.println("methode JDBC");
		return null;
	}

}
