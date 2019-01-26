package extension;

import com.sun.star.beans.XPropertySet;
import com.sun.star.ui.ActionTriggerSeparatorType;
import com.sun.star.ui.ContextMenuExecuteEvent;
import com.sun.star.ui.ContextMenuInterceptorAction;
import com.sun.star.ui.XContextMenuInterceptor;
import com.sun.star.uno.UnoRuntime;

public class ContextMenuInterceptor implements XContextMenuInterceptor{

	@Override
	public ContextMenuInterceptorAction notifyContextMenuExecute(ContextMenuExecuteEvent aEvent) {
		try {
            // Retrieve context menu container and query for service factory to
            // Create sub menus, menu entries and separators.
            com.sun.star.container.XIndexContainer xContextMenu = aEvent.ActionTriggerContainer;
            com.sun.star.lang.XMultiServiceFactory xMenuElementFactory =
                    UnoRuntime.queryInterface(com.sun.star.lang.XMultiServiceFactory.class, xContextMenu);

            if (xMenuElementFactory != null) {

                // Create root menu entry for sub menu and sub menu.
                com.sun.star.beans.XPropertySet xRootMenuEntry =
                        UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, 
                        		xMenuElementFactory.createInstance("com.sun.star.ui.ActionTrigger"));

                // Create a line separator for "Active Element" sub-menu.
                com.sun.star.beans.XPropertySet xSeparator =
                        UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, 
                        		xMenuElementFactory.createInstance("com.sun.star.ui.ActionTriggerSeparator"));
                Short aSeparatorType = Short.valueOf(ActionTriggerSeparatorType.LINE);
                xSeparator.setPropertyValue("SeparatorType", aSeparatorType);

                // Query sub menu for index container to get access.
                com.sun.star.container.XIndexContainer xSubMenuContainer =
                        UnoRuntime.queryInterface(com.sun.star.container.XIndexContainer.class, 
                        		xMenuElementFactory.createInstance("com.sun.star.ui.ActionTriggerContainer"));

                // Initialize root menu entry "Active Element".
                xRootMenuEntry.setPropertyValue("Text", "Active Element");
                xRootMenuEntry.setPropertyValue("CommandURL", "slot:5410");
                xRootMenuEntry.setPropertyValue("SubContainer", xSubMenuContainer);

                // Initialize "Properties" sub-menu.          
                XPropertySet xMenuEntry = UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, 
                		xMenuElementFactory.createInstance("com.sun.star.ui.ActionTrigger"));
                xMenuEntry.setPropertyValue("Text", "Properties");
                xMenuEntry.setPropertyValue("CommandURL", "service:org.libreoffice.modeler.Modeler?showProperties");
                xSubMenuContainer.insertByIndex(0, xMenuEntry);
                
                // Initialize "Enable" sub-menu.
                xMenuEntry = UnoRuntime.queryInterface(XPropertySet.class, 
                		xMenuElementFactory.createInstance("com.sun.star.ui.ActionTrigger"));
                xMenuEntry.setPropertyValue("Text", "Enable");
                xMenuEntry.setPropertyValue("CommandURL", "slot:5401");
                xSubMenuContainer.insertByIndex(1, xMenuEntry);
               
                // Initialize "Tips" sub-menu.
                xMenuEntry = UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, 
                		xMenuElementFactory.createInstance("com.sun.star.ui.ActionTrigger"));
                xMenuEntry.setPropertyValue("Text", "Tips");
                xMenuEntry.setPropertyValue("CommandURL", "slot:5404");
                xSubMenuContainer.insertByIndex(2, xMenuEntry);

                // Add separator into the given context menu.
                xContextMenu.insertByIndex(0, xSeparator);

                // Add new sub-menu into current context menu.
                xContextMenu.insertByIndex(0, xRootMenuEntry);

                // The controller should execute the modified context menu and stop notifying other
                // interceptors.
                return com.sun.star.ui.ContextMenuInterceptorAction.EXECUTE_MODIFIED;
            }
        }
        catch (com.sun.star.beans.UnknownPropertyException ex) {
            // Do nothing and skipped.
        }
        catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
        	// Do nothing and skipped.
        }
        catch (com.sun.star.uno.Exception ex) {
        	// Do nothing and skipped.
        }
        catch (java.lang.Throwable ex) {
        	// Do nothing and skipped.
        }

        return com.sun.star.ui.ContextMenuInterceptorAction.IGNORED;
    }
	
}
