package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignupRequest;
import woowacourse.auth.exceptions.NoSuchCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer signUp(SignupRequest signupRequest) {
        Customer customer = signupRequest.toEntity();
        return customerDao.save(customer);
    }

    public Customer findCustomer(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(NoSuchCustomerException::new);
    }
}
