package com.github.peacetrue.raws;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RawsTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Qualifier("writeDataSource")
    private DataSource writeDataSource;
    @Autowired
    @Qualifier("readDataSource")
    private DataSource readDataSource;

    @Test
    public void test() throws Exception {
        //创建表
        ClassPathResource scheme = new ClassPathResource("scheme.sql");
        ScriptUtils.executeSqlScript(writeDataSource.getConnection(), scheme);
        ScriptUtils.executeSqlScript(readDataSource.getConnection(), scheme);

        //向写库存入数据
        User user = new User(null, "name");
        userRepository.save(user);
        //写库中有一条数据
        ResultSet resultSet = writeDataSource.getConnection().prepareStatement("SELECT * FROM USER").executeQuery();
        Assert.assertTrue(resultSet.next());

        //从读库查询数据，读库中没有数据
        List<User> users = userRepository.findAll();
        Assert.assertEquals(0, users.size());
    }
}