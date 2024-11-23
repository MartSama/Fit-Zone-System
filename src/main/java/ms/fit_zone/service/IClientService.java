package ms.fit_zone.service;

import ms.fit_zone.model.Client;

import java.util.List;

public interface IClientService {
    public List<Client> listClients();
    public Client lookClientForId(Integer idClient);
    public void saveClient(Client client);
    public void deleteClient(Client client);
}
