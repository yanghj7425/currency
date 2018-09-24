package chapter5.section5;

public class Launch {
    public static void main(String[] args) {
        Client client = new Client();

        Data data = client.request("hello");
        System.out.println("request returned and stand by data finished");

        System.out.println(data.getResult());


    }
}
