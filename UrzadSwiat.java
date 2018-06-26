package projekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class UrzadSwiat extends JPanel {
	
	 Random rand = new Random();
	 Image urzad;
	 Semaphore okno = new Semaphore(1);
	 Semaphore kasa = new Semaphore(1);
	 
	 ArrayList<Interesant> ludzie = new ArrayList<Interesant>();
	
	
	public UrzadSwiat() {
		MediaTracker mt = new MediaTracker(this);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		urzad = toolkit.getImage("image/urzad.gif");
        mt.addImage(urzad, 0);
        try {
            mt.waitForID(0);
        } catch (java.lang.InterruptedException e) {
            System.out.println("Nie mozna zaladowac obrazka");
        }
        
    	ludzie.add(new Interesant(this,null));
    	setPreferredSize(new Dimension(urzad.getWidth(null),urzad.getHeight(null)));
	}
	
	 public void paintComponent(Graphics g) {
			g.drawImage(urzad,0,0,this);
		        for (Interesant i : ludzie) {
		        	i.draw(g);
		        }
	 }

	
	 public void dodajInteresanta(UrzadSwiat u) {
		SwingUtilities.invokeLater(new Runnable () {
			public void run() {
				Interesant i = new Interesant(u, ludzie.get(ludzie.size()-1));
				ludzie.add(i);
				new Thread(i).start();
			}
		});
	}
}
