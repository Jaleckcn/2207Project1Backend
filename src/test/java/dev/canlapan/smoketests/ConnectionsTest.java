package dev.canlapan.smoketests;

import dev.canlapan.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionsTest {

    @Test
    void connection_available(){
        Connection connection = ConnectionUtil.createConnection();
        System.out.println(connection);
        Assertions.assertNotNull(connection);
    }
}
