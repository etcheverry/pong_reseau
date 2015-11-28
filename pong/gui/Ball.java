package pong.gui;

import java.awt.Dimension;
import java.awt.Point;

public class Ball extends PongItem{


	public Ball(Dimension area){
		super("image/sharlball.png", (int)area.getWidth()/2, (int)area.getHeight()/2, 3, 3, area);
	}

	public void animate(Racket racket){
		/*
		forall objects {
			if collision object.nextpos {
				object.updateDeplacement
			}
			object.move
		}*/
		
		//Need to modify this because the order of the items can affect their behavior
		setNextPos(this.getSpeed().x + this.getPosition().x, this.getSpeed().y + this.getPosition().y );

		if(collision(racket)){
			this.setSpeedX(-this.getSpeed().x);
			setNextPos(this.getSpeed().x + this.getPosition().x, this.getSpeed().y + this.getPosition().y );
		}
		if (this.getNextPos().x < 0)
		{
			this.setNextPos((int)getArea().getWidth()/2, (int)getArea().getHeight()/2);
			this.setSpeedX(-this.getSpeed().x);
		}
		if (this.getNextPos().y < 0)
		{
			this.setNextPos(getNextPos().x, 0);
			this.setSpeedY(-this.getSpeed().y);
		}
		if (this.getNextPos().x > this.getArea().width - this.getWidth())
		{
			this.setNextPos((int)getArea().getWidth()/2, (int)getArea().getHeight()/2);
			this.setSpeedX(-this.getSpeed().x);
		}
		if (this.getNextPos().y > this.getArea().height - this.getHeight())
		{
			this.setNextPos(getNextPos().x, this.getArea().height - this.getHeight());
			this.setSpeedY(-this.getSpeed().y);
		}
		super.animate();
	}

	public boolean collision(PongItem item){
		if(item instanceof Racket){
			Racket r = (Racket) item;
			
			if (this.getNextPos().x <= r.getNextPos().x + r.getWidth()
				&& this.getPosition().x >= r.getNextPos().x + r.getWidth()
				//The ball is touching the right edge of the racket
				|| this.getNextPos().x + this.getWidth() >= r.getNextPos().x
				&& this.getPosition().x + this.getWidth() <= r.getNextPos().x
				/*The ball is touching the left edge of the racket*/)
			{
				
				if(this.getNextPos().y <= r.getNextPos().y
					&& this.getNextPos().y + this.getHeight() >= r.getNextPos().y)
					//The ball is on the upper edge of the racket
					return true;
				if(this.getNextPos().y > r.getNextPos().y
					&& this.getNextPos().y + this.getHeight() <= r.getNextPos().y + r.getHeight())
					//The ball is inside the racket
					return true;
				if(this.getNextPos().y <= r.getNextPos().y + r.getHeight()
					&& this.getNextPos().y + this.getHeight() >= r.getNextPos().y + r.getHeight())
					//The ball is on the down edge of the racket
					return true;
				
			}
			
				
		}
		return false;
	}
}