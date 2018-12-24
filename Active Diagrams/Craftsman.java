package controller;

import com.sun.star.awt.Point;
import com.sun.star.awt.Size;
import com.sun.star.drawing.XShape;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.lang.NullPointerException;

public class Craftsman {
	
	XMultiServiceFactory m_Xfactory;
	
	public void assignTo(XMultiServiceFactory factory) throws NullPointerException {
		if (factory == null) {
			throw new NullPointerException();
		}
		m_Xfactory = factory;
	}
	
	public XShape createShape(int x, int y, int	width, int height, String shapeType) throws Exception {
		if (m_Xfactory == null) {
			throw new NullPointerException();
		}
		
		Object objShape = m_Xfactory.createInstance(shapeType);
		Point position = new Point(x, y);
		Size size = new Size(width, height);
		
		XShape xShape = (XShape)UnoRuntime.queryInterface(XShape.class, objShape);
		xShape.setPosition(position);
		xShape.setSize(size);
		
		return xShape;
	}
}
