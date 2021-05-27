package unit;

import ipv4.exception.InvalidIpv4Exception;
import ipv4.service.Ipv4FileReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestForIpv4UniqueCounter {
    Ipv4FileReader ipv4FileReader = new Ipv4FileReader();

    private static final String IPV4_100 = "src/test/resources/ipv4-random-100.txt";
    private static final String IPV4_100000 = "src/test/resources/ipv4-random-10000.txt";
    private static final String IPV4_200_WITHOUT_UNIQUE = "src/test/resources/ipv4-random-200-without-unique.txt";
    private static final String INVALID_IPV4_FORMAT = "src/test/resources/invalid-ipv4-format.txt";

    @Test
    void testFor100Ips() throws IOException {

        Long aLong = ipv4FileReader.readFromFile(IPV4_100);

        Assertions.assertThat(aLong).isEqualTo(100);
    }


    @Test
    void testFor100000Ips() throws IOException {

        Long aLong = ipv4FileReader.readFromFile(IPV4_100000);

        Assertions.assertThat(aLong).isEqualTo(9771);
    }

    @Test
    void testFor200IpsWithoutUnique() throws IOException {

        Long aLong = ipv4FileReader.readFromFile(IPV4_200_WITHOUT_UNIQUE);

        Assertions.assertThat(aLong).isEqualTo(0);
    }

    @Test
    void testForInvalidIpv4Format() {
        InvalidIpv4Exception exception = assertThrows(InvalidIpv4Exception.class, () ->
                ipv4FileReader.readFromFile(INVALID_IPV4_FORMAT));
        String message = exception.getMessage();
        Assertions.assertThat(message).isEqualTo("invalid ipv4 format : 104.43.61");
    }
}
