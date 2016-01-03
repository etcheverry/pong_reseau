package pong.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Ball extends PongItem{


	public Ball(Dimension area){
		super("image/sharlball.png", (int)area.getWidth()/2, (int)area.getHeight()/2, 3, 0, area);
	}

	public void updateNextPos(ArrayList<PongItem> items){
		super.updateNextPos();

		for(Iterator i = items.iterator() ; i.hasNext(); ){
			PongItem item = (PongItem)i.next();
			if(collision(item))
				speedAfterCollision(item);
		}

		if (this.getNextPos().y < 0)
		{
			this.setNextPos(getNextPos().x, 0);
			this.setSpeedY(-this.getSpeed().y);
		}
		if (this.getNextPos().y > this.getArea().height - this.getHeight())
		{
			this.setNextPos(getNextPos().x, this.getArea().height - this.getHeight());
			this.setSpeedY(-this.getSpeed().y);
		}
	}

	public boolean collision(PongItem item) {
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
					//The ball is on the bottom edge of the racket
					return true;
			}
		}
		return false;
	}

	private void speedAfterCollision(PongItem item) {
		int midBallPos = (this.getNextPos().y + this.getNextPos().y + this.getHeight()) / 2;

		if(item instanceof Racket){
			Racket r = (Racket) item;
			int midRacketPos = (r.getNextPos().y + r.getNextPos().y + r.getHeight()) / 2;

			//if the ball hit the middle of the racket
			if(Math.abs(midBallPos - midRacketPos) <= r.getHeight() * 0.3){
				//vertical speed dicrease
				this.setSpeedY((int)Math.round(this.getSpeed().y -(this.getSpeed().y / 2)));
				//horizontal speed increase
				if(Math.abs(this.getSpeed().x) < 5){ //speed limit
					if(this.getSpeed().x > 0)
						this.setSpeedX(- (this.getSpeed().x + 1));
					else
						this.setSpeedX(- (this.getSpeed().x - 1));
				}
				else
					this.setSpeedX(-this.getSpeed().x);
			}
			//if the ball hit the top of the racket
			else if(midBallPos < midRacketPos - (r.getHeight() * 0.3)){
				if(Math.abs(this.getSpeed().y) < 5){ //speed limit
					//hit from above => vertical speed decrease
					if(this.getSpeed().y > 0)
						this.setSpeedY((int)Math.round(this.getSpeed().y -(this.getSpeed().y * 0.8)));
					//front hit and from below => vertical speed increase
					else
						this.setSpeedY(this.getSpeed().y -1);
				}	
				this.setSpeedX(-this.getSpeed().x);
			}
			//if the ball hit the bottom of the racket
			else if(midBallPos > midRacketPos + (r.getHeight() * 0.3)){
				if(Math.abs(this.getSpeed().y) < 5){ //speed limit
					//hit from below => vertical speed decrease
					if(this.getSpeed().y < 0) 
						this.setSpeedY((int)Math.round(this.getSpeed().y -(this.getSpeed().y * 0.8)));
					//front hit and from above => vertical speed increase
					else
						this.setSpeedY(this.getSpeed().y +1);
				}	
				this.setSpeedX(-this.getSpeed().x);
			}		
		}
	}
}