package test;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
	
	enum Direction { UP, DOWN, RIGHT, LEFT }
	
	private ArrayList<SnakeUnit> body;
	private Direction direction;
	private BonusPoint bonusPoint;

	private int SCORE = 0;
	
	public Snake(BonusPoint bonusPoint) {
		this.direction = Direction.RIGHT;
		
		SnakeUnit head = new SnakeUnit(null, new Point(10, 10), null, direction);
		SnakeUnit firstUnit = new SnakeUnit(head, new Point(9, 10), null, direction);
		head.addPostUnit(firstUnit);
		SnakeUnit secondUnit = new SnakeUnit(firstUnit, new Point(8, 10), null, direction);
		firstUnit.addPostUnit(secondUnit);
		
		this.body = new ArrayList<SnakeUnit>();
		this.body.add(head);
		this.body.add(firstUnit);
		this.body.add(secondUnit);
		
		this.bonusPoint = bonusPoint;
		changeBonusPointLocation();
	}
	
	public void move() {
		if(isBonusPointReached()) {
			SCORE += 10;
			this.addUnit(this.body.get(this.body.size()-1));
			changeBonusPointLocation();
		}
		if(!isCrashed())
			for(SnakeUnit s : body)
				s.move(this.direction);
		
	}
	
	private void changeBonusPointLocation() {
		do {
			int bpX = (int)(Math.random() * SnakeGame.WIDTH);
			int bpY = (int)(Math.random() * SnakeGame.HEIGHT);
			bonusPoint.setLocation(new Point(bpX, bpY));
		} while(isOnSnake(bonusPoint.getLocation()));			
	}
	
	private boolean isOnSnake(Point p) {
		for(SnakeUnit snakeUnit : this.body)
			if(snakeUnit.getLocation().equals(p))
				return true;
		return false;
	}
	
	public void setDirection(Direction direction) {
		//if(!isOppositeDirection(direction) && !isCrashed()) {
		if(!willCrashesAfterOneMove(direction) && !isCrashed()) {
			this.direction = direction;
		}
	}
	
	public ArrayList<SnakeUnit> getBody() {		
		return this.body;
	}
	
	public void addUnit(SnakeUnit preUnit) {
		SnakeUnit newUnit = new SnakeUnit(
				preUnit,
				preUnit.getLocation(),
				null,
				preUnit.getDirection());
		preUnit.addPostUnit(newUnit);
		this.body.add(newUnit);
	}
	
	public void removeUnit() {
		if(this.body.size() > 1)
			this.body.remove(this.body.size() - 1);
	}
	
	@SuppressWarnings("unused")
	private boolean isOppositeDirection(Direction direction) {
		switch(direction) {
			case RIGHT: if(this.direction == Direction.LEFT) return true; break;
			case LEFT: if(this.direction == Direction.RIGHT) return true; break;
			case UP: if(this.direction == Direction.DOWN) return true; break;
			case DOWN: if(this.direction == Direction.UP) return true; break;
			default: return false;
		}
		return false;
	}
	
	private boolean willCrashesAfterOneMove(Direction direction) {
		if(SnakeUnit.getNextPosition(
			  	direction, 
				this.body.get(0).getLocation()
			  ).equals(
				this.body.get(1).getLocation()
			  )
		  )
			return true;
		return false;
	}
	
	public boolean isCrashed() {
		for(SnakeUnit snakeUnit : this.body) {
			if(snakeUnit.getLocation().
					equals(
							SnakeUnit.getNextPosition(
									this.direction, 
									this.body.get(0).getLocation()
							)
					)
				)
				return true;
		}
		return false;
	}
	
	public boolean isBonusPointReached() {
		if(bonusPoint.getLocation().equals(this.body.get(0).getLocation()))
			return true;
		else
			return false;
	}
	
	public int getScore() {
		return SCORE;
	}
}
