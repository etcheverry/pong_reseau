package pong.gui;
import java.awt.Point;
import java.awt.Dimension;


public class Racket extends PongItem{
	// usefull to know the speed before the racket stop
	private int saved_speed;

	// direction of the racket
	private boolean up = false;
	private boolean down = false;

	public Racket(Dimension area, int player_number){
		super("image/racket.png", 0, area.width/2, 0, 0, area);

		if(player_number == 1)
			setX(0);
		else if(player_number == 2)
			setX(area.width - this.getWidth());
		saved_speed = 4;
	}

	public void updateNextPos(){
		super.updateNextPos();
		//set the speed according to direction booleans
		if(up)
			this.setSpeedY(-saved_speed);
		else if(down)
			this.setSpeedY(saved_speed);
		else
			this.setSpeed(0, 0);
		
		

		/* Update racket next position */
		if (this.getNextPos().y < 0){
			this.setNextPos(getNextPos().x, 0);
		}
		if (this.getNextPos().y > this.getArea().height - this.getHeight()){
			this.setNextPos(getNextPos().x, this.getArea().height - this.getHeight());
		}
	}

	public void down(){
		down = true;
		if(up)
			up = false;
	}

	public void up(){
		up = true;
		if(down)
			down = false;
	}

	public void nup(){
		up = false;
	}

	public void ndown(){
		down = false;
	}

	public void setSavedSpeed(int new_speed){
		this.saved_speed = new_speed;
	}
}