import entities.User;
import orm.EntityManager;
import orm.config.Connector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Connector.createConnection("root", "Lty#88290", "soft_uni");
        Connection connection = Connector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<User>(connection);
//        userEntityManager.persist(new User("u", "p", 12, LocalDate.now()));

       User first = userEntityManager.findFirst(User.class);
        System.out.println(first);
    }
}
