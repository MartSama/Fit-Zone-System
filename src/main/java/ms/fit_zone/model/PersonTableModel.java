package ms.fit_zone.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonTableModel extends AbstractTableModel {
    private final String[] columNames = {"Name", "Lastaname", "Membership"};
    private final List<Client> clients;

    public PersonTableModel(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = clients.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> client.getName();
            case 1 -> client.getLastname();
            case 2 -> client.getMembership();
            default -> null;
        };
    }
}
