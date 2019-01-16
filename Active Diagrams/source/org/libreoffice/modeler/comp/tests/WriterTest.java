package org.libreoffice.modeler.comp.tests.uno;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.libreoffice.modeler.comp.tests.helper.UnoHelper;

import com.sun.star.text.XTextDocument;

public class WriterTest {
	
	private XTextDocument xTextDocument;

	@Before
	public void setUp() throws Exception {
		xTextDocument = UnoHelper.getWriterDocument();
	}

	@Test
	public void test() {
		assertNotNull(xTextDocument);
	}

}
