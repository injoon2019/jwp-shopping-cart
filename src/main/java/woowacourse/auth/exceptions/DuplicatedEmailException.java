package woowacourse.auth.exceptions;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException() {
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
