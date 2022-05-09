package xyz.iconc.dev.ExaTechUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.iconc.dev.ExaTechUtils.bot.Bot;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class Controller {
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    public static void main(String[] args) {
        Bot bot = new Bot();
        Config config = new Config();


        Scanner scanner = new Scanner(System.in);

        Thread thread = new Thread(() -> {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("quit")) {
                    Runtime.getRuntime().exit(0);
                }
            }
        });

        thread.start();

        config.loadConfig();

        try {
            bot.initializeBot(config.getConfigObject().getAPI_TOKEN());
        } catch (LoginException e) {
            logger.error(e.toString());
        }
    }

    public static void ExitApplication(int status) {
        Runtime.getRuntime().exit(status);
    }
}
