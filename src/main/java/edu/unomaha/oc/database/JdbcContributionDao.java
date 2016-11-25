package edu.unomaha.oc.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.unomaha.oc.domain.Contribution;

public class JdbcContributionDao implements ContributionDao {

	@Autowired
	private DataSource ds;
	
	private static String CONTRIBUTION_FIELDS = "id, owner, story, order, title, body, status";
	
	@Override
	public List<Contribution> searchByOwner(int owner) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", owner);
		
		List<Contribution> contributions = template.query("SELECT " + CONTRIBUTION_FIELDS + " FROM Contribution "
				+ "WHERE owner = :owner", paramMap, new ContributionRowMapper()); 
		
		return contributions;
	}
	
	@Override
	public List<Contribution> searchByStory(int story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", story);
		
		List<Contribution> contributions = template.query("SELECT " + CONTRIBUTION_FIELDS + " FROM Contribution "
				+ "WHERE story = :story", paramMap, new ContributionRowMapper()); 
		
		return contributions;
	}

	@Override
	public Contribution read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + CONTRIBUTION_FIELDS + " from Contribution where id = :id", paramMap, new ContributionRowMapper());
	}

	@Override
	public Number update(int id, Contribution contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("updates", "stuff");
		
		template.update("UPDATE users SET (:updates) WHERE id=:id ", paramMap);
		
		return 0;
	}

	@Override
	public Number create(Contribution contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("value", "stuff");
		
		template.update("insert into Contribution values (....)", paramMap, keyHolder, new String[]{"id"});
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		template.update("DELETE FROM Contribution WHERE id = :id", paramMap);
	}

}
