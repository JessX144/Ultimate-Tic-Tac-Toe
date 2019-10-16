import players.Player;
import players.impl.RandomPlayer;

import java.util.Scanner;

// javac -cp . ./Run_Player.java -d ../dist
// java Run_Player
public class Run_Player {

    public static void main(String[] args) {
        Player player = new RandomPlayer();

        try (Scanner scanner = new Scanner(System.in)){
            while (true) {
                if (scanner.hasNext()) {
                    String message = scanner.nextLine();
                    player.processInput(message);
                } else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
