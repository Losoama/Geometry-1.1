public class Vector {
    private Line line;
    private Point point0;
    private Point point1;
    private int x;
    private int y;
    private double size;

    public Vector() {
        this(new Line());
    }

    public Vector(Line line) {
        this.line = line;
        this.point0 = line.getPoint0();
        this.point1 = line.getPoint1();
        this.x = this.point1.getX() - this.point0.getX();
        this.y = this.point1.getY() - this.point0.getY();
        this.size = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public static Vector addVectors(Vector v1, Vector v2)
            throws IllegalArgumentException {
        return new Vector(new Line());
    }

    @Override
    public String toString() {
        return "Координата вектора: (" + this.x + ", " + this.y + ")\n" +
                "Длина вектора: " + this.size + "\n";
    }
}
