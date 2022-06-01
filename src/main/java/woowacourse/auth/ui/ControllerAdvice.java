package woowacourse.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.auth.exceptions.DuplicatedEmailException;
import woowacourse.auth.exceptions.IncorrectPasswordException;
import woowacourse.auth.exceptions.NoSuchCustomerException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class, NoSuchCustomerException.class, IncorrectPasswordException.class,
            DuplicatedEmailException.class})
    public ResponseEntity<Void> handleIllegalArgumentException(RuntimeException e) {
        return ResponseEntity.badRequest().build();
    }
}
