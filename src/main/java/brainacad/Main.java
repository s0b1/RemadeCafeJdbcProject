package brainacad;

import brainacad.ui.ConsoleApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ConsoleApp app = context.getBean(ConsoleApp.class);
        app.start();
    }
}