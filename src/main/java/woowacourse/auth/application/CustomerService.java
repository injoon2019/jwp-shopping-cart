package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.SignoutRequest;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer signUp(SignupRequest request) {
        Customer customer = request.toEntity();
        validateEmailDuplicated(customer);
        return customerDao.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(() -> new InvalidCustomerException("이메일에 해당하는 회원이 존재하지 않습니다"));
    }

    public void delete(Customer customer, SignoutRequest request) {
        validatePassword(customer, request.getPassword());
        customerDao.delete(customer.getId());
    }

    public Customer update(Customer customer, CustomerUpdateRequest request) {
        validatePassword(customer, request.getPassword());
        Customer updatedCustomer = new Customer(customer.getId(),
                customer.getEmail(),
                request.getNewPassword(),
                request.getNickname()
        );
        customerDao.update(updatedCustomer);
        return updatedCustomer;
    }

    private void validateEmailDuplicated(Customer customer) {
        if (customerDao.existByEmail(customer.getEmail())) {
            throw new InvalidCustomerException("중복된 이메일 입니다.");
        }
    }

    private void validatePassword(Customer customer, String password) {
        if (!customer.isSamePassword(password)) {
            throw new InvalidAuthException("비밀번호가 달라서 수정할 수 없습니다.");
        }
    }
}

