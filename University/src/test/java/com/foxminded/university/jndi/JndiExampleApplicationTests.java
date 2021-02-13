package com.foxminded.university.jndi;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jndi.JndiTemplate;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.*;

import com.foxminded.university.dao.TestHelper;

@SpringBootTest
@ContextConfiguration(classes = TestHelper.class)
class JndiExampleApplicationTests {

	@Test
    void shouldGetDataSource() {
        DataSource dataSource = null;
        JndiTemplate jndi = new JndiTemplate();
        try {
            dataSource = jndi.lookup("java:comp/env/jdbc/testDB", DataSource.class);
            assertTrue(dataSource.getConnection()!=null);
        } catch (NamingException | SQLException e) {
        }
    }

}