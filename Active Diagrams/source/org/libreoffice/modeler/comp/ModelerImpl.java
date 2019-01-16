package org.libreoffice.modeler.comp;

import com.sun.star.uno.Type;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.util.URL;
import com.sun.star.view.XSelectionSupplier;

import controller.Craftsman;
import controller.DocumentHandler;
import dialog.LvPropertyDialog;
import dialog.LvPropertyInfo;

import com.sun.star.lib.uno.helper.Factory;

import java.awt.HeadlessException;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.JOptionPane;

import com.sun.star.awt.MessageBoxType;
import com.sun.star.awt.Size;
import com.sun.star.awt.XActionListener;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;
import com.sun.star.container.XNamed;
import com.sun.star.drawing.ConnectorType;
import com.sun.star.drawing.RectanglePoint;
import com.sun.star.drawing.XDrawPageSupplier;
import com.sun.star.drawing.XShape;
import com.sun.star.drawing.XShapeDescriptor;
import com.sun.star.drawing.XShapes;
import com.sun.star.frame.DispatchDescriptor;
import com.sun.star.frame.XController;
import com.sun.star.frame.XDispatch;
import com.sun.star.frame.XDispatchProvider;
import com.sun.star.frame.XStatusListener;
import com.sun.star.lang.EventObject;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.NullPointerException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XEventListener;
import com.sun.star.lang.XInitialization;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextGraphicObjectsSupplier;
import com.sun.star.text.XTextShapesSupplier;
import com.sun.star.lib.uno.helper.WeakBase;


