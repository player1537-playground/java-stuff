import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RayCastingTest extends JFrame implements KeyListener, Runnable
{
    Thread thread = new Thread(this);
    Container con = null;
    JLabel title = new JLabel("Ray Casting");
    Font bigFont = new Font("Arial", Font.BOLD, 24);
    Color groundBrown = new Color(100, 50, 30);
    Color skyBlue = new Color(100, 150, 230);
    Color wallColor = Color.gray;
    Graphics offgc;
    Image offscreen = null;
    static final int projectionPlaneWidth = 640;
    static final int projectionPlaneHeight = 400;
    static final int distanceToProjectionPlane = (int)((.5 * projectionPlaneWidth) / Math.tan(Math.PI / 6));
    static final int playerHeight = 64;
    static final int playerSpeed = 20;
    static final int tileWidth = 128;
    static final int wallHeight = 128;
    static final double fieldOfView = Math.PI / 3;
    static final double angleBetweenRays = fieldOfView / projectionPlaneWidth;
    static final int constant = wallHeight * distanceToProjectionPlane;
    double playerPOV = Math.PI + .00111;
    double rayAngle = 0;
    int playerX = 200, playerY = 200;
    boolean first = true;
    boolean isUpPressed = false, isDownPressed = false, isLeftPressed = false, isRightPressed = false;
    static final byte[][] currentMap= {
	{1, 1, 1, 1, 1, 1, 1, 1, 1, 1} , 
	{1, 0, 0, 1, 0, 0, 0, 0, 0, 1} , 
	{1, 0, 0, 1, 0, 1, 1, 0, 0, 1},
	{1, 0, 0, 1, 0, 1, 0, 0, 0, 1} , 
	{1, 0, 1, 0, 0, 0, 0, 1, 1, 1} , 
	{1, 0, 0, 0, 1, 1, 0, 0, 0, 1} , 
	{1, 1, 1, 0, 0, 0, 0, 0, 0, 1} , 
	{1, 0, 0, 0, 0, 0, 0, 1, 0, 1} , 
	{1, 0, 0, 1, 0, 0, 1, 0, 0, 1} , 
	{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    public RayCastingTest()
    {
	setTitle("Ray Casting");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	con = this.getContentPane();
	con.setLayout(new FlowLayout());
	title.setFont(bigFont);
	con.add(title);
	addKeyListener(this);
	thread.start();
    }
    public void update(Graphics gr)
    {
	paint(gr);
    }
    public void paint(Graphics gr)
    {
	if(first)
	    {
		super.paint(gr);
		first = false;
	    }
	offscreen = createImage(projectionPlaneWidth, projectionPlaneHeight);
	offgc = offscreen.getGraphics();
	offgc.setColor(skyBlue);
	offgc.fillRect(0, 0, projectionPlaneWidth, projectionPlaneHeight / 2);
	offgc.setColor(groundBrown);
	offgc.fillRect(0, projectionPlaneHeight / 2, projectionPlaneWidth, projectionPlaneHeight / 2);
	offgc.setColor(Color.gray);
	rayAngle = playerPOV - (Math.PI / 6);
	if(rayAngle < 0)
	    rayAngle = 2 * Math.PI + rayAngle + .001;
	for(int a = 0; a < projectionPlaneWidth; a++)
	    {
		int wallSlice[] = getWallSlice();
		offgc.setColor(wallColor);
		offgc.drawLine(projectionPlaneWidth - a - 1, wallSlice[0], projectionPlaneWidth - a - 1, wallSlice[1]);
	    }
	gr.drawImage(offscreen, 180, 300, this);
    }
    public void run()
    {
	while(true)
	    {
		if (isLeftPressed)
		    {
			playerPOV += .04;
			if(playerPOV >= 2 * Math.PI)
			    playerPOV = 0.01;
		    }
		if (isRightPressed)
		    {
			playerPOV -= .04;
			if(playerPOV <= 0)
			    playerPOV = 1.999 * Math.PI;
		    }
		if (isUpPressed)
		    calculateMovement(true);
		if (isDownPressed)
		    calculateMovement(false);
		repaint();
		try
		    {
			thread.sleep(50);
		    }
		catch(Exception exc)
		    {
			System.out.println("error");
		    }
	    }
    }
    public static void main(String[] args)
    {
	RayCastingTest myFrame = new RayCastingTest();
	myFrame.setSize(1000, 1000);
	myFrame.setVisible(true);
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyPressed(KeyEvent e)
    {
	switch(e.getKeyCode())
	    {
	    case KeyEvent.VK_LEFT:
		isLeftPressed = true;
		break;
	    case KeyEvent.VK_RIGHT:
		isRightPressed = true;
		break;
	    case KeyEvent.VK_UP:
		isUpPressed = true;
		break;
	    case KeyEvent.VK_DOWN:
		isDownPressed = true;
		break;
	    }
    }
    public void keyReleased(KeyEvent e)
    {
	switch(e.getKeyCode())
	    {
	    case KeyEvent.VK_LEFT:
		isLeftPressed = false;
		break;
	    case KeyEvent.VK_RIGHT:
		isRightPressed = false;
		break;
	    case KeyEvent.VK_UP:
		isUpPressed = false;
		break;
	    case KeyEvent.VK_DOWN:
		isDownPressed = false;
		break;
	    }
    }
    public void calculateMovement(boolean fowards)
    {
	double tempAngle = 0;
	int quadrant = (int)(2 * playerPOV / Math.PI);
	int newPlayerX = playerX;
	int newPlayerY = playerY;
	if((fowards) && (quadrant == 0))
	    {
		tempAngle = playerPOV;
		newPlayerX += (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY -= (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((fowards) && (quadrant == 1))
	    {
		tempAngle = Math.PI - playerPOV;
		newPlayerX -= (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY -= (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((fowards) && (quadrant == 2))
	    {
		tempAngle = playerPOV - Math.PI;
		newPlayerX -= (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY += (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((fowards) && (quadrant == 3))
	    {
		tempAngle = 2 * Math.PI - playerPOV;
		newPlayerX += (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY += (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((!fowards) && (quadrant == 2))
	    {
		tempAngle = playerPOV - Math.PI;
		newPlayerX += (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY -= (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((!fowards) && (quadrant == 3))
	    {
		tempAngle = 2 * Math.PI - playerPOV;
		newPlayerX -= (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY -= (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((!fowards) && (quadrant == 0))
	    {
		tempAngle = playerPOV;
		newPlayerX -= (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY += (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((!fowards) && (quadrant == 1))
	    {
		tempAngle = Math.PI - playerPOV;
		newPlayerX += (int)(playerSpeed * Math.cos(tempAngle));
		newPlayerY += (int)(playerSpeed * Math.sin(tempAngle));
	    }
	if((currentMap[(int)((newPlayerX - 5) / tileWidth)][(int)((newPlayerY - 5) / tileWidth)] == 0) && (currentMap[(int)((newPlayerX + 5) / tileWidth)][(int)((newPlayerY - 5) / tileWidth)] == 0) && (currentMap[(int)((newPlayerX - 5) / tileWidth)][(int)((newPlayerY + 5) / tileWidth)] == 0) && (currentMap[(int)((newPlayerX + 5) / tileWidth)][(int)((newPlayerY + 5) / tileWidth)] == 0))
	    {
		playerX = newPlayerX;
		playerY = newPlayerY;
	    }
	repaint();
    }
    public int[] getWallSlice()
    {
	double firstIntersectionX = 0;
	double firstIntersectionY = 0;
	int wallSlice[] = new int[2];
	int horrizontalWallSliceAt[] = {-20000, -20000};
	int verticalWallSliceAt[] = {-20000, -20000};
	double xInterval = 0;
	double yInterval = 0;
	try
	    {
		if((rayAngle < 0.5 * Math.PI) && (rayAngle > 0))
		    {
			firstIntersectionY = (int)(playerY / tileWidth) * tileWidth - 1;
			firstIntersectionX = (int)(playerX + ((playerY - firstIntersectionY) / (Math.tan(rayAngle))));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				horrizontalWallSliceAt[0] = (int)(firstIntersectionX);
				horrizontalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			yInterval = -tileWidth;
			xInterval = (tileWidth / Math.tan(rayAngle));
		    }
		else if((rayAngle < Math.PI) && (rayAngle > 0.5 * Math.PI))
		    {
			firstIntersectionY = (int)(playerY / tileWidth) * tileWidth - 1;
			firstIntersectionX = (int)(playerX - (playerY - firstIntersectionY) / Math.tan(Math.PI - rayAngle));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				horrizontalWallSliceAt[0] = (int)(firstIntersectionX);
				horrizontalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			yInterval = -tileWidth;
			xInterval = -(int)(tileWidth / Math.tan(Math.PI - rayAngle));
		    }
		else if((rayAngle < 1.5 * Math.PI) && (rayAngle > Math.PI))
		    {
			firstIntersectionY = (int)(playerY / tileWidth) * tileWidth + tileWidth;
			firstIntersectionX = (int)(playerX - (firstIntersectionY - playerY) / Math.tan(rayAngle - Math.PI));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				horrizontalWallSliceAt[0] = (int)(firstIntersectionX);
				horrizontalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			yInterval = tileWidth;
			xInterval = -(tileWidth / Math.tan(rayAngle - Math.PI));
		    }
		else if((rayAngle < 2 * Math.PI) && (rayAngle > 1.5 * Math.PI))
		    {
			firstIntersectionY = (int)(playerY / tileWidth) * tileWidth + tileWidth;
			firstIntersectionX = (int)(playerX + (firstIntersectionY - playerY) / Math.tan(2 * Math.PI - rayAngle));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				horrizontalWallSliceAt[0] = (int)(firstIntersectionX);
				horrizontalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			yInterval = tileWidth;
			xInterval = (tileWidth / Math.tan(2 * Math.PI - rayAngle));
		    }
		double previousIntersectionX = firstIntersectionX;
		double previousIntersectionY = firstIntersectionY;
		while((horrizontalWallSliceAt[0] == -20000) || (horrizontalWallSliceAt[1] == -20000))
		    {
			double nextIntersectionX = previousIntersectionX + xInterval;
			double nextIntersectionY = previousIntersectionY + yInterval;
			if(currentMap[(int)(nextIntersectionX / tileWidth)][(int)(nextIntersectionY / tileWidth)] == 1)
			    {
				horrizontalWallSliceAt[0] = (int)(nextIntersectionX);
				horrizontalWallSliceAt[1] = (int)(nextIntersectionY);
			    }
			previousIntersectionX = nextIntersectionX;
			previousIntersectionY = nextIntersectionY;
		    }
	    }
	catch(Exception e)
	    {
		horrizontalWallSliceAt[0] = -20000;
		horrizontalWallSliceAt[1] = -20000;
	    }
	try
	    {
		if((rayAngle < 0.5 * Math.PI))
		    {
			firstIntersectionX = (int)(playerX / tileWidth) * tileWidth + tileWidth;
			firstIntersectionY = (int)(playerY - (firstIntersectionX - playerX) * Math.tan(rayAngle));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				verticalWallSliceAt[0] = (int)(firstIntersectionX);
				verticalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			xInterval = tileWidth;
			yInterval = -(tileWidth * Math.tan(rayAngle));
		    }
		else if((rayAngle < Math.PI) && (rayAngle > 0.5 * Math.PI))
		    {
			firstIntersectionX = (int)(playerX / tileWidth) * tileWidth - 1;
			firstIntersectionY = (int)(playerY - (playerX - firstIntersectionX) * Math.tan(Math.PI - rayAngle));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				verticalWallSliceAt[0] = (int)(firstIntersectionX);
				verticalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			xInterval = -tileWidth;
			yInterval = -(tileWidth * Math.tan(Math.PI - rayAngle));
		    }
		else if((rayAngle < 1.5 * Math.PI) && (rayAngle > Math.PI))
		    {
			firstIntersectionX = (int)(playerX / tileWidth) * tileWidth - 1;
			firstIntersectionY = (int)(playerY + (playerX - firstIntersectionX) * Math.tan(rayAngle - Math.PI));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				verticalWallSliceAt[0] = (int)(firstIntersectionX);
				verticalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			xInterval = -tileWidth;
			yInterval = (tileWidth * Math.tan(rayAngle - Math.PI));
		    }
		else if((rayAngle < 2 * Math.PI) && (rayAngle > 1.5 * Math.PI))
		    {
			firstIntersectionX = (int)(playerX / tileWidth) * tileWidth + tileWidth;
			firstIntersectionY = (int)(playerY + (firstIntersectionX - playerX) * Math.tan(2 * Math.PI - rayAngle));
			if(currentMap[(int)(firstIntersectionX / tileWidth)][(int)(firstIntersectionY / tileWidth)] == 1)
			    {
				verticalWallSliceAt[0] = (int)(firstIntersectionX);
				verticalWallSliceAt[1] = (int)(firstIntersectionY);
			    }
			xInterval = tileWidth;
			yInterval = tileWidth * Math.tan(2 * Math.PI - rayAngle);
		    }
		double previousIntersectionX = firstIntersectionX;
		double previousIntersectionY = firstIntersectionY;
		while((verticalWallSliceAt[0] == -20000) || (verticalWallSliceAt[1] == -20000))
		    {
			double nextIntersectionX = previousIntersectionX + xInterval;
			double nextIntersectionY = previousIntersectionY + yInterval;
			if(currentMap[(int)(nextIntersectionX / tileWidth)][(int)(nextIntersectionY / tileWidth)] == 1)
			    {
				verticalWallSliceAt[0] = (int)(nextIntersectionX);
				verticalWallSliceAt[1] = (int)(nextIntersectionY);
			    }
			previousIntersectionX = nextIntersectionX;
			previousIntersectionY = nextIntersectionY;
		    }
	    }
	catch(Exception e)
	    {
		verticalWallSliceAt[0] = -20000;
		verticalWallSliceAt[1] = -20000;
	    }
	int distanceToHorrizontal = (int)((Math.sqrt(((playerX - horrizontalWallSliceAt[0]) * (playerX - horrizontalWallSliceAt[0])) + ((playerY - horrizontalWallSliceAt[1]) * (playerY - horrizontalWallSliceAt[1])))) * Math.cos(playerPOV - rayAngle));
	int distanceToVertical = (int)((Math.sqrt(((playerX - verticalWallSliceAt[0]) * (playerX - verticalWallSliceAt[0])) + ((playerY - verticalWallSliceAt[1]) * (playerY - verticalWallSliceAt[1])))) * Math.cos(playerPOV - rayAngle));
	if(distanceToHorrizontal > distanceToVertical)
	    wallColor = Color.gray;
	else
	    wallColor = Color.darkGray;
	rayAngle = rayAngle + angleBetweenRays;
	if(rayAngle > 2 * Math.PI)
	    rayAngle = .001;
	int lengthOfWallSlice = (int)(constant / (Math.min(distanceToHorrizontal, distanceToVertical) + .0001));
	if(lengthOfWallSlice > projectionPlaneHeight - 1)
	    lengthOfWallSlice = projectionPlaneHeight - 1;
	if(lengthOfWallSlice < 0)
	    lengthOfWallSlice = 0;
	wallSlice[0] = (int)(projectionPlaneHeight - lengthOfWallSlice) / 2;
	wallSlice[1] = wallSlice[0] + lengthOfWallSlice;
	return wallSlice;
    }
}