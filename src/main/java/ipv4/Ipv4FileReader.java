package ipv4;

import ipv4.exception.InvalidIpv4Exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Ipv4FileReader {

    private static final int FIRST_PRIME_NUMBER = 257;
    private static final int SECOND_PRIME_NUMBER = 263;
    private static final int THIRD_PRIME_NUMBER = 269;
    private static final int FOURTH_PRIME_NUMBER = 271;
    private static final long MAX_IPV4_UNIQUE_COUNT = 4228250625L;
    private static final int MAX_IPV4_UNIQUE_COUNT_FIRST_PART_IN_INT = 2114125312;
    private static final int MAX_IPV4_UNIQUE_COUNT_SECOND_PART_IN_INT = 2114125313;


    public Long readFromFile(String fileName) throws IOException {
        File ipv4File = new File(fileName);
        int[] firstPartUniqueIps = new int[MAX_IPV4_UNIQUE_COUNT_FIRST_PART_IN_INT];
        int[] secondPartUniqueIps = new int[MAX_IPV4_UNIQUE_COUNT_SECOND_PART_IN_INT];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ipv4File))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<Integer> ips = extractIpv4(line);
                long uniqueNumberForIp = createUniqueNumberForIp(ips);
                putToRightArray(firstPartUniqueIps, secondPartUniqueIps, uniqueNumberForIp);
            }
        }
        return countAllUniques(firstPartUniqueIps, secondPartUniqueIps);
    }

    private Long countAllUniques(int[] firstPartUniqueIps, int[] secondPartUniqueIps) {
        long allUniqueIpsCount = 0;
        for (int current : firstPartUniqueIps) {
            if (current == 1) {
                allUniqueIpsCount++;
            }
        }
        for (int current : secondPartUniqueIps) {
            if (current == 1) {
                allUniqueIpsCount++;
            }
        }
        return allUniqueIpsCount;
    }

    private List<Integer> extractIpv4(String ipv4Str) {
        List<String> split = List.of(ipv4Str.split("\\."));
        if (split.size() != 4) {
            throw new InvalidIpv4Exception(ipv4Str);
        }
        return convertToIntegerList(split);
    }

    private List<Integer> convertToIntegerList(List<String> splicedIpv4) {
        return splicedIpv4.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    //Cant use Math.pow() because double size dont contain 255^4
    private long createUniqueNumberForIp(List<Integer> ips) {
        return (long) ips.get(0) * FIRST_PRIME_NUMBER * ips.get(1)
                * SECOND_PRIME_NUMBER * ips.get(2)
                * THIRD_PRIME_NUMBER * ips.get(3)
                * FOURTH_PRIME_NUMBER % MAX_IPV4_UNIQUE_COUNT;
    }

    private void putToRightArray(int[] firstArray, int[] secondArray, long value) {
        if (value < MAX_IPV4_UNIQUE_COUNT_SECOND_PART_IN_INT) {
            firstArray[Math.toIntExact(value)]++;
        } else {
            int positionForSecondArray = Math.toIntExact(value - MAX_IPV4_UNIQUE_COUNT_SECOND_PART_IN_INT);
            secondArray[Math.toIntExact(positionForSecondArray)]++;
        }

    }

}
