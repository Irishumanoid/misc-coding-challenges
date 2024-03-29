package JavaGraphics;

public class Pixel {
    public final int xCoord;
    public final int yCoord;
    public final double intensity;

    public Pixel(int xCoord, int yCoord, double intensity) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "(" + xCoord + ", " + yCoord + ") " + " " + intensity;
    }
}

