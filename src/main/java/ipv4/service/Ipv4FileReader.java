package ipv4.service;

import ipv4.exception.InvalidIpv4Exception;
import ipv4.model.Ipv4Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ipv4FileReader {

    private static final int FIRST_PRIME_NUMBER = 257;
    private static final int SECOND_PRIME_NUMBER = 263;
    private static final int THIRD_PRIME_NUMBER = 269;
    private static final int FOURTH_PRIME_NUMBER = 271;
    private static final long MAX_IPV4_UNIQUE_COUNT = 4228250625L;

    public Long readFromFile(String fileName) throws IOException {
        File ipv4File = new File(fileName);
        ArrayList<Ipv4Model> uniqueFields = new ArrayList<>();
        Long count = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ipv4File))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<Integer> ips = extractIpv4(line);
                long uniqueNumberForIp = createUniqueNumberForIp(ips);
                count = putToRightList(uniqueNumberForIp, count, uniqueFields);
            }
        }
        return count;
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

    private Long putToRightList(Long uniqueNumberForIp, Long count, ArrayList<Ipv4Model> arrayList) {

        if (arrayList.parallelStream().noneMatch(c -> c.getIp().equals(uniqueNumberForIp))) {
            count++;
            Ipv4Model ipv4Model = new Ipv4Model();
            ipv4Model.setIp(uniqueNumberForIp);
            ipv4Model.setRepeatCount(1);
            arrayList.add(ipv4Model);
        } else {
            Ipv4Model first = arrayList.parallelStream().filter(c -> c.getIp().equals(uniqueNumberForIp)).findFirst().get();
            if (first.getRepeatCount() == 1) {
                count--;
            }
            first.setRepeatCount(first.getRepeatCount() + 1);

        }
        return count;

    }

}
