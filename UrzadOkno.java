package projekt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UrzadOkno extends JFrame {
	UrzadSwiat ekran;
	JButton dodaj;
	
	public UrzadOkno() {
		super("Urzad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		
		c.setLayout(new BorderLayout());
		ekran = new UrzadSwiat();
		
		c.add("Center",ekran);
		dodaj = new JButton("Dodaj");
		
		dodaj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ekran.dodajInteresanta(ekran);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(dodaj);
		c.add("South",panel);
		
		
	}
}
