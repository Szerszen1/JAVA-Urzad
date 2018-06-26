package projekt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Interesant implements Runnable {

	 int xpos, ypos; 
	 UrzadSwiat u;
	 Random r = new Random();
	 Interesant inFront;
	 Image obraz;
	 private final static int startY = -600;
	 private final static int startX = 1600;
	 Color kolor = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	 public Interesant(UrzadSwiat u,Interesant inFront) {
		 this.u = u;
		 this.inFront = inFront;
		 xpos = inFront == null ? startX : 700;// im wiekszy X tym bardziej w prawo
		 ypos = inFront == null ? startY : Math.max(900,inFront.pobierzY()+100);//im wiekszy Y tym bardziej w dó³ 
	 }
	 /*
	  * przy okienku X = 700 Y = 300
	  * liia do okienka X = 700 Y = 440
	  * droga do kolejki do kasy X = 1025 Y = 300
	  * kasa X = 1340 Y = 300
	  * 
	  */

	public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(kolor);
			g2d.fillOval(xpos, ypos, 80, 80);
	 }
	
	public boolean czyWolne() {
		if(Math.abs(ypos - inFront.pobierzY()) >= 90)
			return true;
		else
			return false;
	}
	private int pobierzY() {
		return ypos;
	}
	
	private int pobierzX() {
		return xpos;
	}
	
	public void wejdz() {
		while(ypos >= 440) {
			if(czyWolne()) 
				ypos -= 4;
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

				
		}
	}
	public void doOkienka() {
		try {
			u.okno.acquire();
			while(ypos >= 300) {
				ypos-=4;
				Thread.sleep(40);
			}
			Thread.sleep(ThreadLocalRandom.current().nextLong(2000));
			while(xpos <= 1025) {
				xpos+=4;
				Thread.sleep(40);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		u.okno.release();
	}
	public void doKolejki() {
		while(ypos <= inFront.pobierzY() + 125) {
			if(czyWolne() || Math.abs(xpos - inFront.pobierzX()) >= 90) 
				ypos += 4;

			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		while(xpos <= 1340) {
			if(czyWolne() || Math.abs(xpos - inFront.pobierzX()) >= 90) 
				xpos += 4;
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(ypos >= 440) {
			if(czyWolne()) 
				ypos -= 4;
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void doKasy() {
		try {
			u.kasa.acquire();
			while(ypos >= 300) {
				ypos-=4;
				Thread.sleep(40);
			}
			Thread.sleep(ThreadLocalRandom.current().nextLong(20000));
			while(xpos <= 1600) {
				xpos+=4;
				Thread.sleep(40);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		u.kasa.release();
		
	}
	


	
	@Override
	public void run() {
		wejdz();
		doOkienka();
		doKolejki();
		doKasy();
	}

}