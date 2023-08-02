package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import org.junit.experimental.theories.Theories;

import java.awt.*;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};
    private Font largeFont = new Font("sans", Font.BOLD, 48);
    private Font normalFont = new Font("serif", Font.BOLD, 24);
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        char[] array = new char[n];
        for (int i = 0; i < n; ++i) {
            array[i] = CHARACTERS[rand.nextInt(CHARACTERS.length)];
        };
        return new String(array);
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.setFont(normalFont);
        if (!gameOver) {
            StdDraw.line(0, 35, 40, 35);
            StdDraw.text(30, 37.5, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.text(10, 37.5, "Round " + round);
            if (playerTurn) {
                StdDraw.text(20, 37.5, "Spell!");
            } else {
                StdDraw.text(20, 37.5, "Watch!");
            }
        }

        StdDraw.setFont(largeFont);
        StdDraw.text(20, 20, s);

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(String.valueOf(letters.charAt(i)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            drawFrame("");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
    }

    public String solicitNCharsInput(int n) {
        char[] array = new char[n];
        for (int i = 0; i < n; ++i) {
            while (!StdDraw.hasNextKeyTyped()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
            array[i] = StdDraw.nextKeyTyped();
            drawFrame(String.valueOf(array));

        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        return String.valueOf(array);
    }

    public void startGame() {
        round = 1;
        gameOver = false;
        playerTurn = false;
        while (true) {
            drawFrame("Round " + round + "! Good luck!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            String str = generateRandomString(round);
            flashSequence(str);
            playerTurn = true;
            drawFrame("");
            String ans = solicitNCharsInput(round);
            playerTurn = false;
            if (ans.equals(str)) {
                drawFrame("Correct!");
            } else {
                gameOver = true;
                drawFrame("You lose!");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            ++round;
        }
    }

}
