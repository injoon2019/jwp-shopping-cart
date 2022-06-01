package woowacourse.auth.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
}
