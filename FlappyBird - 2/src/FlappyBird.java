import  javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int frameWidth = 360;
    int frameHeight = 640;

    // image attribute
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // player
    int playerStartPosX = frameWidth/8;
    int playerStartPosY = frameHeight/2;
    int playerWidth = 34;
    int playerHeight = 24;

    Player player;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // Buat Timer
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    boolean gameOver = false;

    int score = 0;
    JLabel scoreLabel;


    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(360,640));
        setFocusable(true);
        addKeyListener(this);
        //setBackground(Color.blue);

        // score
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        this.setLayout(null); // manual positioning
        scoreLabel.setBounds(10, 10, 200, 30);
        this.add(scoreLabel);


        // load image
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pipa");
                placePipes();
            }
        });
        pipesCooldown.start(); // <- ini yang kurang

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

    }

    // Tampilkan player melalui method draw()
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // Cek jatuh ke bawah (Game Over)
        if (player.getPosY() + player.getHeight() > frameHeight) {
            gameOver("Game Over! Kamu jatuh.");
            return;
        }


        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // Cek apakah gameover atau tidak
            if (player.getBounds().intersects(pipe.getBounds())) {
                gameOver("Game Over! Menabrak pipa.");
                return;
            }

            if (!pipe.isPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.setPassed(true);
                if (i % 2 == 0) { // hanya tambahkan skor di pipa atas saja, biar nggak double
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
            }
            return; // jangan loncat kalau game over
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.setVelocityY(-10);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

    public void placePipes(){
        int randomPosY = (int)(pipeStartPosY - pipeHeight/4 - Math.random() * pipeHeight/2);
        int openingSpace = frameHeight/4;

        Pipe upperpipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperpipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void gameOver(String message) {
        gameLoop.stop();
        pipesCooldown.stop();
        gameOver = true;

        JOptionPane.showMessageDialog(this, message + "\nTekan R untuk restart.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public void restartGame() {
        // reset posisi player
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // reset pipa
        pipes.clear();

        // mulai lagi timernya
        gameOver = false;
        gameLoop.start();
        pipesCooldown.start();

        score = 0;
        scoreLabel.setText("Score: 0");

    }



}

