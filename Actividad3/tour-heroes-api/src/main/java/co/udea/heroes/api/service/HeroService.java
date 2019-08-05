package co.udea.heroes.api.service;

import java.util.List;
import java.util.Optional;

import co.udea.heroes.api.model.Hero;

public interface HeroService {
	
	public List<Hero> getHeroes();
	public Hero getHero(int id) ;
	
	public Optional<Hero> searchHeroes(String name);
	
	
	public void deleteHero(int id);
	public void deleteHero(Hero hero);
	public Hero addHero(Hero hero);
	public Hero updateHero(Hero hero);

}
