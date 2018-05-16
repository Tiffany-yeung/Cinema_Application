package com.qa.business.repository;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;

import org.apache.log4j.Logger;

import com.qa.persistence.domain.Movie;
import com.qa.util.JSONUtil;

public class MovieDBRepository implements IMovieRepository {

//	private static final Logger LOGGER = Logger.getLogger(MovieDBRepository.class);
	
	@PersistenceContext(unitName="primary")
	private EntityManager manager;
	
	@Inject
	private JSONUtil util;
	
	public void setEntityManager (EntityManager manager) {
		this.manager = manager;
	}

	public void setJsonUtil(JSONUtil jsonUtil) {
		this.util = jsonUtil;
	}
	
	@Override
	public String getAllMovies() {
//		LOGGER.info("MovieDBRepository getAllMovies");
		Query query = manager.createQuery("SELECT m FROM Movie m");
		Collection<Movie> movies = (Collection<Movie>) query.getResultList();
		return util.getJSONForObject(movies);
	}
	
	private Movie findAMovie(Long id) {
		return manager.find(Movie.class, id);
	}

	@Override
	public String getAMovie(Long id) {
		Movie aMovie = findAMovie(id);
		if(aMovie!=null) {
			return util.getJSONForObject(aMovie);
		}
		else {
			return "{\"message\":\"movie not found\"}";
		}
	}

	@Override
	@Transactional(REQUIRED)
	public String createAMovie(String movieJSON) {
		Movie aMovie = util.getObjectForJSON(movieJSON, Movie.class);
		manager.persist(aMovie);
		return "{\"message\":\"movie created\"}";
	}

	@Override
	@Transactional(REQUIRED)
	public String deleteAMovie(Long id) {
		Movie oldMovie = findAMovie(id);
		if(oldMovie != null) {
			manager.remove(oldMovie);
			return "{\"message\":\"movie deleted\"}";
		}
		else {
			return "{\"message\":\"movie not found, cannot delete\"}";
		}
	}

	@Override
	@Transactional(REQUIRED)
	public String updateAMovie(String movieCorrection) {
		Movie correctMovie = util.getObjectForJSON(movieCorrection, Movie.class);
		Movie wrongMovie = findAMovie(correctMovie.getId());
		if(wrongMovie != null) {
			manager.merge(correctMovie);
			return "{\"message\":\"movie updated\"}";
		}
		else {
			return "{\"message\":\"movie not found, cannot update\"}";
		}
	}
}