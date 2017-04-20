package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.BonusPoint;
import main.SnakeUnitForm;
import test.Snake.Direction;

public class SnakeGame extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6478444653084756616L;

	protected static JFrame f;
	
	public static BufferedImage snakeSprite;
	public static BufferedImage horizontalBody;
	public static BufferedImage verticalBody;
	public static BufferedImage leftTopCorner;
	public static BufferedImage rightTopCorner;
	public static BufferedImage leftBottomCorner;
	public static BufferedImage rightBottomCorner;
	public static BufferedImage leftHead;
	public static BufferedImage rightHead;
	public static BufferedImage upHead;
	public static BufferedImage downHead;
	public static BufferedImage leftTail;
	public static BufferedImage rightTail;
	public static BufferedImage upTail;
	public static BufferedImage downTail;
	public static BufferedImage apple;
	
	
	
	public static final int SCALE = 32;
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	public static double SPEED = 5;

	
	Timer t = new Timer((int) (1000 / SPEED), this);
	
	BonusPoint bonusPoint = new BonusPoint(new Point(0, 0));
	Snake snake = new Snake(bonusPoint);
	
	
	public SnakeGame() {
		try {
			snakeSprite = ImageIO.read(new File("res/images/snake-graphics.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		horizontalBody = snakeSprite.getSubimage(64, 0, 64, 64);
		verticalBody = snakeSprite.getSubimage(2*64, 64, 64, 64);
		leftTopCorner = snakeSprite.getSubimage(0, 0, 64, 64);
		rightTopCorner = snakeSprite.getSubimage(2*64, 0, 64, 64);
		leftBottomCorner = snakeSprite.getSubimage(0, 64, 64, 64);
		rightBottomCorner = snakeSprite.getSubimage(2*64, 2*64, 64, 64);
		leftHead = snakeSprite.getSubimage(3*64, 64, 64, 64);
		rightHead = snakeSprite.getSubimage(4*64, 0, 64, 64);
		upHead = snakeSprite.getSubimage(3*64, 0, 64, 64);
		downHead = snakeSprite.getSubimage(4*64, 64, 64, 64);
		leftTail = snakeSprite.getSubimage(3*64, 3*64, 64, 64);
		rightTail = snakeSprite.getSubimage(4*64, 2*64, 64, 64);
		upTail = snakeSprite.getSubimage(3*64, 2*64, 64, 64);
		downTail = snakeSprite.getSubimage(4*64, 3*64, 64, 64);
		apple = snakeSprite.getSubimage(0, 3*64, 64, 64);
		t.start();
		addKeyListener(new Keyboard());
		setFocusable(true);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		g.setColor(Color.LIGHT_GRAY);
		for(int xx = 0; xx < WIDTH * SCALE; xx += SCALE) {
			g.drawLine(xx, 0, xx, HEIGHT * SCALE);
		}
		for(int yy = 0; yy < HEIGHT * SCALE; yy += SCALE) {
			g.drawLine(0, yy, WIDTH * SCALE, yy);
		}
		
		g.drawImage( apple,
				 bonusPoint.getLocation().x * SnakeGame.SCALE,
				 bonusPoint.getLocation().y * SnakeGame.SCALE,
				 SnakeGame.SCALE,
				 SnakeGame.SCALE,
				 null);
		
		ArrayList<SnakeUnit> body = snake.getBody();
		//g.setColor(Color.CYAN);
		
		for(int i = 0; i < body.size(); i++) {
			SnakeUnit currentUnit = body.get(i);
			
			/*g.fillRect(	currentUnit.getLocation().x * SnakeGame.SCALE + 3, 
						currentUnit.getLocation().y * SnakeGame.SCALE + 3, 
						SnakeGame.SCALE - 5, 
						SnakeGame.SCALE - 5	);*/
			g.drawImage( getSnakeUnitImage(currentUnit.getSnakeUnitForm()),
						 currentUnit.getLocation().x * SnakeGame.SCALE,
						 currentUnit.getLocation().y * SnakeGame.SCALE,
						 SnakeGame.SCALE,
						 SnakeGame.SCALE,
						 null);
		}
		
		/*g.setColor(Color.MAGENTA);
		g.fillRect( bonusPoint.getLocation().x * SnakeGame.SCALE + 3,
				    bonusPoint.getLocation().y * SnakeGame.SCALE + 3,
				    SnakeGame.SCALE - 5,
				    SnakeGame.SCALE - 5 );*/
		
		

		if(snake.isCrashed()) {
			g.setFont(new Font("Dialog", Font.BOLD, 26));
			g.drawString("Press space to restart", WIDTH * SCALE / 2 - 4*SCALE, HEIGHT * SCALE / 2 - SCALE);
		}
		
		g.setFont(new Font("Dialog", Font.PLAIN, 14));
		g.drawString("SCORE: " + snake.getScore(), WIDTH * SCALE - 3*SCALE, SCALE / 2);
	
	}
	
	private BufferedImage getSnakeUnitImage(SnakeUnitForm form) {
		switch(form) {
			case horizontalBody : return horizontalBody;
			case verticalBody : return verticalBody;
			case leftTopCorner : return leftTopCorner;
			case rightTopCorner : return rightTopCorner;
			case leftBottomCorner : return leftBottomCorner;
			case rightBottomCorner : return rightBottomCorner;
			case leftHead : return leftHead;
			case rightHead : return rightHead;
			case upHead : return upHead;
			case downHead : return downHead;
			case leftTail : return leftTail;
			case rightTail : return rightTail;
			case upTail : return upTail;
			case downTail : return downTail;
			default : return null;
		}
	}
	
	public static void main(String[] args) {
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setSize(WIDTH * SCALE + 7, HEIGHT * SCALE + 30);
		f.setLocation(300, 50);
		f.getContentPane().add(new SnakeGame());
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		snake.move();
		
			
		repaint();
	}
	
	private class Keyboard extends KeyAdapter {
		public void keyPressed(KeyEvent kEvt) {
			int key = kEvt.getKeyCode();
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				snake.setDirection(Direction.UP);
			} else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				snake.setDirection(Direction.DOWN);
			} else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				snake.setDirection(Direction.RIGHT);
			} else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				snake.setDirection(Direction.LEFT);
			} else if(key == KeyEvent.VK_F) {
				SPEED ++;
				//System.out.println(SPEED);
				t.stop();
				t = new Timer((int) (1000 / SPEED), SnakeGame.this);
				t.start();
			} else if(key == KeyEvent.VK_G) {
				SPEED --;
				//System.out.println(SPEED);
				t.stop();
				t = new Timer((int) (1000 / SPEED), SnakeGame.this);
				t.start();
			} else if(key == KeyEvent.VK_E) {
				snake.addUnit(snake.getBody().get(snake.getBody().size() - 1));
			} else if(key == KeyEvent.VK_R) {
				snake.removeUnit();
			} else if(key == KeyEvent.VK_SPACE) {
				f.dispose();
				f = new JFrame();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setResizable(false);
				f.setLocationRelativeTo(null);
				f.setSize(WIDTH * SCALE + 7, HEIGHT * SCALE + 30);
				f.setLocation(300, 50);
				f.getContentPane().add(new SnakeGame());
				f.setVisible(true);
			}
		}
	}
	
	
	@SuppressWarnings("unused")
	private Graphics2D gradientUnit(Graphics2D g, int x, int y, int width, int height) {
		GradientPaint primary = new GradientPaint(
                0f, 0f, Color.WHITE, 200f, 0f, Color.ORANGE);
        GradientPaint shade = new GradientPaint(
                0f, 0f, new Color(0, 0, 0, 0),
                0f, 200f, new Color(0, 0, 0, 255));
        g.setPaint(primary);
        g.fillRect(x, y, width, height);
        g.setPaint(shade);
        g.fillRect(x, y, width, height);
        return g;
	}
}