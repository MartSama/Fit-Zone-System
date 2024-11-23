package ms.fit_zone;

import io.micrometer.observation.GlobalObservationConvention;
import jakarta.persistence.criteria.CriteriaBuilder;
import ms.fit_zone.model.Client;
import ms.fit_zone.service.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;
import java.util.Scanner;


//@SpringBootApplication
public class FitZoneApplication implements CommandLineRunner {

	@Autowired
	private IClientService clientService;

	private static final Logger logger = LoggerFactory.getLogger(FitZoneApplication.class);

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
		logger.info("Starting applicatino");

		SpringApplication.run(FitZoneApplication.class, args);
		logger.info("Exiting application");
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner console = new Scanner(System.in);
		logger.info("---Application FIT ZONE---");
		boolean exit = false;
		while(!exit){

			logger.info("""
					Select One of the Following options
					1.- Show Clients
					2.-	Look Client for Id
					3.- Add Client
					4.- Update Client
					5.- Delete Client
					6.- Exit 
					Enter your option: \s
					""");
			var option = console.nextLine();
			switch (option){
				case "1" -> {
					List<Client> clients = clientService.listClients();
					clients.forEach((item) -> logger.info(item.toString()));
				}
				case "2" ->	{
					logger.info("Enter the id-> ");
					var id = Integer.parseInt(console.nextLine());
					var client = clientService.lookClientForId(id);
                    logger.info("Client: {}", client);
				}

				case "3" -> {
					logger.info("Enter the next information");
					logger.info("Name -> ");
					String name = console.nextLine();
					logger.info("Lastname -> ");
					String lastname = console.nextLine();
					logger.info("Membership -> ");
					Integer membership = Integer.parseInt(console.nextLine());
					Client client = new Client(null,name,lastname,membership);
					clientService.saveClient(client);
                    logger.info("Client added {}", client);
				}

				case "4" -> {
					logger.info("Enter the id of the client: ");
					Integer id = Integer.parseInt(console.nextLine());
					logger.info("Name -> ");
					String name = console.nextLine();
					logger.info("Lastname -> ");
					String lastname = console.nextLine();
					logger.info("Membership -> ");
					Integer membership = Integer.parseInt(console.nextLine());
					Client client = new Client(id,name,lastname,membership);
					clientService.saveClient(client);
					logger.info("Client updated {}", client);
				}

				case "5" -> {
					logger.info("Enter the id of the client to delete");
					Integer id = Integer.parseInt(console.nextLine());
					Client client = new Client();
					client.setIdClient(id);
					clientService.deleteClient(client);
					logger.info("Client deleted");
				}
				case "6" -> exit = true;
				default -> logger.info("Invalid option");
			}

		}
	}

}
