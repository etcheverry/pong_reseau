package pong.gui;

import java.awt.Dimension;
import java.awt.Point;

public class Ball extends PongItem{


	public Ball(Dimension area){
		super("image/sharlball.png", /*(int)area.getWidth()/2 */ 100, 100 /*(int)area.getHeight()/2*/, 0, 2, area);
	}

	public void animate(Racket racket){
		/*
		forall objects {
			if collision object.nextpos {
				object.updateDeplacement
			}
			object.move
		}*/
		setNextPos(this.getSpeed().x + this.getPosition().x, this.getSpeed().y + this.getPosition().y );
		
		CollisionState state = collision(racket);
		if(state == CollisionState.HORIZONTAL){
			//this.setX(racket.getPosition().x + racket.getWidth());
			this.setSpeedX(-this.getSpeed().x);
		}
		if(state == CollisionState.VERTICAL){
			this.setSpeedY(-this.getSpeed().y);
			this.setNextPos(this.getPosition().x, racket.getNextPos().y - this.getHeight() + 1);
		}
		if (this.getNextPos().x < 0)
		{
			this.setNextPos(0, getNextPos().y);
			this.setSpeedX(-this.getSpeed().x);
		}
		if (this.getNextPos().y < 0)
		{
			this.setNextPos(getNextPos().x, 0);
			this.setSpeedY(-this.getSpeed().y);
		}
		if (this.getNextPos().x > this.getArea().width - this.getWidth())
		{
			this.setNextPos(this.getArea().width - this.getWidth(), getNextPos().y);
			this.setSpeedX(-this.getSpeed().x);
		}
		if (this.getNextPos().y > this.getArea().height - this.getHeight())
		{
			this.setNextPos(getNextPos().x, this.getArea().height - this.getHeight());
			this.setSpeedY(-this.getSpeed().y);
		}
		super.animate();
	}

	public CollisionState collision(PongItem item){
		if(item instanceof Racket){
			Racket r = (Racket) item;
			
			if (this.getNextPos().x < r.getNextPos().x + r.getWidth()
				&& this.getNextPos().y + this.getHeight() > r.getNextPos().y
				&& this.getNextPos().y < r.getNextPos().y + r.getHeight()){
			System.out.println("Vertical");
			return CollisionState.VERTICAL;

			}
			
				
		}
		return CollisionState.NONE;
	}
}