import java.awt.*;
import java.awt.Robot;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
/**
 * @author Leigh Rubin
 * Assignment #10: GameOfLife.java
 * Conway's Game Of Life
 */
public class GameOfLife implements ActionListener, KeyListener
{
    private JFrame f = new JFrame("Game of Life: PRESS \"ENTER\" TO BEGIN");
    private int x = 50;
    private int y = 50;
    JButton[][] cells = new JButton[x][y];
    boolean[][] status = new boolean[x][y];
    private int alive = -1;
    public GameOfLife()
    {

        //f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(x,y));
        f.setVisible(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setLocationRelativeTo(null);
        f.setDefaultLookAndFeelDecorated(true);

        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[i].length; j++)
            {
                JButton temp = new JButton();
                f.add(temp);
                cells[i][j] = temp;
                temp.addActionListener(this);
                temp.addKeyListener(this);
                temp.setBackground(Color.WHITE);
            }
        }

        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        try{
            Thread.sleep(100);
            Robot r = new Robot();
            r.mouseMove(width-60,10);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        }
        catch(Exception e){}
    }

    public void actionPerformed(ActionEvent e)
    {
        Object temp = e.getSource();
        JButton b = (JButton)temp;
        if(b.getBackground() == Color.WHITE)b.setBackground(Color.BLUE);
        else b.setBackground(Color.WHITE);
    }

    public void keyPressed(KeyEvent e) { 
        int c = e.getKeyCode();
        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[i].length; j++)
            {
                if(cells[i][j].getBackground() == Color.BLUE)status[i][j] = true;
                else status[i][j] = false;
            }
        }
        if(c == KeyEvent.VK_ENTER)
        {
            run();
            f.repaint();
            try{
                Robot r = new Robot();
                if(alive != 0){
                    r.keyPress(KeyEvent.VK_ENTER);
                    r.keyRelease(KeyEvent.VK_ENTER);
                }
                Thread.sleep(50);
            }
            catch(Exception x){}
        }
    }

    public void keyReleased(KeyEvent e) { 

    }

    public void keyTyped(KeyEvent e) {

    }

    public void run()
    {
        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[i].length; j++)
            {
                int neighbors = 0;
                for(int k = i - 1; k <= i + 1; k++)
                {
                    for(int l = j - 1; l <= j + 1; l++)
                    {
                        try{if(!(i == k  && j == l) && cells[k][l].getBackground() == Color.BLUE)neighbors++;}
                        catch(Exception e){}
                    }
                }
                if(cells[i][j].getBackground() == Color.WHITE && neighbors == 3)status[i][j] = true;
                else if(neighbors >= 4 || neighbors <= 1)status[i][j] = false;
            }
        }
        for(int i = 0; i < status.length; i++)
        {
            for(int j = 0; j < status[i].length; j++)
            {
                if(status[i][j] == true)cells[i][j].setBackground(Color.BLUE);
                else cells[i][j].setBackground(Color.WHITE);
            }
        }
        alive = 0;
        for(int i = 0; i < status.length; i++)
        {
            for(int j = 0; j < status[i].length; j++)
            {
                if(status[i][j] == true)alive++;   
            }
        }
        int row10 = 0;
        for(int i = 0; i < status.length; i++)if(status[9][i] == true)row10++;
        int column10 = 0;
        for(int i = 0; i < status[9].length; i++)if(status[i][9] == true)column10++;
        f.setTitle("Alive cells: " + alive + ", Alive cells (row 10): " + row10 + ", Alive cells(column 10): " + column10);
    }

    public static void main(String[] args)
    {
        new GameOfLife();
    }
}