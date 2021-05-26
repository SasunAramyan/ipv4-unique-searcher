package ipv4;

import ipv4.service.Ipv4FileReader;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Ipv4FileReader ipv4FileReader=new Ipv4FileReader();

        Long aLong = ipv4FileReader.readFromFile("src/main/resources/ipv4-random.txt");
        System.out.println(aLong);
    }
}
