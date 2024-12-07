import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TriangleButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager;
    private TriangleCommand triangleCommand;
    private MouseHandler mouseHandler;

    public TriangleButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Triangle");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); 
        drawingPanel.addMouseListener(mouseHandler); 
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point clickPoint = View.mapPoint(event.getPoint());

            if (triangleCommand == null) {
                triangleCommand = new TriangleCommand(clickPoint); 
                undoManager.beginCommand(triangleCommand);
            } else {
                triangleCommand.setNextPoint(clickPoint);
                if (triangleCommand.end()) {
                    undoManager.endCommand(triangleCommand);
                    triangleCommand = null;
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                }
            }
            view.refresh(); 
        }

        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isRightMouseButton(event)) {
                if (triangleCommand != null) { 
                    undoManager.cancelCommand();
                    triangleCommand = null;
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                    view.refresh();
                }
            }
        }
    }
}