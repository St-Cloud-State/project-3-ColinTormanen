import java.awt.*;

public class PolygonCommand extends Command {
    private Polygon polygon;

    public PolygonCommand() {
        polygon = new Polygon();
    }

    public void addPoint(Point point) {
        polygon.addPoint(point);
    }

    public void closePolygon() {
        polygon.closePolygon();
    }

    public void execute() {
        model.addItem(polygon);
    }

    public boolean undo() {
        model.removeItem(polygon);
        return true;
    }

    public boolean redo() {
        execute();
        return true;
    }

    public boolean end() {
        if (polygon.getPoints().size() < 3) {
            undo();
            return false;
        }
        return true;
    }
}