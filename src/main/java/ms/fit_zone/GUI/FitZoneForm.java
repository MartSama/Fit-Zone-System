package ms.fit_zone.GUI;

import ms.fit_zone.model.Client;
import ms.fit_zone.service.ClientService;
import ms.fit_zone.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


@Component
public class FitZoneForm extends JFrame{

    private IClientService clientService;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel mainPanel;
    private JTable table1;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearButton;
    private DefaultTableModel tableModelClients;
    private Integer idClient;

    @Autowired
    public FitZoneForm(ClientService clientService){
        this.clientService = clientService;
        initializer();

        List<Client> clients = clientService.listClients();
        saveButton.addActionListener(e -> saveClient());
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fillSelectClient();
            }
        });
        clearButton.addActionListener(e -> {
           cleanForm();
        });
        deleteButton.addActionListener(e -> {
           deleteClient();
        });
    }


    void initializer(){
       setContentPane(mainPanel);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLocationRelativeTo(null);
       setSize(900,700);
    }



    public static void main(String[] args) {

    }

    private void createUIComponents() {
        this.tableModelClients = new DefaultTableModel(0,4){
            //Does not allow to modify clicking the row
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        String[] header = {"ID", "Name", "Lastname", "Membership"};
        this.tableModelClients.setColumnIdentifiers(header);
        this.table1 = new JTable(tableModelClients);
        //Does not allow to select more than 1 row
        this.table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listClients();
    }

    private void listClients(){
       this.tableModelClients.setRowCount(0);
       List<Client> clients = this.clientService.listClients();
       clients.forEach((client) -> {
           Object[] clientRow = {
                   client.getIdClient(),
                   client.getName(),
                   client.getLastname(),
                   client.getMembership()
           };
            this.tableModelClients.addRow(clientRow);
       });
    }

    private void saveClient(){
        var name = textField1.getText();
        var lastname = textField2.getText();
        var membership = textField3.getText();
        if(name.equals("")){
            showMessage("Insert a name");
            textField1.requestFocusInWindow();
            return;
        }else if(membership.equals("")){
            showMessage("Insert a Lastname");
            textField3.requestFocusInWindow();
            return;
        }
        Client client = new Client(this.idClient,name,lastname,Integer.parseInt(membership));
        this.clientService.saveClient(client);
        cleanForm();
        this.listClients();
    }

    private void deleteClient(){
        Client client = new Client();
        client.setIdClient(this.idClient);
        this.clientService.deleteClient(client);
        cleanForm();
        showMessage("Client deleted");
        listClients();
    }

    private void fillSelectClient(){
        var row = table1.getSelectedRow();
        if(row != -1){
            var id = table1.getModel().getValueAt(row,0).toString();
            this.idClient = Integer.parseInt(id);
            var name = table1.getModel().getValueAt(row,1).toString();
            this.textField1.setText(name);
            var lastname = table1.getModel().getValueAt(row,2).toString();
            this.textField2.setText(lastname);
            var membership = table1.getModel().getValueAt(row,3).toString();
            this.textField3.setText(membership);
        }
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }

    private void cleanForm(){
       textField1.setText("");
       textField2.setText("");
       textField3.setText("");
       this.idClient=null;
       this.table1.getSelectionModel().clearSelection();
    }

}


