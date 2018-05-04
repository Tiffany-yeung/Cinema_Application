package com.qa.business.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.qa.business.repository.MovieDBRepository;
import com.qa.persistence.domain.Movie;
import com.qa.util.JSONUtil;

@RunWith(MockitoJUnitRunner.class)
public class MovieDBServiceTest {

	@InjectMocks
	private MovieDBRepository repo;

	@Mock
	private EntityManager manager;

	@Mock
	Query query;

	private JSONUtil jsonUtil = new JSONUtil();

	private static final String MOCK_LIST = "[{\"title\":\"Life of Nabeel\",\"genre\":\"horror\",\"rating\":\"18\"}]";
	private static final String MOCK_OBJECT = "{\"title\":\"Life of Nabeel\",\"genre\":\"horror\",\"rating\":\"18\"}";

	@Before
	public void testInit() {
		jsonUtil = new JSONUtil();
		repo.setEntityManager(manager);
		repo.setJsonUtil(jsonUtil);
	}

	@Test
	public void createAMovieTest() {
		String expected = repo.createAMovie(MOCK_OBJECT);
		assertEquals(expected, "{\"message\":\"movie created\"}");
	}

	@Test
	public void deleteAMovieTest() {
		String expected = repo.deleteAMovie(1L);
		assertEquals(expected, "{\"message\":\"movie not found, cannot delete\"}");
	}

	@Test
	public void updateAMovieTest() {
		String expected = repo.updateAMovie(MOCK_OBJECT);
		assertEquals(expected, "{\"message\":\"movie not found, cannot update\"}");
	}

	@Test
	public void getAllMovies() {
		Mockito.when(manager.createQuery(Mockito.anyString())).thenReturn(query);
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie("Life of Nabeel", "horror", "18"));
		Mockito.when(query.getResultList()).thenReturn(movies);
		assertEquals(MOCK_LIST, repo.getAllMovies());
	}

}
