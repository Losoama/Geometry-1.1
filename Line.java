public class Line {
    private Point point0;
    private Point point1;

    public Line() {
        this.point0 = new Point(0, 0);
        this.point1 = new Point(0, 0);
    }

    public Line(int x1, int y1) {
        this.point0 = new Point(0, 0);
        this.point1 = new Point(x1, y1);
    }

    public Line(int x0, int y0, int x1, int y1) {
        this.point0 = new Point(x0, y0);
        this.point1 = new Point(x1, y1);
    }

    public void setX0(int x0) {
        this.point0.setX(x0);
    }

    public void setY0(int y0) {
        this.point0.setY(y0);
    }

    public void setX1(int x1) {
        this.point1.setX(x1);
    }

    public void setY1(int y1) {
        this.point1.setY(y1);
    }

    public void setXY0(int x0, int y0) {
        this.point0.setXY(x0, y0);
    }

    public void setXY1(int x1, int y1) {
        this.point1.setXY(x1, y1);
    }

    public void setXY(int x0, int y0, int x1, int y1) {
        this.point0.setXY(x0, y0);
        this.point1.setXY(x1, y1);
    }

    public Point getPoint0() {
        return this.point0;
    }

    public Point getPoint1() {
        return this.point1;
    }

    @Override
    public String toString() {
        return "(" + this.getPoint0().getX() + ", " + this.getPoint0().getY() + "); " +
                "(" + this.getPoint1().getX() + ", " + this.getPoint1().getY() + ")\n"+
                (new Vector(this));
    }
}
