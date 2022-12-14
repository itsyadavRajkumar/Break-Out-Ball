package BreakOutBall;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PlayGame extends JPanel implements KeyListener , ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalrow = 3;
    private int totalcol = 7;
    private int totalbricks = totalcol * totalrow;
    private Timer time;
    private int delay = 8;
    private int playerX = 330 ;   //For Slider Bar
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -3;      //Ball speed X-Axis.
    private int ballYdir = -3;      //Ball speed Y-Axis.
    private BrickGenerator map;


    public PlayGame()
    {
        map = new BrickGenerator(totalrow, totalcol);
        addKeyListener(this);
        setFocusable(true);

        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay , this);
        time.start();
    }

    @Override
    public void paint(Graphics g)
    {
        //Background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        //Drawing Bricks
        map.draw((Graphics2D)g);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif" , Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        //Boarder
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 0, 592);

        //Paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550 , 100, 8);

        //the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20 , 20);

        if (totalbricks == 0)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif" , Font.BOLD, 25));
            g.drawString("You Won : ", 260, 300);
            g.drawString(Integer.toString(score), 390, 300);
            g.setFont(new Font("serif" , Font.BOLD, 25));
            g.drawString("Press Enter to Restart ", 230, 350);
        }

        if (ballposY > 570) // game over
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif" , Font.BOLD, 25));
            g.drawString("Game Over Scores :", 190, 300);
            g.drawString(Integer.toString(score), 410, 300);
            g.setFont(new Font("serif" , Font.BOLD, 25));
            g.drawString("Press Enter to Restart", 230, 350);

        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        time.start();

        if (play)
        {
            if (new Rectangle(ballposX , ballposY , 20, 20).intersects(new Rectangle(playerX, 550, 100, 58))) {
                ballYdir = -ballYdir;
            }
        A:  for (int i = 0 ; i < map.map.length ; i++)
            {
                for (int j = 0 ; j < map.map[0].length ; j++)
                {
                    if (map.map[i][j] > 0 )
                    {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickHeight = map.brickHeight;
                        int brickWidth = map.brickWidth;
                        Rectangle  rect = new Rectangle(brickX , brickY , brickWidth , brickHeight);
                        Rectangle ballRect =  new Rectangle(ballposX , ballposY , 20, 20);
                        Rectangle  brickrect = rect;
                        if (ballRect.intersects(brickrect))
                        {
                            map.setBrickValue(0, i, j);
                            totalbricks--;
                            score += 10;

                            if ( ballposX + Math.random() <= brickrect.x || ballposY + 1 >= brickrect.x + brickrect.width ) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) ballXdir = -ballXdir; // left

            if (ballposY < 0) ballYdir = -ballYdir;  //top

            if (ballposX > 670) ballXdir = -ballXdir;  //right
        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }
    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent e) { // after restart we need to change the speed also
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (playerX >= 600)  playerX = 600;
            else  moveRight();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (playerX < 10) playerX = 10;
            else moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (!play)
            {
                play = true;
                ballposX = 120; // start position
                ballposY = 350; // start position
                ballXdir = -2;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalbricks = totalcol * totalrow;
                map = new BrickGenerator(totalrow , totalcol);

                repaint();
            }
        }
    }

    public void moveRight()
    {
        play = true;
        playerX += 50;
    }

    public void moveLeft()
    {
        play = true;
        playerX -= 50;
    }
}