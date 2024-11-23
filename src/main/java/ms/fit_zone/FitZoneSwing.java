package ms.fit_zone;

import com.formdev.flatlaf.FlatDarculaLaf;
import io.github.cdimascio.dotenv.Dotenv;
import ms.fit_zone.GUI.FitZoneForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class FitZoneSwing {
    public static void main(String[] args) {
       //Instanciar Spring Fabric
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        FlatDarculaLaf.setup();
        ConfigurableApplicationContext springContext =
                new SpringApplicationBuilder(FitZoneSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
        //Create a Swing object
        SwingUtilities.invokeLater(()-> {
            var fzf = springContext.getBean(FitZoneForm.class);
            fzf.setVisible(true);
        });
    }
}
