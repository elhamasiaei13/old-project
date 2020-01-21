package fi.hut.soberit.agilefant.db;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import fi.hut.soberit.agilefant.model.WidgetCollection;
import fi.hut.soberit.agilefant.test.AbstractHibernateTests;

import static org.junit.Assert.*;

@ContextConfiguration
@Transactional
public class WidgetCollectionDAOTest extends AbstractHibernateTests {
    
    @Autowired
    private WidgetCollectionDAO testable;
    
    @Before
    public void setUp_data() {
        executeClassSql();
    }
    
    @Test
    public void getCollections() {
        List<WidgetCollection> list = testable.getCollections();
        
        assertEquals(3, list.size());
        // Check for alphabetical order
        assertEquals("A: Second", list.get(0).getName());
        assertEquals("B: First", list.get(1).getName());
        assertEquals("C: User first", list.get(2).getName());
    }
}
