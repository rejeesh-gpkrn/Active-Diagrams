package controller;

import java.rmi.activation.ActivationException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.sun.star.awt.MessageBoxType;
import com.sun.star.awt.Size;
import com.sun.star.beans.XPropertySet;
import com.sun.star.drawing.XDrawPageSupplier;
import com.sun.star.drawing.XShape;
import com.sun.star.drawing.XShapes;
import com.sun.star.frame.FrameSearchFlag;
import com.sun.star.frame.XController;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XDispatch;
import com.sun.star.frame.XDispatchProvider;
import com.sun.star.frame.XFrame;
import com.sun.star.frame.XModel;
import com.sun.star.lang.EventObject;
import com.sun.star.lang.IndexOutOfBoundsException;
import com.sun.star.lang.NullPointerException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XEventListener;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.URL;
import com.sun.star.util.XURLTransformer;
import com.sun.star.view.XSelectionSupplier;

import contract.Constants.ServiceName;
import contract.Service;
import extension.ContetMenuClickHandler;
import extension.ContextMenuInterceptor;

public class LOInterfaceService implements Service {
	
	private XComponentContext m_xContext;
	private XMultiComponentFactory m_xMCF;
	private XDesktop m_xDesktop;
	private XComponent m_xComp;
	private boolean m_initialized;
	
	public LOInterfaceService(XComponentContext context) throws NullPointerException {
		if (context == null) {
			throw new NullPointerException();
		}
		
		m_xContext = context;
		m_initialized = false;
	}
	
	public void initializeXComponent() {
		try {
			m_xMCF = UnoRuntime.queryInterface(XMultiComponentFactory.class, m_xContext.getServiceManager());
			m_xDesktop = UnoRuntime.queryInterface(com.sun.star.frame.XDesktop.class,
					m_xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", m_xContext));
			m_xComp = m_xDesktop.getCurrentComponent();
			m_initialized = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Exception [initializeXComponent]", MessageBoxType.ERRORBOX_value);
		}
	}
	
	public XTextDocument getXTextDocument() throws ActivationException {
		if (m_initialized == false) {
			throw new ActivationException("Invoke initializeXComponent required.");
		}
		
		XTextDocument xTextDoc = UnoRuntime.queryInterface(com.sun.star.text.XTextDocument.class, m_xComp);
		return xTextDoc;
	}
	
	public XText getXText(XTextDocument textDocument) throws NullPointerException {
		if (textDocument == null) {
			throw new NullPointerException();
		}
		
		XText xText = textDocument.getText();
		return xText;
	}
	
	/**
	 * @return text content of the document as String.
	 * @throws Exception
	 */
	public String getDocumentText() throws Exception {
		XTextDocument xTextDocument = getXTextDocument();
		XText xText = getXText(xTextDocument);
		String documentText = xText.getString();
		return documentText;
	}
	
	public XTextCursor getXTextCursor(XText text) throws NullPointerException {
		if (text == null) {
			throw new NullPointerException();
		}
		
		XTextCursor xTextCursor = text.createTextCursor();
		return xTextCursor;
	}
	
	public XMultiServiceFactory initialieXMultiServiceFactory(XTextDocument textDocument) throws NullPointerException {
		if (textDocument == null) {
			throw new NullPointerException();
		}
		
		XMultiServiceFactory xMSFDoc = UnoRuntime.queryInterface(XMultiServiceFactory.class, textDocument);
		return xMSFDoc;
	}
	
	public XShapes getXShapes(XTextDocument textDocument) throws NullPointerException{
		if (textDocument == null) {
			throw new NullPointerException();
		}
		
		XDrawPageSupplier drawPageSupplier = UnoRuntime.queryInterface(XDrawPageSupplier.class, textDocument);
		if (drawPageSupplier == null) {
			return null;
		}
		
		XShapes xShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, drawPageSupplier.getDrawPage());
		return xShapes;
	}
	
	public ArrayList<XShape> extractAllShapes(XTextDocument textDocument) throws NullPointerException{
		if (textDocument == null) {
			throw new NullPointerException();
		}
		
		ArrayList<XShape> xShapesList = new ArrayList<XShape>();
		
		XDrawPageSupplier drawPageSupplier = UnoRuntime.queryInterface(XDrawPageSupplier.class, textDocument);
		if (drawPageSupplier == null) {
			return xShapesList;
		}
		
		XShapes xShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, drawPageSupplier.getDrawPage());
		if (xShapes == null) {
			return xShapesList;
		}
		
