package backend.ejb;

import backend.ejb.DishEJB;
import backend.entity.Dish;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.After;
import org.junit.Before;
import util.DeleterEJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Jacob on 07.06.2017.
 */

@RunWith(Arquillian.class)
public class DishEJBTest {


    @EJB
    private DishEJB dishEJB;

    @EJB
    private DeleterEJB deleterEJB;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "backend")
                .addClasses(DishEJB.class, DeleterEJB.class)
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @Before
    @After
    public void emptyDatabase(){
        deleterEJB.deleteEntities(Dish.class);
    }


    @Test
    public void testCreateDish(){
        assertEquals(0, dishEJB.getAllDishes().size());
        String dishName = "pizza";
        String dishDescripion = "Grandiosa";

        boolean created = dishEJB.createDish(dishName, dishDescripion);
        assertTrue(created);
        assertEquals(1, dishEJB.getAllDishes().size());

    }

    @Test
    public void testCreateTwoDishes(){
        assertEquals(0, dishEJB.getAllDishes().size());
        String dishName = "pizza";
        String dishName2 = "Spaghetti bolognese";
        String dishDescripion = "Grandiosa";
        String dishDescripion2 = "Spaghetti with bolognese sauce";

        dishEJB.createDish(dishName, dishDescripion);
        dishEJB.createDish(dishName2, dishDescripion2);

        assertEquals(2, dishEJB.getAllDishes().size());
    }
}
