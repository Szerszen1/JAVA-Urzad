package projekt;

public class Main {
    
	private static void nap(int ms) {
	try {
	    Thread.sleep(ms);
	} catch (InterruptedException e) {}
    }

	public static void main(String[] args) {
		
		final UrzadOkno okno = new UrzadOkno();
		
		okno.pack();
		okno.setVisible(true);

		new Thread(new Runnable() {
			public void run() {
			    while (true) {
				nap(25);
				okno.repaint();
			    }
			}
		}).start();
	}
		
}

