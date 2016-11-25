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

import edu.unomaha.oc.domain.Comment;

public class JdbcCommentDao implements CommentDao {

	@Autowired
	private DataSource ds;
	
	private static String COMMENT_FIELDS = "id, story, commenter, date, comment";
	
	@Override
	public List<Comment> searchByCommenter(int commenter) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("commenter", commenter);
		
		List<Comment> comments = template.query("SELECT " + COMMENT_FIELDS + " FROM Comment "
				+ "WHERE commenter = :commenter", paramMap, new CommentRowMapper()); 
		
		return comments;
	}
	
	@Override
	public List<Comment> searchByStory(int story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", story);
		
		List<Comment> comments = template.query("SELECT " + COMMENT_FIELDS + " FROM Comment "
				+ "WHERE story = :story", paramMap, new CommentRowMapper()); 
		
		return comments;
	}

	@Override
	public Comment read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + COMMENT_FIELDS + " from Comment where id = :id", paramMap, new CommentRowMapper());
	}

	@Override
	public Number update(int id, Comment comment) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("updates", "stuff");
		
		template.update("UPDATE users SET (:updates) WHERE id=:id ", paramMap);
		
		return 0;
	}

	@Override
	public Number create(Comment comment) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("value", "stuff");
		
		template.update("insert into Comment values (....)", paramMap, keyHolder, new String[]{"id"});
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		template.update("DELETE FROM Comment WHERE id = :id", paramMap);
	}

}
