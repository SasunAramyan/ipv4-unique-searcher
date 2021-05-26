package ipv4;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Ipv4FileReader ipv4FileReader=new Ipv4FileReader();

        Long aLong = ipv4FileReader.readFromFile("src/main/resources/ipv4-random.txt");
        System.out.println(aLong);
    }
}
