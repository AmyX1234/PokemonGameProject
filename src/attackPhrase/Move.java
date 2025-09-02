package attackPhrase;

public class Move {
    private String name;
    private int power;
    private String type;
    private double accuracy;


    public Move(String name, int power, String type, double accuracy) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.accuracy = accuracy;
    }


    public String getName() {
        return name;
    }


    public int getPower() {
        return power;
    }


    public String getType() {
        return type;
    }


    public double getAccuracy() {
        return accuracy;
    }
}
