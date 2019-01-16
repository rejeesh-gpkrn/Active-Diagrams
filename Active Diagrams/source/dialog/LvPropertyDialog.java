package dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;

public class LvPropertyDialog extends JDialog {
	
	private LvPropertyDialogActionAdapter btnActionAdapter = new LvPropertyDialogActionAdapter(this);
	
	private LvPropertyDialogWindowListener windowListener = new LvPropertyDialogWindowListener(this);
	
	private LvPropertyInfo propertyInfo;
	
	private JTextField jtxtPropNameValue;
	private JButton jbtnApply;

	/**
	 * Default.
	 */
	public LvPropertyDialog() {
		super();
		
		decorate();
		
		addParts();
		
		// Event subscription.
		addEventListener();
		
		propertyInfo = new LvPropertyInfo();
	}
	
	private void decorate() {
		
		setType(Type.POPUP);
		setTitle("Activation Settings");
	}
	
	private void addParts() {
		
		getContentPane().setLayout(null);

		JLabel jlblPropName = new JLabel("Name");
		jlblPropName.setBackground(Color.BLACK);
		jlblPropName.setHorizontalAlignment(SwingConstants.CENTER);
		jlblPropName.setBounds(34, 49, 69, 20);
		getContentPane().add(jlblPropName);
		
		jtxtPropNameValue = new JTextField();
		jtxtPropNameValue.setBounds(104, 46, 294, 26);
		getContentPane().add(jtxtPropNameValue);
		jtxtPropNameValue.setColumns(10);
		
		jbtnApply = new JButton("Apply");
		jbtnApply.setBounds(300, 198, 98, 30);
		getContentPane().add(jbtnApply);
	}
	
	private void addEventListener() {
		jbtnApply.addActionListener(btnActionAdapter);
		this.addWindowListener(windowListener);
	}
	
	public LvPropertyInfo showModal() {
		
		this.setSize(500, 400);
		this.setModal(true);
		this.setVisible(true);
		return propertyInfo;
	}
	
	public void jbtnApply_actionPerformed(ActionEvent e) {
		
		propertyInfo.setName(jtxtPropNameValue.getText());
	}
	
	public void onClosing(WindowEvent e) {
		doClose();
		System.out.println("Closing");
	}
	
	private void doClose() {
		jbtnApply.removeActionListener(btnActionAdapter);
	}
}

// TODO: Move as common class.
class LvPropertyDialogActionAdapter implements ActionListener {
	
	private LvPropertyDialog adaptee;

	/**
	 * @param adaptee
	 */
	public LvPropertyDialogActionAdapter(LvPropertyDialog adaptee) {
		super();
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		adaptee.jbtnApply_actionPerformed(arg0);
	}
}

//TODO: Move as common class.
class LvPropertyDialogWindowListener implements WindowListener {
	
	private LvPropertyDialog adaptee;
	
	/**
	 * @param adaptee
	 */
	public LvPropertyDialogWindowListener(LvPropertyDialog adaptee) {
		super();
		
		this.adaptee = adaptee;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		adaptee.onClosing(e);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
