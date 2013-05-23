import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Set your own class name                                                                        
public class MouseAngleTest extends JApplet
    implements KeyListener, MouseListener, MouseMotionListener
{
    // Your variables                                                                               
    Point mouseCoord;
    double xCenter, yCenter;
    Image dotImage;
    double angle;
    boolean mouseOnRight;

    boolean leftPressed, rightPressed, downPressed, upPressed; // Keys you want to respond to       
    Graphics backBuffer;
    Image bufferImage;
    Timer myTimer;
    boolean hasBeenInInit = false;

    public void init()
    {
	// Your stuff to be initialized                                                               
	dotImage  = getImage(getCodeBase(), "Earth.jpg");
	yCenter = getHeight() / 2;
	xCenter = getWidth() / 2;
	mouseCoord = new Point();

	hasBeenInInit = true;
	bufferImage = createImage(getWidth(), getHeight());
	backBuffer = bufferImage.getGraphics();
	myTimer = new Timer(15, updateStuff);
	myTimer.start();
	addMouseListener(this);
	addKeyListener(this);
	addMouseMotionListener(this);
    }
    public void paint(Graphics g)
    {
	if (!hasBeenInInit)
	    init();
	backBuffer.fillRect(0,0,500,500);
	// Your stuff to be drawn to backBuffer                                                       
	drawDot(angle, 99, backBuffer, mouseOnRight);

	g.drawImage(bufferImage,0,0,this);
    }
    public void update(Graphics g)
    {
	paint(g);
    }
    ActionListener updateStuff = new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		if (!hasBeenInInit)
		    init();
		// Your stuff to be updated                                                                 
		angle = getAngle(mouseCoord.getX(), mouseCoord.getY());


		repaint();
	    }
	};
    public void keyPressed(KeyEvent evt)
    {
	int key = evt.getKeyCode();

	// Your keys to be responded to                                                               
	if (key == KeyEvent.VK_LEFT)
	    leftPressed = true;
	if (key == KeyEvent.VK_DOWN)
	    downPressed = true;
	if (key == KeyEvent.VK_RIGHT)
	    rightPressed = true;
    }
    public void keyReleased(KeyEvent evt)
    {
	int key = evt.getKeyCode();

	// Same keys, but now set each boolean to false                                               
	if (key == KeyEvent.VK_LEFT)
	    leftPressed = false;
	if (key == KeyEvent.VK_DOWN)
	    downPressed = false;
	if (key == KeyEvent.VK_RIGHT)
	    rightPressed = false;
	if (key == KeyEvent.VK_UP)
	    upPressed = false;

    }
    public double getAngle(double x, double y)
    {
	double xDiff = x - xCenter;
	double yDiff = y - yCenter;
	double retAngle;
	try
	    {
		retAngle = Math.atan(yDiff / xDiff);
	    }
	catch (Exception e)
	    {
		retAngle = Math.atan(yDiff);
	    }
	return retAngle;
    }
    public void drawDot(double angle, double length, Graphics g, boolean onRight)
    {
	double xMove = Math.cos(angle) * length;
	double yMove = Math.sin(angle) * length;
	if (!onRight)
	    {
		xMove *= -1;
		yMove *= -1;
	    }
	g.drawImage(dotImage,(int) (xCenter + xMove),(int) (yCenter + yMove), 50, 50, this);
    }
    public void mousePressed(MouseEvent evt)
    {
	requestFocus();
    }
    public void mouseMoved(MouseEvent me)
    {
	mouseCoord = me.getPoint();
	mouseOnRight = me.getXOnScreen() > xCenter;
    }
    public void mouseDragged(MouseEvent me)
    {
	mouseMoved(me);
    }
    public void mouseEntered(MouseEvent evt) { }  // Required by the                                
    public void mouseExited(MouseEvent evt) { }   //    MouseListener                               
    public void mouseReleased(MouseEvent evt) { } //       interface.                               
    public void mouseClicked(MouseEvent evt) { }
    public void keyTyped(KeyEvent evt) { } // Required by KeyListener                               
}
