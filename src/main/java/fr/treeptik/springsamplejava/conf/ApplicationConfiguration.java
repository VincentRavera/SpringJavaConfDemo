package fr.treeptik.springsamplejava.conf;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import fr.treeptik.springsample.dao.PersonneDAO;
import fr.treeptik.springsample.dao.PersonneJDBCDAO;
import fr.treeptik.springsample.dao.PersonneJPADAO;
import fr.treeptik.springsample.service.PersonneService;
import fr.treeptik.springsample.service.PersonneServiceImpl;

@Configuration // stéréotype de bean de type configuration
@PropertySource(value = "classpath:/config.properties") // location du fichier de property (on peut en mettre plusieurs avec l'annotation @PropertySources
@Import(value = {DataConfig.class})
@ComponentScan(basePackages={"fr.treeptik.springsample.service"})
public class ApplicationConfiguration {
	
	@PersistenceContext
	private EntityManager em;
	//environment : contient les propriétés systèmes, les variables d'environnements et les properties déclarées en propertysource

	@Bean // bean obligatoire pour que les ${...} soit remplacé par les valeurs des properties. Doit être static pour s'assurer que cette phase s'exécute avant celles des autres beans.
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}


	@Bean(initMethod="init", destroyMethod="destroy")//bean métier. l'id du bean est par défaut le nom de la méthode. initMethod et destroyMethod sont respectivement les méthodes d'initatialisation et de destruction du bean.
//	@Lazy //ATTENTION !!! ça peur ruiner les perf
	public PersonneService personneService(PersonneDAO personneDAO) {
		PersonneServiceImpl personneService = new PersonneServiceImpl();
		personneService.setPersonneDAO(personneDAO);
		return personneService;
	}

	@Bean(name = "personneDAO") // renommage le bean en personneDAO
	@Profile("jpa") //bean instancié si le profile jpa est activé
	public PersonneDAO personneJPADAO() {
		PersonneJPADAO personneJPADAO = new PersonneJPADAO();
		personneJPADAO.setEm(em);
		
		return personneJPADAO;
	}

	@Bean(name = "personneDAO")
	@Profile("jdbc")//bean instancié si le profile jdbc est activé
	public PersonneDAO personneJDBCDAO() {
		return new PersonneJDBCDAO();
	}

}
