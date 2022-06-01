package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignupRequest;
import woowacourse.auth.exceptions.DuplicatedEmailException;
import woowacourse.auth.exceptions.NoSuchCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Customer signUp(SignupRequest signupRequest) {
        Customer customer = signupRequest.toEntity();
        validateDuplicated(signupRequest.getEmail());
        return customerDao.save(customer);
    }

    private void validateDuplicated(String email) {
        if (customerDao.existsByEmail(email)) {
            throw new DuplicatedEmailException("이메일이 중복되었습니다");
        }
    }

    public Customer findCustomer(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(NoSuchCustomerException::new);
    }

    public void signOut(Customer customer) {
        customerDao.deleteById(customer.getId());
    }
}