public final class ModelerImpl extends WeakBase
   implements com.sun.star.lang.XServiceInfo,
   			com.sun.star.task.XJobExecutor, 
   			XInitialization, XDispatchProvider, XDispatch
              // org.libreoffice.modeler.XModeler
{
    private final XComponentContext m_xContext;
    private static final String m_implementationName = ModelerImpl.class.getName();
    private static final String[] m_serviceNames = {
        "org.libreoffice.modeler.Modeler" };
    
    private DocumentHandler m_docHandler;
    
    private boolean m_On;
    private Timer m_timer;
    private TimerTask m_timerTask;
    private int m_timerShow;

    public ModelerImpl( XComponentContext context )
    {
        m_xContext = context;
        initialize();
        m_timer = new Timer("FlickerTimer");
        
        m_docHandler.subscribeContextMenu();
    };

    public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
        XSingleComponentFactory xFactory = null;

        if ( sImplementationName.equals( m_implementationName ) )
            xFactory = Factory.createComponentFactory(ModelerImpl.class, m_serviceNames);
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo( XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                                                m_serviceNames,
                                                xRegistryKey);
    }

    // com.sun.star.lang.XServiceInfo:
    public String getImplementationName() {
         return m_implementationName;
    }

    public boolean supportsService( String sService ) {
        int len = m_serviceNames.length;

        for( int i=0; i < len; i++) {
            if (sService.equals(m_serviceNames[i]))
                return true;
        }
        return false;
    }

    public String[] getSupportedServiceNames() {
        return m_serviceNames;
    }

	@Override
	public void trigger(String arg0) {
		
		switch (arg0) {
    	case "actionOne":
    		execute();
    		System.out.println("actionOne Executed.");
    		break;
    		
    	case "extractShape":
    		extractShape();
    		System.out.println("extractShape Executed.");
    		break;
    		
    	case "connectShapes":
    		connectShapes();
    		System.out.println("connectShapes Executed.");
    		break;
    		
    	case "startFlicker":
    		startFlicker();
    		System.out.println("startFlicker Executed.");
    		break;
    		
    	case "stopFlicker":
    		stopFlicker();
    		System.out.println("stopFlicker Executed.");
    		break;
    	
    	default:
    		System.out.println("default Executed.");
    		break;
		}		
	}
	
	private void initialize() {
		try {
			m_docHandler = new DocumentHandler(m_xContext);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void execute() {
		try {
			m_docHandler.initializeXComponent();
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			XText xText = m_docHandler.getXText(xTextDocument);
			JOptionPane.showMessageDialog(null, xText.getString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void extractShape() {
		try {
			m_docHandler.initializeXComponent();
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			ArrayList<XShape> xShapes = m_docHandler.extractAllShapes(xTextDocument);
			for (XShape xShape : xShapes) {
				XPropertySet property = m_docHandler.getXPropertySet(xShape);
				if (m_On) {
					property.setPropertyValue("FillColor", new Integer(0x00FF00));
				}else {
					property.setPropertyValue("FillColor", new Integer(0xFF0000));
				}
				String shapeName = (String)property.getPropertyValue("Name");
				System.out.println("Name [" + shapeName + "]");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void connectShapes() {
		try {
			m_docHandler.initializeXComponent();
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			XMultiServiceFactory multiServiceFactory = m_docHandler.initialieXMultiServiceFactory(xTextDocument);
			//Page 1051
			
			Craftsman craftsman = new Craftsman();
			craftsman.assignTo(multiServiceFactory);
			XShape xConnector = craftsman.createShape(0, 0, 0, 0, "com.sun.star.drawing.ConnectorShape");
			
			XShapes xShapes = m_docHandler.getXShapes(xTextDocument);
			if (xShapes == null) {
				JOptionPane.showMessageDialog(null, "No shapes selected.", "Error", MessageBoxType.ERRORBOX_value);
				return;
			}
			ArrayList<XShape> selectedXShapes = m_docHandler.extractSelectedShapes(xTextDocument);
			if (selectedXShapes == null) {
				JOptionPane.showMessageDialog(null, "Selected shape count < 2.", "Error", MessageBoxType.ERRORBOX_value);
				return;
			}
			
			xShapes.add(xConnector);
			
			XPropertySet xConnectorPropertySet = m_docHandler.getXPropertySet(xConnector);
			xConnectorPropertySet.setPropertyValue("StartShape", selectedXShapes.get(0));
			xConnectorPropertySet.setPropertyValue("StartGluePointIndex", new Integer(2));
			xConnectorPropertySet.setPropertyValue("EndShape", selectedXShapes.get(1));
			xConnectorPropertySet.setPropertyValue("EndGluePointIndex", new Integer(0));
			xConnectorPropertySet.setPropertyValue("EdgeKind", ConnectorType.STANDARD);
			xConnectorPropertySet.setPropertyValue("LineEndCenter", false);
			xConnectorPropertySet.setPropertyValue("LineEndName", "Arrow"); 
			
			JOptionPane.showMessageDialog(null, "Completed", "Passed", MessageBoxType.INFOBOX_value);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", MessageBoxType.ERRORBOX_value);
		}
	}
	
	private void doTask() {
		try {
			m_docHandler.initializeXComponent();
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			ArrayList<XShape> xShapes = m_docHandler.extractAllShapes(xTextDocument);
			for (int idx=0; idx<xShapes.size(); idx++) {
				XShape xShape = xShapes.get(idx);
				XPropertySet property = m_docHandler.getXPropertySet(xShape);
				
				m_timerTask = new TimerTask() {
					
					@Override
					public void run() {
						m_On = !m_On;
						try {
							if (m_On) {
								property.setPropertyValue("FillColor", new Integer(0x00FF00));
							}else {
								property.setPropertyValue("FillColor", new Integer(0xFF0000));
							}
							for(int x=0; x<1000; x++) {
								System.out.println("Doing Task" + x);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// Check for last object here.
							cancel();
							m_timer.cancel();
						
					}
				};
				m_timer.schedule(m_timerTask, 0);
				
				String shapeName = (String)property.getPropertyValue("Name");
				System.out.println("Name [" + shapeName + "]");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void startFlicker() {
		/*m_timerTask = new TimerTask() {
			
			@Override
			public void run() {
				m_On = !m_On;
				extractShape();
				m_timerShow += 1;
				if (m_timerShow >=6) {
					cancel();
					m_timer.cancel();
				}
			}
		};
		m_timer.schedule(m_timerTask, 0, 1000);*/
		
		doWalk();
		/*LvPropertyDialog prop = new LvPropertyDialog();
		LvPropertyInfo propertyInfo = prop.showModal();
		JOptionPane.showMessageDialog(null, propertyInfo.getName());*/
	}
	
	private void doWalk() {
		try {
			m_docHandler.initializeXComponent();
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			ArrayList<XShape> xShapes = m_docHandler.extractAllShapes(xTextDocument);
			for (int idx=0; idx<xShapes.size(); idx++) {
				XShape xShape = xShapes.get(idx);
				XPropertySet property = m_docHandler.getXPropertySet(xShape);
				
				String shapeName = (String)property.getPropertyValue("Name");
				System.out.println("Name [" + shapeName + "]");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void stopFlicker() {
		//JOptionPane.showMessageDialog(null, "stopFlicker executed");
	}

	@Override
	public void initialize(Object[] arg0) throws com.sun.star.uno.Exception {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "initialize");
	}

	@Override
	public void addStatusListener(XStatusListener arg0, URL arg1) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "addStatusListener");
	}

	@Override
	public void dispatch(URL arg0, PropertyValue[] arg1) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "dispatch");
	}

	@Override
	public void removeStatusListener(XStatusListener arg0, URL arg1) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "removeStatusListener");
	}

	@Override
	public XDispatch queryDispatch(URL arg0, String arg1, int arg2) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "queryDispatch");
		return null;
	}

	@Override
	public XDispatch[] queryDispatches(DispatchDescriptor[] arg0) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "queryDispatches");
		return null;
	}

}
