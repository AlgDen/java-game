package fr.sakad.chernogame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

public class Game extends Canvas implements Runnable {

    // static links this field to the class
    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;
    public static String title = "Rain";

    private Thread thread;
    private JFrame frame;
    private boolean running = false;

    private Screen screen;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();



    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        screen = new Screen(width, height);

        frame = new JFrame();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double nsBtwUpdate = 1_000_000_000.0 / 60.0;
        int ups = 0;
        int fps = 0;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsBtwUpdate;
            lastTime = now;

            while(delta >= 1) {
                update();
                ups++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frame.setTitle(title + " | ups: " + ups + ", " + "fps: " + fps);
                ups = 0;
                fps = 0;
            }

            render();
            fps++;
        }
    }

    public void update() {}
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        screen.render();

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setTitle(Game.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setResizable(false);
        game.frame.setLocationRelativeTo(null);
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setVisible(true);

        game.start();
    }
}
