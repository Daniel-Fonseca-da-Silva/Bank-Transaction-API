package dafon.tech.bank_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransferNotAuthorizedException extends BankException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("transfer not authorized.");
        pb.setDetail("Authorization service not authorized this transfer.");

        return pb;
    }
}
