package woowacourse.auth.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignupRequest;
import woowacourse.auth.dto.SignupResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<SignupResponse> signUp(@RequestBody @Valid SignupRequest request) {
        Customer customer = customerService.signUp(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + customer.getId()).build().toUri();
        return ResponseEntity.created(uri).body(new SignupResponse(customer));
    }

    @DeleteMapping
    public ResponseEntity<Void> signOut() {
        //Customer customer = customerService.signUp(request);
        return ResponseEntity.noContent().build();
    }
}
