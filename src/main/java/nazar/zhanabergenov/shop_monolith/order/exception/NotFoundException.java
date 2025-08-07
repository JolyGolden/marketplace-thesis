package nazar.zhanabergenov.shop_monolith.order.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
