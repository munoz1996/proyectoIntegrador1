package co.udea.heroes.api.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.udea.heroes.api.exception.DataNotFoundException;
import co.udea.heroes.api.model.Hero;
import co.udea.heroes.api.service.HeroService;
import co.udea.heroes.api.util.Messages;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class HeroController {
	
	private final Logger log = LoggerFactory.getLogger(HeroController.class);
	
	private HeroService heroService;
	
	@Autowired
    private Messages messages;	
	
	public HeroController(HeroService heroService) {
		this.heroService = heroService;
	}

	/**
	 * List the specified resource.
	 */
	@GetMapping(value = "listar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Buscar todos los heroes", response = Page.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Los heroes fueron buscados", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public ResponseEntity<List<Hero>> getHeros(){
		log.debug("REST request listar todos los heroes");
		return ResponseEntity.ok(heroService.getHeroes());		
	}

	/**
	 * Search  the specified resource.
	 */
	@GetMapping(value = "buscar/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar heroe por name", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe encontrado", response = Hero.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 404, message = "Recurso no encontrado"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public Hero searchHeroes(String name) throws DataNotFoundException{
		 log.debug("busqueda name : {}", name);
		 Optional<Hero> hero = heroService.searchHeroes(name);
		return hero.get();
	}

	/**
	 * Consult the specified resource.
	 */
	@GetMapping(value = "consultar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar heroe por id", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe encontrado", response = Hero.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 404, message = "Recurso no encontrado"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public ResponseEntity<Hero> getHero( @PathVariable("id") int id){
		 log.debug("REST request getHero id : {}", id);
		return ResponseEntity.ok(heroService.getHero(id));
	}

	/**
	 * Remove the specified resource from storage.
	 */
	@RequestMapping(value = "borrar", method=RequestMethod.DELETE)
    @ApiOperation(value = "Eliminar heroe", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe eliminado", response = Hero.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 404, message = "Recurso no encontrado"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public void deleteHero(@RequestBody Hero hero){
		log.debug("REST request deleteHero id : {}", hero.getId());
		heroService.deleteHero(hero);
	}

	/**
	 * Remove the resource by specified identifier from storage.
	 */
	@DeleteMapping(value = "borrar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Borrar heroe por id", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe encontrado", response = Hero.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 404, message = "Recurso no encontrado"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public void deleteHero( @PathVariable("id") int id){
		 log.debug("REST request deleteHero id : {}", id);
		heroService.deleteHero(id);
	}

	/**
	 * Update the specified resource in storage.
	 */
	@PutMapping(value = "actualizar/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualizar heroe", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe actualizado", response = Hero.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 404, message = "Recurso no encontrado"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public void updateHero(@RequestBody Hero hero ){
		 log.debug("REST request updateHero id : {}", hero.getId());
		heroService.updateHero(hero);
	}

	/**
	 * Store a newly created resource in storage.
	 */
	@RequestMapping(value="crear", method=RequestMethod.POST)
	public Hero addHero(@RequestBody Hero hero) throws DataNotFoundException{
		log.debug("Entro a crear");
		if(hero == null){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		return heroService.addHero(hero);		
		
	}
	

}