		for(int next = 0; next < xShapes.getCount(); next ++) {
			Object objShape;
			try {
				objShape = xShapes.getByIndex(next);
			} catch (IndexOutOfBoundsException e) {
				return xShapesList;
			} catch (WrappedTargetException e) {
				return xShapesList;
			}
			
			XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class, objShape);
			xShapesList.add(xShape);
		}
		
		return xShapesList;
	}
	
	public XShapes getSelectedXShapes(XTextDocument textDocument) throws NullPointerException{
		if (textDocument == null) {
			throw new NullPointerException();
		}
		
		XSelectionSupplier xSelectionSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, textDocument.getCurrentController());
		if (xSelectionSupplier == null) {
			return null;
		}
		
		XShapes xShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, xSelectionSupplier.getSelection());
		
		return xShapes;
	}
	
	public ArrayList<XShape> extractSelectedShapes(XShapes xShapes) throws NullPointerException{
		if (xShapes == null) {
			throw new NullPointerException();
		}
		
		ArrayList<XShape> xShapesList = new ArrayList<XShape>();
		
		for(int next = 0; next < xShapes.getCount(); next ++) {
			Object objShape;
			try {
				objShape = xShapes.getByIndex(next);
			} catch (IndexOutOfBoundsException e) {
				return xShapesList;
			} catch (WrappedTargetException e) {
				return xShapesList;
			}
			
			XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class, objShape);
			xShapesList.add(xShape);
		}
		
		return xShapesList;
	}
	
	public ArrayList<XShape> extractSelectedShapes(XSelectionSupplier xSelectionSupplier) throws NullPointerException{
		if (xSelectionSupplier == null) {
			throw new NullPointerException();
		}
		
		XShapes xShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, xSelectionSupplier.getSelection());
		
		ArrayList<XShape> xShapesList = extractSelectedShapes(xShapes);
		
		return xShapesList;
	}
	
	public ArrayList<XShape> extractSelectedShapes(XTextDocument textDocument) throws NullPointerException{
		if (textDocument == null) {
			throw new NullPointerException();
		}
		
		ArrayList<XShape> xShapesList = new ArrayList<XShape>();
		
		XSelectionSupplier xSelectionSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, textDocument.getCurrentController());
		if (xSelectionSupplier == null) {
			return xShapesList;
		}
		
		XShapes xShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, xSelectionSupplier.getSelection());
		if (xShapes == null) {
			return xShapesList;
		}
		
		for(int next = 0; next < xShapes.getCount(); next ++) {
			Object objShape;
			try {
				objShape = xShapes.getByIndex(next);
			} catch (IndexOutOfBoundsException e) {
				return xShapesList;
			} catch (WrappedTargetException e) {
				return xShapesList;
			}
			
			XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class, objShape);
			xShapesList.add(xShape);
		}
		
		return xShapesList;
	}
	
	public XPropertySet getXPropertySet(XShape shape) throws NullPointerException {
		if (shape == null) {
			throw new NullPointerException();
		}
		
		XPropertySet xShapePropertySet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, shape);
		return xShapePropertySet;
	}
	
	public void subscribeContextMenu() {
		try {
			initializeXComponent();
			XModel xModel = (XModel)UnoRuntime.queryInterface(XModel.class, m_xComp);
			
			XController xController = xModel.getCurrentController();
			if (xController != null) {
				com.sun.star.ui.XContextMenuInterception xContextMenuInterception =
				(com.sun.star.ui.XContextMenuInterception) UnoRuntime.queryInterface(
				com.sun.star.ui.XContextMenuInterception.class, xController);

				if (xContextMenuInterception != null) {
					/*com.sun.star.ui.XContextMenuInterceptor xContextMenuInterceptor =
					(com.sun.star.ui.XContextMenuInterceptor) UnoRuntime.queryInterface(
					com.sun.star.ui.XContextMenuInterceptor.class, this);
					xContextMenuInterception.registerContextMenuInterceptor(xContextMenuInterceptor);*/
					ContextMenuInterceptor xContextMenuInterceptor = new ContextMenuInterceptor();
//					ContextMenuInterceptor xContextMenuInterceptor = 
//							(ContextMenuInterceptor) UnoRuntime.queryInterface(
//							ContextMenuInterceptor.class, 
//							m_xMCF.createInstanceWithContext("extension.ContextMenuInterceptor", m_xContext));
							xContextMenuInterception.registerContextMenuInterceptor(xContextMenuInterceptor);
							
				}
				
				// Register mouse handler.
				/*com.sun.star.awt.XUserInputInterception xUserInputInterception =
				(com.sun.star.awt.XUserInputInterception) UnoRuntime.queryInterface(
				com.sun.star.awt.XUserInputInterception.class, xController);
				if (xUserInputInterception != null) {
					xUserInputInterception.addMouseClickHandler(new ContetMenuClickHandler());
				}*/
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public XFrame getXFrame() {
		return m_xDesktop.getCurrentFrame();
	}
	
	public XDispatch getXDispatcher(String sURL) {
		try {
			URL[] aParseURL = new URL[1];
			aParseURL[0] = new URL();
			aParseURL[0].Complete = sURL;
			
			Object oTransformer = m_xMCF.createInstanceWithContext("com.sun.star.util.URLTransformer", m_xContext);
			XURLTransformer xTransformer = UnoRuntime.queryInterface(XURLTransformer.class, oTransformer);
			xTransformer.parseStrict(aParseURL);
			
			XDispatchProvider xDispatchProvider = UnoRuntime.queryInterface(
											XDispatchProvider.class, getXFrame());
			
			XDispatch xDispatch = xDispatchProvider.queryDispatch(aParseURL[0], "", 0);
	        return xDispatch;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void execute() {
		try {
			com.sun.star.lang.XMultiComponentFactory xMCF = UnoRuntime.queryInterface(XMultiComponentFactory.class,m_xContext.getServiceManager());
			com.sun.star.frame.XDesktop xDesktop = UnoRuntime.queryInterface(com.sun.star.frame.XDesktop.class,
					xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", m_xContext));
			/*com.sun.star.frame.XComponentLoader xCompLoader =
	                UnoRuntime.queryInterface(
	                com.sun.star.frame.XComponentLoader.class, xDesktop);*/
			/*com.sun.star.lang.XComponent xComp = xCompLoader.loadComponentFromURL(
	                "private:factory/swriter", "_blank", 0,
	                new com.sun.star.beans.PropertyValue[0]);*/
			com.sun.star.lang.XComponent xComp = xDesktop.getCurrentComponent();
			com.sun.star.text.XTextDocument xTextDoc =
	                UnoRuntime.queryInterface(
	                com.sun.star.text.XTextDocument.class, xComp);
			/*com.sun.star.lang.XMultiServiceFactory xMSFDoc =
	                UnoRuntime.queryInterface(
	                com.sun.star.lang.XMultiServiceFactory.class, xTextDoc);*/
			/*com.sun.star.text.XText xText = xTextDoc.getText();
			JOptionPane.showMessageDialog(null, xText.getString());*/
			
			/*com.sun.star.lang.XMultiServiceFactory xMSFDoc =
	                UnoRuntime.queryInterface(
	                com.sun.star.lang.XMultiServiceFactory.class, xTextDoc);
			com.sun.star.text.XTextCursor xTextCursor = xText.createTextCursor();*/
			
			
			//Object oGraphic = xMSFDoc.createInstance("com.sun.star.text.TextGraphicObject");
			//com.sun.star.text.XTextContent xTextContent =
	        //        UnoRuntime.queryInterface(
	        //        com.sun.star.text.XTextContent.class, oGraphic );
			//xText.insertTextContent(xTextCursor, xTextContent, true);
			
//			com.sun.star.beans.XPropertySet xPropSet =
//	                UnoRuntime.queryInterface(
//	                com.sun.star.beans.XPropertySet.class, oGraphic);
//			com.sun.star.graphic.XGraphicProvider xGraphicProvider =
//                    UnoRuntime.queryInterface(com.sun.star.graphic.XGraphicProvider.class,
//                        xMCF.createInstanceWithContext("com.sun.star.graphic.GraphicProvider",
//                        m_xContext));
//			com.sun.star.beans.PropertyValue[] aMediaProps = new com.sun.star.beans.PropertyValue[] { new com.sun.star.beans.PropertyValue() };
//            aMediaProps[0].Name = "URL";
//            aMediaProps[0].Value = "sUrl";
//
//            com.sun.star.graphic.XGraphic xGraphic =
//                UnoRuntime.queryInterface(com.sun.star.graphic.XGraphic.class,
//                            xGraphicProvider.queryGraphic(aMediaProps));
//			xPropSet.setPropertyValue("AnchorType",
//                    com.sun.star.text.TextContentAnchorType.AT_PARAGRAPH );
//			xPropSet.setPropertyValue( "Graphic", xGraphic );
//			
//			xPropSet.setPropertyValue( "HoriOrientPosition",
//                    Integer.valueOf( 5500 ) );
//			xPropSet.setPropertyValue( "VertOrientPosition",
//                    Integer.valueOf( 4200 ) );
//			
//			xPropSet.setPropertyValue( "Width", Integer.valueOf( 4400 ) );
//			xPropSet.setPropertyValue( "Height", Integer.valueOf( 4000 ) );
//			xPropSet.setPropertyValue("BackColor", new Integer(19921999));
			
			/*XMultiServiceFactory xWriterFactory =
					(XMultiServiceFactory)UnoRuntime.queryInterface(
					XMultiServiceFactory.class, xComp);
			Object writerShape = xWriterFactory.createInstance(
					"com.sun.star.drawing.EllipseShape");			
			XShape xWriterShape = (XShape)UnoRuntime.queryInterface(
					XShape.class, writerShape);			
			xWriterShape.setSize(new Size(3000, 3000));
			XTextContent xTextContentShape =
					(XTextContent)UnoRuntime.queryInterface(
					XTextContent.class, writerShape);
			xText.insertTextContent(xText.getEnd(), xTextContentShape,
					false);
					XPropertySet xShapeProps =
					(XPropertySet)UnoRuntime.queryInterface(
					XPropertySet.class, writerShape);
					// wrap text inside shape
					xShapeProps.setPropertyValue("TextContourFrame", new
					Boolean(true));
					xShapeProps.setPropertyValue("FillColor", new Integer(0x990000));
					xShapeProps.setPropertyValue("LineColor", new Integer(0x000099));
					
					Object table =
							xWriterFactory.createInstance("com.sun.star.text.TextTable");
							XTextContent xTextContentTable =
							(XTextContent)UnoRuntime.queryInterface(
							XTextContent.class, table);
							xText.insertTextContent(xText.getEnd(), xTextContentTable,
							false);
							XCellRange xCellRange = (XCellRange)UnoRuntime.queryInterface(
							XCellRange.class, table);
							XCell xCell = xCellRange.getCellByPosition(0, 1);
							XText xCellText = (XText)UnoRuntime.queryInterface(XText.class,
							xCell);
							
							xCellText.setString("Quotation");
							xCell = xCellRange.getCellByPosition(1,0);
							xCellText = (XText)UnoRuntime.queryInterface(XText.class, xCell);
							xCellText.setString("Data");
							// cell value
							xCell = xCellRange.getCellByPosition(1,1);
							xCell.setValue(0);*/
			
			
			
//			String[] ss = xWriterFactory.getAvailableServiceNames();		
			//Object o = UnoRuntime.queryInterface(new Type("com.sun.star.drawing.EllipseShape"), xText);
			
			 /*XDrawPageSupplier drawPageSupplier = UnoRuntime.queryInterface(XDrawPageSupplier.class, xTextDoc); 
			 XShapes xShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, drawPageSupplier.getDrawPage());
			 
			 for(int idx = 0; idx < xShapes.getCount(); idx ++) {
				 
			 
			 XShape dd = (XShape)UnoRuntime.queryInterface(XShape.class, xShapes.getByIndex(idx));*/
			 /*dd.setSize(new Size(2000, 2000));*/
			 /*System.out.println("Type: " + dd.getShapeType()); */
			 /*XNamed xShapeNamed =UnoRuntime.queryInterface(XNamed.class, dd);
			 System.out.println("Name: " + xShapeNamed.getName());*/
			/*XPropertySet xShapeProps =
					(XPropertySet)UnoRuntime.queryInterface(
					XPropertySet.class, dd);*/
			/*xShapeProps.setPropertyValue("FillColor", new Integer(0x990000));*/
			//xShapeProps.setPropertyValue("Name", "DCondition");
			/*Object name = xShapeProps.getPropertyValue("Name");
			System.out.println("Name: " + name);*/
			
			
			 /*}*/
			 
			 /*XSelectionSupplier selSupplier = (XSelectionSupplier)UnoRuntime.queryInterface(XSelectionSupplier.class, xTextDoc.getCurrentController());
			 XShapes xSelectedShapes = (XShapes) UnoRuntime.queryInterface( XShapes.class, selSupplier.getSelection());
			 XShape selectedShape = (XShape)UnoRuntime.queryInterface(XShape.class, xSelectedShapes.getByIndex(0));
			 XPropertySet xShapeProps =
						(XPropertySet)UnoRuntime.queryInterface(
						XPropertySet.class, selectedShape);
			 System.out.println("Selection" + xShapeProps.getPropertyValue("Name"));*/
			 
			/*dd = (XShape)UnoRuntime.queryInterface(XShape.class, xShapes.getByIndex(1));
			 dd.setSize(new Size(600, 2000));*/
			 
			
			//lineShape.setSize( new Size(5, 0)); 
			//JOptionPane.showMessageDialog(null, x.toString());
			//o.hasElements()
			/*XNameAccess xNameAccess = x.getGraphicObjects();
			
			if (xNameAccess != null && xNameAccess.hasElements()) {
	            String[] names = xNameAccess.getElementNames();

	            for (int i = 0; i < names.length; i++) {
	                Object oGraphics = xNameAccess.getByName(names[i]);
	                JOptionPane.showMessageDialog(null, oGraphics.toString());
	            }
			}*/
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean isReady() {
		return m_initialized;
	}

	@Override
	public String getName() {
		return ServiceName.LO.getName();
	}

}
