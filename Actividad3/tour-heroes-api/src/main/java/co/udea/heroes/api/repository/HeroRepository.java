package co.udea.heroes.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.udea.heroes.api.model.Hero;

public interface HeroRepository extends JpaRepository<Hero, String>{

	public Optional<Hero> findById(int id); 
	
	public Optional<Hero> findByName(String term);
	
}
