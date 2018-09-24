package chapter5.section3;

public class PCData {

    private final int inData;

    public PCData(int data) {
        this.inData = data;
    }


    public PCData(String data) {
        this.inData = Integer.valueOf(data);
    }

    public int getInData() {
        return inData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "inData=" + inData +
                '}';
    }
}
