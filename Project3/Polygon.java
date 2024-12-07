import java.awt.*;
import java.util.ArrayList;

public class Polygon extends Item {
    private ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public boolean isClosed() {
        return points.size() > 2 && points.get(0).equals(points.get(points.size() - 1));
    }

    public void closePolygon() {
        if (!isClosed() && points.size() > 2) {
            points.add(points.get(0)); // Connect the last point to the first
        }
    }

    public boolean includes(Point point) {
        for (Point p : points) {
            if (distance(point, p) < 10.0) {
                return true;
            }
        }
        return false;
    }

    public void render(UIContext uiContext) {
        if (points.size() > 1) {
            for (int i = 0; i < points.size() - 1; i++) {
                uiContext.drawLine(points.get(i), points.get(i + 1));
            }
        }
    }

    public void translate(int dx, int dy) {
        for (int i = 0; i < points.size() - 1; i++) {
            points.get(i).translate(dx, dy);
        }
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}