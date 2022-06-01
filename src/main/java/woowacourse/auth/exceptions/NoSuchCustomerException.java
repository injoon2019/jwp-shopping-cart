package woowacourse.auth.exceptions;

public class NoSuchCustomerException extends RuntimeException {

    public NoSuchCustomerException() {
    }

    public NoSuchCustomerException(String message) {
        super(message);
    }
}
