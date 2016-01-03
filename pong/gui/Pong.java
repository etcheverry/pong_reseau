package pong.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BasicStroke;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.awt.Font;
import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 * An Pong is a Java graphical container that extends the JPanel class in
 * order to display graphical elements.
 */
public class Pong extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	private ArrayList<PongItem> items;

	/**
	 * Constant (c.f. final) common to all Pong instances (c.f. static)
	 * defining the background color of the Pong
	 */

	private static Color backgroundColor = new Color(0, 255, 255);
	private static Color textColor = new Color(0, 0, 0);
	private Font stringFont = new Font( "SansSerif", Font.PLAIN, 30 );

	/**
	 * Pong area
	 */
	private static final Dimension AREA_SIZE = new Dimension(800,600);
	/**
	 * Time step of the simulation (in ms)
	 */
	public static final int timestep = 10;

	/**
	 * Pixel data buffer for the Pong rendering
	 */
	private Image buffer = null;
	/**
	 * Graphic component context derived from buffer Image
	 */
	private Graphics graphicContext = null;

	private Network network;

	private int [] score;

	public Pong(Network network) {
		this.items = new ArrayList<PongItem>();
		this.network = network;
		this.setPreferredSize(AREA_SIZE);
		this.addKeyListener(this);
	}

	public void initItems(){
		PongItem itemvide = new PongItem();
		for(int i = 0; i < network.getPlayers().length + 1; i++){
			items.add(itemvide);
		}
		items.set(network.getID(), new Racket(AREA_SIZE, network.getID()));
		System.out.println("Client id :" + network.getID());
		for(int i = 0; i < network.getPlayers().length ; i++){
			items.set(network.getPlayers()[i].getID(), new Racket(AREA_SIZE, network.getPlayers()[i].getID()));
			System.out.println("Player id :" + network.getPlayers()[i].getID());
			//network.getPlayers()[i].setRacket(r);
		}
		items.add(new Ball(AREA_SIZE));
		Ball ball2 = new Ball(AREA_SIZE);
		ball2.setSpeedX(-3);
		items.add(ball2);
		score = new int[network.getPlayers().length + 1];
	}

	private void updateScore(Ball b){
		if (b.getNextPos().x < 0)
		{
			b.setNextPos(AREA_SIZE.width /2 - b.getWidth() /2, AREA_SIZE.height / 2 - b.getHeight() /2);
			b.setSpeed(3, 0);
			score[1] ++;
		}
		if (b.getNextPos().x > b.getArea().width - b.getWidth())
		{
			b.setNextPos(AREA_SIZE.width /2 - b.getWidth() /2, AREA_SIZE.height / 2 - b.getHeight() /2);
			b.setSpeed(-3, 0);
			score[0] ++;
		}
	}

	/**
         * Proceeds to the movement of the ball and updates the screen
	 */
	public void animate() {
		for(Iterator it = items.iterator() ; it.hasNext(); ){
			PongItem item = (PongItem)it.next();
			if(item instanceof Ball){
				Ball b = (Ball) item;
				b.updateNextPos(this.items);
				updateScore(b);
				network.sendPosition(b.getNextPos(), items.indexOf(b));
				network.sendSpeed(b.getSpeed(), items.indexOf(b));
				for(int i = 0 ; i < network.getPlayers().length ; i++){
					analyze(network.receive(i));
					analyze(network.receive(i));
				}
				/*
				for(int i = 0 ; i < network.getPlayers().length ; i++){
					String msgPos = "pos " + Integer.toString(b.getNextPos().x) + " " + Integer.toString(b.getNextPos().y);
					String msgSpeed = "speed " + Integer.toString(b.getSpeed().x) + " " + Integer.toString(b.getSpeed().y);
					network.getPlayers()[i].write(msgSpeed);
					network.getPlayers()[i].write(msgPos);
				}
				for(int i = 0 ; i < network.getPlayers().length ; i++){
					for(int j = 0; j < 2; j++){
						String msg = network.getPlayers()[i].read();
						String tmsg[] = msg.split(" ");
						if(tmsg[0].equals("pos")){
							int x = Integer.parseInt(tmsg[1]);
							int y = Integer.parseInt(tmsg[2]);
							if(b.getNextPos().x != x || b.getNextPos().y != y){
								System.out.println("Position desync");
								b.setNextPos((b.getNextPos().x + x) / 2, (b.getNextPos().y + y) / 2);
							}
						}
						else if(tmsg[0].equals("speed")){
							int x = Integer.parseInt(tmsg[1]);
							int y = Integer.parseInt(tmsg[2]);
							if(b.getSpeed().x != x || b.getSpeed().y != y){
								System.out.println("Speed desync");
								b.setSpeed((b.getSpeed().x + x) / 2, (b.getSpeed().y + y) / 2);
							}
						}
					}
					
				}*/
				
			}
			
			if(item instanceof Racket){
				Racket r = (Racket) item;
				r.updateNextPos();
				network.sendPosition(r.getNextPos(), items.indexOf(r));
				for(int i = 0 ; i < network.getPlayers().length ; i++){
					analyze(network.receive(i));
				}
			}
		}
		/*
		for(int i = 0 ; i < network.getPlayers().length ; i++){
			network.getPlayers()[i].write(Integer.toString(items.get(0).getPosition().y));
		}
		for(int i = 0 ; i < network.getPlayers().length ; i++){
			network.getPlayers()[i].getRacket().setNextPos(network.getPlayers()[i].getRacket().getNextPos().x, Integer.parseInt(network.getPlayers()[i].read()));
		}
	*/


		for(Iterator it = items.iterator() ; it.hasNext(); ){
			PongItem item = (PongItem)it.next();
			item.animate();
		}

		/* And update output */
		updateScreen();
	}

	private void analyze(String[] msg){
		if(msg[0].equals("pos")){
			int id = Integer.parseInt(msg[1]);
			int x = Integer.parseInt(msg[2]);
			int y = Integer.parseInt(msg[3]);
			PongItem item = items.get(id);
			if(item instanceof Ball){
				Ball b = (Ball) item;
				if(b.getNextPos().x != x || b.getNextPos().y != y){
					System.out.println("Position desync");
					b.setNextPos((b.getNextPos().x + x) / 2, (b.getNextPos().y + y) / 2);
				}
			}
			if(item instanceof Racket){
				Racket r = (Racket) item;
				if(y > r.getPosition().y)
					r.setNextPos(r.getNextPos().x, r.getNextPos().y + r.getSavedSpeed());
				else if(y < r.getPosition().y)
					r.setNextPos(r.getNextPos().x, r.getNextPos().y - r.getSavedSpeed());
			}
		}
		else if (msg[0].equals("speed")){
			int id = Integer.parseInt(msg[1]);
			int x = Integer.parseInt(msg[2]);
			int y = Integer.parseInt(msg[3]);
			PongItem item = items.get(id);
			if(item instanceof Ball){
				Ball b = (Ball) item;
				if(b.getSpeed().x != x || b.getSpeed().y != y){
					System.out.println("Speed desync");
					b.setSpeed((b.getSpeed().x + x) / 2, (b.getSpeed().y + y) / 2);
				}
			}
			else
				System.out.println("Unknown element");
		}
	}
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			((Racket)items.get(network.getID())).up();
			break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			((Racket)items.get(network.getID())).down();
			break;
			default:
			System.out.println("got press "+e);
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			((Racket)items.get(network.getID())).nup();
			break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			((Racket)items.get(network.getID())).ndown();
			break;
			default:
			System.out.println("got release "+e);
		}
	}
	public void keyTyped(KeyEvent e) { }

	/*
	 * (non-Javadoc) This method is called by the AWT Engine to paint what
	 * appears in the screen. The AWT engine calls the paint method every time
	 * the operative system reports that the canvas has to be painted. When the
	 * window is created for the first time paint is called. The paint method is
	 * also called if we minimize and after we maximize the window and if we
	 * change the size of the window with the mouse.
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Draw each Pong item based on new positions
	 */
	public void updateScreen() {
		if (buffer == null) {
			/* First time we get called with all windows initialized */
			buffer = createImage(AREA_SIZE.width, AREA_SIZE.height);
			if (buffer == null)
				throw new RuntimeException("Could not instanciate graphics");
			else
				graphicContext = buffer.getGraphics();
		}
		graphicContext.setFont(stringFont);
		/* Fill the area with blue */
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, AREA_SIZE.width, AREA_SIZE.height);
		graphicContext.setColor(textColor);
		graphicContext.drawLine(AREA_SIZE.width/2, 0, AREA_SIZE.width/2, AREA_SIZE.height);
		
		/* Draw items */
		for(Iterator i = items.iterator() ; i.hasNext(); ){
			PongItem item = (PongItem)i.next();
			item.draw(graphicContext);
		}
		graphicContext.drawString(Integer.toString(score[0]), AREA_SIZE.width/2 - 130, 40);
		graphicContext.drawString(Integer.toString(score[1]), AREA_SIZE.width/2 + 100, 40);
		this.repaint();
	}
}
