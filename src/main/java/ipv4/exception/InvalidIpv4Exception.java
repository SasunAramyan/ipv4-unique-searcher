package ipv4.exception;

public class InvalidIpv4Exception extends RuntimeException {

    public InvalidIpv4Exception(String ipv4) {
        super(String.format("invalid ipv4 format : %s", ipv4));
    }
}
