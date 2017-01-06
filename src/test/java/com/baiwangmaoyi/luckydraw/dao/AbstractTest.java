package com.baiwangmaoyi.luckydraw.dao;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.baiwangmaoyi.luckydraw.config.TestDataSourceConfig;
import com.baiwangmaoyi.luckydraw.config.CustomContextInitializer;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {CustomContextInitializer.class}, classes = {TestDataSourceConfig.class})
public abstract class AbstractTest {

    public UUID tenantId;
    public UUID userId;
    public static final String DB_SCHEMA = "tenant_user_test";

    @Autowired
    private SqlSessionFactory sqlSession;

    private IDatabaseConnection connection;

    public void initContext(String dataSource, HashMap<String, Object> map) {
        tenantId = UUID.randomUUID();
        userId = UUID.randomUUID();

//        SecurityContextHolder.clearContext();
        //        UserContext userContext = new UserContext(userId, tenantId);
        //        Map<String, String> dataSecurityMap = new HashMap<>();
        //        dataSecurityMap.put(SecurityConstants.FILTER_TYPE_TENANT, "('" + tenantId.toString() + "')");
        //        userContext.setDataSecurityMap(dataSecurityMap);
        //        userContext.setAccessType(AccessType.ACCESS_TYPE_NORMAL);
        //        SecurityContextHolder.setContext(userContext);

        ReplacementDataSet dataSet = new ReplacementDataSet(loadDataSource(dataSource));
        dataSet.addReplacementObject("[tenantId]", tenantId.toString());
        dataSet.addReplacementObject("[userId]", userId.toString());

        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                dataSet.addReplacementObject("[" + entry.getKey() + "]", entry.getValue());
            }
        }
        populateData(dataSet);
    }

    private void populateData(IDataSet dataset) {
        populateData(DatabaseOperation.CLEAN_INSERT, dataset);
    }

    private void populateData(DatabaseOperation operation, IDataSet dataSet) {
        try {
            operation.execute(getConnection(), dataSet);
        } catch (SQLException | DatabaseUnitException e) {
            throw new RuntimeException("Failed to load dataset: " + dataSet, e);
        }
    }

    private FlatXmlDataSet loadDataSource(String dataSource) {
        try {
            return new FlatXmlDataSetBuilder().setColumnSensing(true)
                    .build(new File("src/test/resources/dataset/dao/" + dataSource));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to load dataset: " + e);
        } catch (DataSetException e) {
            throw new RuntimeException("Failed to load dataset: " + e);
        }
    }

    private IDatabaseConnection getConnection() {
        if (connection == null) {
            try {
                connection = new DatabaseConnection(sqlSession.openSession().getConnection(), DB_SCHEMA);
                DatabaseConfig config = connection.getConfig();
                config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
                config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
                config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
                config.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN,"`");
            } catch (Exception e) {
                throw new RuntimeException("Database connection cannot be set up correctly.");
            }
        }
        return connection;
    }

    @After
    public void teardown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Close database connection failed.");
            }
        }
    }

    protected String getRandomData(int length, String suffix) {
        return RandomStringUtils.randomAlphabetic(length) + suffix;
    }

    protected String getRandomData(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    protected String getRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    protected String getRandomEmail(int length) {
        return RandomStringUtils.randomAlphanumeric(length) + "@test.com";
    }

    protected String getRandomPhone(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
