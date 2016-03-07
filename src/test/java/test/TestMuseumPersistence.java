package test;

import com.ssv.museum.core.AnswerOption;
import com.ssv.museum.persistence.AnswerOptionDAO;
import com.ssv.museum.persistence.MediaDAO;
import com.ssv.museum.persistence.MuseumDAO;
import com.ssv.museum.persistence.QuestionDAO;
import com.ssv.museum.persistence.QuizDAO;
import com.ssv.museum.persistence.TeamDAO;
import com.ssv.museum.persistence.VisitorDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author larssonvictor
 */
@RunWith(Arquillian.class)
public class TestMuseumPersistence {
    
    @Inject
    TestMuseumWrapper tmw;
   
    
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "museum.war")
                // Add all classes
                .addPackage("com.ssv.museum.core")
                .addPackage("com.ssv.museum.persistance")
                .addPackage("service")
                // This will add test-persitence.xml as persistence.xml (renamed)
                // in folder META-INF, see Files > jpa_managing > target > arquillian
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsResource("web-fragment.xml", "META-INF/web-fragment.xml")
                // Must have for CDI to work
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
 
    }
    
    @Before
    public void before() throws Exception{
        //Do something
    }
    
    @Test
    public void testAnswerOption() throws Exception{
        AnswerOption a1 = new AnswerOption("testString");
        tmw.getAnswerOptionDAO().create(a1);
        
        List<AnswerOption> a_Options = tmw.getAnswerOptionDAO().findAll();
        assertTrue(a_Options.size() > 0);
        assertTrue(a_Options.get(0).getText().equals(a1.getText()));
    }
    
        // Need a standalone em to remove testdata between tests
    // No em accessible from interfaces
    @PersistenceContext(unitName = "com.mycompany_Museum_war_1.0-SNAPSHOTPU")
    @Produces
    @Default
    EntityManager em;

    
}
