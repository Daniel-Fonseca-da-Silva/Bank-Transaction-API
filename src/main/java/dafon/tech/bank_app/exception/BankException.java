package dafon.tech.bank_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class BankException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Bank internal server error");
        return pb;
    }

}
