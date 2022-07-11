import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.AllPermission;
import java.util.Random;

public class GameFielf extends JPanel implements ActionListener {

    private final int SIZE = 320;//размер поля
    private final int DOT_SIZE = 16;//размер игровой клеточки
    private final int ALL_DOTS = 400;//сколько может быть клеточек
    private Image dot;
    private Image apple;
    private int appleX, appleY;//координаты яблочка
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left,down = false;
    private boolean right, up, inGame = true;
    private int score;
    public GameFielf(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new KeyFieldListener());
        setFocusable(true);

    }

    public void initGame(){//начало игры
        dots = 3;
        for(int i =0; i<dots; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;

        }
        timer = new Timer(250,this);
        timer.start();
        createApple();

    }

    public void createApple(){//создаем яблочко
        appleX = new Random().nextInt(20)*DOT_SIZE;
        if(appleX==x[0]){
            createApple();
        }
        appleY= new Random().nextInt(20)*DOT_SIZE;
        if(appleY==y[0]){
            createApple();
        }

    }
    public void loadImages(){
        ImageIcon lia = new ImageIcon("burn.png");
        apple = lia.getImage();
        ImageIcon dotsLoad = new ImageIcon("dots.png");
        dot = dotsLoad.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){


            for (int i = 0; i < dots; i++) {

                g.drawImage(dot,x[i],y[i],this);
            }
            g.drawImage(apple,appleX,appleY,this);
        }
        else {
            String str = "Game Over\n Ваш счет: "+score;
            //Font f = new Font("Arial" , 14, Font.BOLD);
            g.setColor(Color.WHITE);
           // g.setFont(f);
            g.drawString(str,125,SIZE/2);


        }
    }

    public void move(){
        for (int i = dots; i >0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];


        }
        if(left){
            x[0] -=DOT_SIZE;

        }
        if(right){
            x[0] +=DOT_SIZE;
        }
        if(up){
            y[0]-=DOT_SIZE;

        }
        if(down){
            y[0]+= DOT_SIZE;
        }
    }
    public void checkApple(){
        if(x[0]==appleX && y[0] == appleY){
            dots++;
            score++;

            createApple();
        }
    }
    private void addrestartButton(){
        String buttonRestartText = "Restart";
        JButton restartButton = new JButton(buttonRestartText);
        restartButton.setBounds(150, 200, 30,30);
    restartButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    });
    }

    public void checkCollision(){
        for (int i = dots; i >0; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }

            if(x[0]>SIZE){
                inGame=false;
            }
            if(x[0]<0){
                inGame=false;
            }
            if(y[0]>SIZE){
                inGame=false;
            }
            if(y[0]<0){
                inGame=false;
            }
        }
    }
    public void actionPerformed(ActionEvent e){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public void reset(){
        inGame = true;
        dots = 3;

    }
    class KeyFieldListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                right= false;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                right= false;
                left = false;
            }
        }
    }
}
