package ec.edu.espe.petshopinventorycontrol.utils;

import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public final class ScrollUtils {

    private ScrollUtils() {
    }

    public static void applyScrollBars(JFrame frame) {
        if (frame == null) {
            return;
        }
        Container content = frame.getContentPane();
        if (!(content instanceof JComponent)) {
            return;
        }

        JComponent contentComponent = (JComponent) content;
        JScrollPane scroll = new JScrollPane(
                contentComponent,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getHorizontalScrollBar().setUnitIncrement(16);
        frame.setContentPane(scroll);
    }
}
