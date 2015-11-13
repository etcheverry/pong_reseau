package pong.gui;

import java.awt.Dimension;

public class Ball extends PongItem{

	public Ball(Dimension area){
		super("image/sharlball.png", (int)area.getWidth()/2, (int)area.getHeight()/2, 3, 3, area);
	}

	public void animate(Racket racket){
		super.animate();

		if(collision(racket)){
			this.setSpeedX(-this.getSpeed().x);
		}

		if (this.getPosition().x < 0)
		{
			this.setX(0);
			this.setSpeedX(-this.getSpeed().x);
		}
		if (this.getPosition().y < 0)
		{
			this.setY(0);
			this.setSpeedY(-this.getSpeed().y);
		}
		if (this.getPosition().x > this.getArea().width - this.getWidth())
		{
			this.setX(this.getArea().width - this.getWidth());
			this.setSpeedX(-this.getSpeed().x);
		}
		if (this.getPosition().y > this.getArea().height - this.getHeight())
		{
			this.setY(this.getArea().height - this.getHeight());
			this.setSpeedY(-this.getSpeed().y);
		}
	}

	public boolean collision(PongItem item){
		if(item instanceof Racket){
			Racket r = (Racket) item;
			if(this.getPosition().x <= r.getPosition().x + r.getWidth()
				&& this.getPosition().x + this.getWidth() >= r.getPosition().x
				&& this.getPosition().y <= r.getPosition().y + r.getHeight()
				&& this.getPosition().y + this.getHeight() >= r.getPosition().y)
				return true;
		}
		return false;
	}
}