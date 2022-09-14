package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 1);
            dao.add(c);
            // int id = c.getId();

            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

           // list.get(0).setAge(55);
           // dao.update(list.get(0));


            //List<Client> list1 = dao.getAllByFields(Client.class, Stream.of("name", "age"));
            List<Client> list2 = dao.getAllByFields(Client.class, "id","age");

            List<Client> list3 = dao.getAllByFields(Client.class, "name","age");

           // System.out.println(list1);
            for (Client cli : list2)
                System.out.println(cli);

            for (Client cli : list3)
                System.out.println(cli);

            dao.delete(list.get(0));
        }
    }
}
