package test;

import java.awt.Point;

import main.SnakeUnitForm;
import test.Snake.Direction;

public class SnakeUnit {
	private SnakeUnit preUnit;
	private SnakeUnit postUnit;
	private Point location;
	private Point preLocation;
	private Direction direction;
	private Direction preDirection;
	private SnakeUnitForm form;
	
	public SnakeUnit(SnakeUnit preUnit, Point location, SnakeUnit postUnit, Direction direction) {
		this.preUnit = preUnit;
		this.location = location;
		this.preLocation = location;
		this.postUnit = postUnit;
		this.direction = direction;
		this.form = getUnitForm(direction, direction);
	}
	
	public boolean isHead() {
		if(this.preUnit == null)
			return true;
		return false;
	}
	
	public boolean isTail() {
		if(this.postUnit == null)
			return true;
		return false;
	}
	
	public void checkForm(Direction direction) {
		this.preDirection = this.direction;
		if(this.isHead()) {
			this.direction = direction;
			this.form = getUnitForm(direction, this.direction);
		} else {
			this.direction = this.preUnit.preDirection;
			this.form = getUnitForm(preUnit.direction, this.direction);
		}
	}
	
	public void move(Direction direction) {
		this.preLocation = new Point(this.location.x, this.location.y);
		if(this.isHead()) {
			this.location = getNextPosition(direction, location);
		} else {
			this.location = this.preUnit.preLocation;
		}
		checkForm(direction);
	}
	
	public void addPostUnit(SnakeUnit postUnit) {
		this.postUnit = postUnit;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public Point getPreLocation() {
		return preLocation;
	}
	
	public SnakeUnitForm getSnakeUnitForm() {
		return form;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Direction getPreDirection() {
		return preDirection;
	}
	
	public static Point getNextPosition(Direction direction, Point position) {
		Point nextPosition = new Point();
		switch (direction) {
			case UP: {
				nextPosition.move(position.x, position.y - 1);
				if(isBoundsReached(nextPosition))
					nextPosition.move(position.x, SnakeGame.HEIGHT - 1);
				break;
			}
			case DOWN: {
				nextPosition.move(position.x, position.y + 1);
				if(isBoundsReached(nextPosition))
					nextPosition.move(position.x, 0);
				break;			
			}
			case RIGHT: {
				nextPosition.move(position.x + 1, position.y);
				if(isBoundsReached(nextPosition))
					nextPosition.move(0, position.y);				
				break;
			}
			case LEFT: {
				nextPosition.move(position.x - 1, position.y);
				if(isBoundsReached(nextPosition))
					nextPosition.move(SnakeGame.WIDTH - 1, position.y);
				break;
			}
			//default: throw new Exception(); break;
		}
		return nextPosition;
	}
	
	private static boolean isBoundsReached(Point p) {
		if( (int)p.getX() == SnakeGame.WIDTH  ||
		    (int)p.getY() == SnakeGame.HEIGHT ||
		    (int)p.getX() == -1               ||
		    (int)p.getY() == -1
		  )
			return true;
		return false;
	}
	
	private SnakeUnitForm getUnitForm(Direction direction, Direction preDirection) {
		if(isHead()) {
			if(direction == Direction.UP)
				return SnakeUnitForm.upHead;
			else if(direction == Direction.DOWN)
				return SnakeUnitForm.downHead;
			else if(direction == Direction.RIGHT)
				return SnakeUnitForm.rightHead;
			else if(direction == Direction.LEFT)
				return SnakeUnitForm.leftHead;
		} else if(isTail()) {
			if(direction == Direction.UP)
				return SnakeUnitForm.upTail;
			else if(direction == Direction.DOWN)
				return SnakeUnitForm.downTail;
			else if(direction == Direction.RIGHT)
				return SnakeUnitForm.rightTail;
			else if(direction == Direction.LEFT)
				return SnakeUnitForm.leftTail;
		}
		
		if(preDirection == direction) {
			if(direction == Direction.UP || direction == Direction.DOWN)
				return SnakeUnitForm.verticalBody;
			if(direction == Direction.RIGHT || direction == Direction.LEFT)
				return SnakeUnitForm.horizontalBody;
		} else {
			if( preDirection == Direction.UP    && direction == Direction.RIGHT ||
				preDirection == Direction.LEFT  && direction == Direction.DOWN  )
				return SnakeUnitForm.leftTopCorner;
			if( preDirection == Direction.UP    && direction == Direction.LEFT  ||
				preDirection == Direction.RIGHT && direction == Direction.DOWN  )
				return SnakeUnitForm.rightTopCorner;
			if( preDirection == Direction.DOWN  && direction == Direction.RIGHT ||
				preDirection == Direction.LEFT  && direction == Direction.UP    )
				return SnakeUnitForm.leftBottomCorner;
			if( preDirection == Direction.DOWN  && direction == Direction.LEFT  ||
				preDirection == Direction.RIGHT && direction == Direction.UP    )
				return SnakeUnitForm.rightBottomCorner;
		}
		
		return null;
	}
}
