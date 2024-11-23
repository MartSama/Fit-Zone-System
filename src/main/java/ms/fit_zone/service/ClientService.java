package ms.fit_zone.service;

import ms.fit_zone.model.Client;
import ms.fit_zone.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService{
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client lookClientForId(Integer idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }
}
