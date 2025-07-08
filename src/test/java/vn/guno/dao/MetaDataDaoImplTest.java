package vn.guno.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.guno.model.SchemaMetaData;
import vn.guno.model.TableMetaData;

import static org.junit.Assert.assertNotNull;
//@ComponentScan(basePackages = {"vn.guno.dao"})
//class AppConfig {
//}

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {})
@ActiveProfiles("local")
public class MetaDataDaoImplTest {

    @Autowired
    private MetaDataDao metaDataDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getSchemaMetaData() {
        SchemaMetaData schemaMetaData = metaDataDao.getSchemaMetaData("public");
        assertNotNull(schemaMetaData);
    }

    @Test
    public void getTableMetaData() {
        TableMetaData tableMetaData = metaDataDao.getTableMetaData("public", "tbl_test");
        assertNotNull(tableMetaData);
    }
}