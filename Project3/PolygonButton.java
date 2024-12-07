import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PolygonButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private PolygonCommand polygonCommand;
    private UndoManager undoManager;

    public PolygonButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Polygon");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        polygonCommand = new PolygonCommand();
        undoManager.beginCommand(polygonCommand);
    }

    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) { // Left click
                polygonCommand.addPoint(View.mapPoint(event.getPoint()));
                view.refresh();
            } else if (event.getButton() == MouseEvent.BUTTON3) { // Right click
                polygonCommand.closePolygon();
                undoManager.endCommand(polygonCommand);
                drawingPanel.removeMouseListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                view.refresh();
            }
        }
    }
}