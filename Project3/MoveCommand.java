import java.awt.*;
import java.util.*;
public class MoveCommand extends Command {
    private int dx;
    private int dy;
    public MoveCommand(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public boolean undo() {
        Enumeration enumeration = model.getSelectedItems();
        while (enumeration.hasMoreElements()) {
            Item item = (Item)(enumeration.nextElement());
            item.translate(-dx, -dy);
        }
        model.setChanged();
        return true;
    }

    public boolean redo() {
        execute();
        return true;
    }

    public void execute() {
        Enumeration enumeration = model.getSelectedItems();
        while (enumeration.hasMoreElements()) {
            Item item = (Item)(enumeration.nextElement());
            item.translate(dx, dy);
        }
        model.setChanged();
    }
    
}