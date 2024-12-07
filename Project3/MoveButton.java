import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MoveButton extends JButton implements ActionListener {
    private MoveCommand command;
    private UndoManager undoManager;
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;

    public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Move");
        this.undoManager = undoManager;
        view = jFrame;
        drawingPanel = jPanel;
        addActionListener(this);
        mouseHandler = new MouseHandler();
    }

    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.HAND_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        
    }

  private class MouseHandler extends MouseAdapter {
    private Point startingPt;
    public void mousePressed(MouseEvent event) {
      startingPt = event.getPoint();
    }
    public void mouseReleased(MouseEvent event) {
      int dx = (event.getX() - startingPt.x);
      int dy = (event.getY() - startingPt.y);
      command = new MoveCommand(dx, dy);
      undoManager.beginCommand(command);
      undoManager.endCommand(command);
      drawingPanel.removeMouseListener(this);
      view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }
}