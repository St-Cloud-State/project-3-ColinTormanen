import java.awt.*;

public class TriangleCommand extends Command {
    private Triangle triangle; 
    private int pointCount = 0; 

    public TriangleCommand(Point startPoint) {
        triangle = new Triangle(startPoint, startPoint, null);
        model.addItem(triangle); 
        model.setChanged();
        pointCount = 1;
    }

    public void setNextPoint(Point nextPoint) {
        if (pointCount == 1) {
            triangle.setPoint2(nextPoint);
            model.setChanged();
            pointCount++;
        } else if (pointCount == 2) {
            triangle.setPoint3(nextPoint);
            model.setChanged(); 
            pointCount++;
        }
    }

    @Override
    public void execute() {
        if (triangle.isComplete()) {
            model.addItem(triangle);
            model.setChanged(); 
        }
    }

    @Override
    public boolean undo() {
        model.removeItem(triangle);
        model.setChanged();
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        return pointCount == 3;
    }

}