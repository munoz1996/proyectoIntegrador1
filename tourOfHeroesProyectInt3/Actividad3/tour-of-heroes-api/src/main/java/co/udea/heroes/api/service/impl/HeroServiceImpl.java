package co.udea.heroes.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.udea.heroes.api.exception.DataNotFoundException;
import co.udea.heroes.api.model.Hero;
import co.udea.heroes.api.repository.HeroRepository;
import co.udea.heroes.api.service.HeroService;
import co.udea.heroes.api.util.Messages;

@Service
public class HeroServiceImpl implements HeroService {
	
	private final Logger log = LoggerFactory.getLogger(HeroServiceImpl.class);
	
	private Messages messages;	
	private HeroRepository heroRepository;

	public HeroServiceImpl(HeroRepository heroRepository, Messages messages) {
		this.heroRepository = heroRepository;
		this.messages = messages;
	}

	@Override
	public List<Hero> getHeroes() {
		log.debug("Inicio getHeroes");
		List<Hero> heroes= heroRepository.findAll();
		log.debug("Fin getHeroes");
		return heroes;
	}
	
	@Override
	public Hero getHero(int id) {
		log.debug("Inicio getHero: id = {}", id);
		Optional<Hero> hero = heroRepository.findById(id);
		if(!hero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		log.debug("Fin getHero: heroe = {}", hero.get());
		return hero.get();
	}
	
	@Override
	public Optional<Hero> searchHeroes(String name) {
		log.debug("Inicio getHero: name = {}", name);
		Optional<Hero> hero = heroRepository.findByName(name);
		if(!hero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		log.debug("Fin getHero: heroe = {}", hero.get());
		return hero;
	}
	
	@Override
	public void deleteHero(int id) {
		log.debug("Inicio getHero: id = {}", id);
		Optional<Hero> hero = heroRepository.findById(id);
		heroRepository.delete(hero.get());
	}
	
	@Override
	public void deleteHero(Hero hero) {
		heroRepository.delete(hero);		
	}
	
	@Override
	public Hero updateHero(Hero hero) {
		return heroRepository.save(hero);
	}
	
	@Override
	public Hero addHero(Hero hero) {
		List<Hero> heroes= heroRepository.findAll();
		hero.setId(heroes.size());
		return heroRepository.save(hero);
	}
	
//	@Override
//	public Hero updateHero(Hero hero) {
//		log.debug("Inicio updateHero: id = {}", hero.getId());
//		heroRepository.save(hero);
//		log.debug("Fin updateHero: heroe = {}", hero.getId());
//		return hero;
//		
//	}

}
