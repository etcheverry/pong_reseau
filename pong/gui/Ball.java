package pong.gui;

import java.awt.Dimension;

public class Ball extends PongItem{

	public Ball(Dimension area){
		super("image/sharlball.png", 0, 0, 2, 2, area);
	}

	public void animate(){
		super.animate();

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
}