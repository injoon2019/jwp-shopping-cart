package woowacourse.auth.dao;

import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.Customer;

@Repository
public class CustomerDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CustomerDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
    }

    public Customer save(Customer customer) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(customer);
        long id = jdbcInsert.executeAndReturnKey(source).longValue();

        return new Customer(id, customer.getEmail(), customer.getNickname(), customer.getPassword());
    }

    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT id, email, nickname, password FROM customer WHERE email = :email";
        SqlParameterSource parameters = new MapSqlParameterSource("email", email);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS (SELECT * FROM customer where email = :email)";
        return jdbcTemplate.queryForObject(sql, Map.of("email", email), Boolean.class);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM customer where id = :id";
        jdbcTemplate.update(sql, Map.of("id", id));
    }

    private RowMapper<Customer> customerRowMapper() {
        return (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            String email = resultSet.getString("email");
            String nickname = resultSet.getString("nickname");
            String password = resultSet.getString("password");
            return new Customer(id, email, nickname, password);
        };
    }
}
