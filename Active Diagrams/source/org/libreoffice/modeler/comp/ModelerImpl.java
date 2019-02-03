package org.libreoffice.modeler.comp;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.sun.star.awt.MessageBoxType;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.drawing.ConnectorType;
import com.sun.star.drawing.XShape;
import com.sun.star.drawing.XShapes;
import com.sun.star.frame.DispatchDescriptor;
import com.sun.star.frame.XController;
import com.sun.star.frame.XDispatch;
import com.sun.star.frame.XDispatchProvider;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStatusListener;
import com.sun.star.lang.NullPointerException;
import com.sun.star.lang.XInitialization;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.lib.uno.helper.Factory;
import com.sun.star.lib.uno.helper.WeakBase;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.task.XJobExecutor;
import com.sun.star.task.XJobListener;
import com.sun.star.text.XText;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.URL;
import com.sun.star.util.XJobManager;
import com.sun.star.util.XURLTransformer;

import controller.Craftsman;
import controller.DocumentHandler;
import controller.ServiceLocator;
import dialog.LvPropertyDialog;
import dialog.LvPropertyDialogController;
import dialog.LvPropertyDialogData;
import utilities.TimeUtil;


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
    private volatile boolean m_running = false;
    private volatile int m_timerShow = 0;

    public ModelerImpl( XComponentContext context )
    {
    	System.out.println("Active-Diagrams trying to load.");
    	
        m_xContext = context;
        initialize();
        m_timer = new Timer("FlickerTimer");
        
        // TODO Move this invocation to Enable Menu handler.
        //		This action should not auto-activated.
        //		This action should be purposefully enabled to work.
        m_docHandler.subscribeContextMenu();
        
        // TODO Calculate and log loading time.
        System.out.println("Active-Diagrams successfully loaded. [" + TimeUtil.now() + "]");
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
    		
    	case "showProperties":
    		showProperties();
    		System.out.println("showProperties Executed.");
    		break;
    		
    	case "processProperties":
    		processProperties();
    		System.out.println("processProperties Executed.");
    		break;
    	
    	default:
    		System.out.println("default Executed.");
    		break;
		}		
	}
	
	private void initialize() {
		try {
			m_docHandler = new DocumentHandler(m_xContext);
			m_docHandler.initializeXComponent();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	// TODO Move code as required.
	private void execute() {
		try {
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			XText xText = m_docHandler.getXText(xTextDocument);
			JOptionPane.showMessageDialog(null, xText.getString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// TODO Move code as required.
	private void extractShape() {
		try {
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
	
	// TODO Move code as required.
	private void connectShapes() {
		try {
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
	
	// TODO Remove code if not required.
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
	
	// TODO Move code as required.
	private void startFlicker() {
		
		m_running = true;
		
		//Iterate over all scheduled tasks.
		for(int idx=0; idx<20; idx++) {
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					m_timerShow++;
					if (!m_running || m_timerShow >= 10) {
						System.out.println("Cancelled scheduled job at [" + m_timerShow + "]");
						return;
					}
					
					m_On = !m_On;
					extractShape();
					
					// Execute one task.
					for(int tdx=0; tdx<100000; tdx++) {
						System.out.println("Job [" + m_timerShow + "] - Task [" + tdx + "]");
					}
				}
			});
			
			System.out.println("[" + idx + "] Jobs pushed.");
		}
		
		/*m_timerTask = new TimerTask() {
			
			@Override
			public void run() {
				m_On = !m_On;
				extractShape();
				//m_timerShow += 1;
				if (!m_running) {
					cancel();
					m_timer.cancel();
				}
			}
		};
		m_timer.schedule(m_timerTask, 0, 1000);*/
		
		doWalk();
	}
	
	// TODO Remove code if not required.
	public void doJob() {
		m_On = !m_On;
		extractShape();
	}
	
	// TODO Move code as required.
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
	
	// TODO Move code as required.
	private void stopFlicker() {
		// TODO Implement abort mechanism.
	}
	
	// TODO Move code as required.
	private void showProperties() {
		
		String shapeName = "";
		String shapeText = "";

		try {
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			ArrayList<XShape> selectedShapes =  m_docHandler.extractSelectedShapes(xTextDocument);
			
			if (selectedShapes.size() > 0) {
				XShape xShape = selectedShapes.get(0);
				XPropertySet property = m_docHandler.getXPropertySet(xShape);
				
				shapeName = (String)property.getPropertyValue("Name");
				XText xShapeText = UnoRuntime.queryInterface(XText.class, xShape);
				shapeText = xShapeText.getString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("No items selected.");
		}
		System.out.println("Property: Name [" + shapeName + "]");
		System.out.println("Property: Text [" + shapeText + "]");
		// TODO Pass this value to controller.
		// TODO Organize usage.
		XDispatch propertyDispatch = m_docHandler.getXDispatcher("service:org.libreoffice.modeler.Modeler?actionOne");
		
		LvPropertyDialog propertyDialog = (LvPropertyDialog)ServiceLocator.getService("LvPropertyDialog");
		if (propertyDialog == null) {
			propertyDialog = new LvPropertyDialog();
			propertyDialog.setWidth(400);
			propertyDialog.setHeight(500);
			ServiceLocator.addService(propertyDialog);
		}
		LvPropertyDialogController dialogController = (LvPropertyDialogController)propertyDialog.getController();
		dialogController.setXDispatcher(propertyDispatch);
		LvPropertyDialogData dialogData = dialogController.getData();
		// Assuming properties list is fixed and Name is the first entry in property bag.
		dialogData.getProperties().get(0).setValue(shapeName);
		propertyDialog.show();
	}

	private void processProperties() {
		
		LvPropertyDialog propertyDialog = (LvPropertyDialog)ServiceLocator.getService("LvPropertyDialog");
		if (propertyDialog == null) {
			JOptionPane.showMessageDialog(null, "Unable to retrieve LvPropertyDialog");
			return;
		}
		
		LvPropertyDialogController dialogController = (LvPropertyDialogController)propertyDialog.getController();
		LvPropertyDialogData dialogData = dialogController.getData();
		
		try {
			XTextDocument xTextDocument = m_docHandler.getXTextDocument();
			ArrayList<XShape> selectedXShapes = m_docHandler.extractSelectedShapes(xTextDocument);
			if (selectedXShapes == null) {
				JOptionPane.showMessageDialog(null, "Select one shape.", "Error", 
																	MessageBoxType.ERRORBOX_value);
				return;
			}
			
			XPropertySet xShapeProps = (XPropertySet)UnoRuntime.queryInterface(
											XPropertySet.class, selectedXShapes.get(0));
			// Assuming properties list is fixed and Name is the first entry in property bag.
			System.out.println("Retrieve " + dialogData.getProperties().get(0).getValue());
			xShapeProps.setPropertyValue("Name", dialogData.getProperties().get(0).getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
