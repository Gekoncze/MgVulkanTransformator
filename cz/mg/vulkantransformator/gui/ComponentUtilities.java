package cz.mg.vulkantransformator.gui;

import java.awt.*;


public class ComponentUtilities {
    public interface ComponentVisitedListener {
        public void onComponentVisited(Component component);
    }

    public static void visitAll(Container c, ComponentVisitedListener listener) {
        Component[] components = c.getComponents();
        for(Component component : components) {
            listener.onComponentVisited(component);
            if(component instanceof Container) {
                visitAll((Container) component, listener);
            }
        }
    }
}
