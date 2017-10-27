package backend.ejb;

import backend.entity.Dish;
import backend.entity.Menu;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.DeleterEJB;

import javax.ejb.EJB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 * Created by Jacob on 08.06.2017.
 */
@RunWith(Arquillian.class)
public class MenuEJBTest{

    @EJB
    private MenuEJB menuEJB;

    @EJB
    private DishEJB dishEJB;

    @EJB
    private DeleterEJB deleterEJB;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    Menu menu = new Menu();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "backend")
                .addClasses(DeleterEJB.class)
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @Before
    @After
    public void emptyDatabase() {
        deleterEJB.deleteEntities(Menu.class);
    }

    private void createMenu(String dateString, List<Dish> dayMenu){
        menuEJB.createMenu(dateString, dayMenu);
    }

    private void createMenuWithDish(String dishName, String description, String dateString){
        dishEJB.createDish(dishName, description);
        Dish dish = dishEJB.getDish(dishName);
        List<Dish> dayMenu = menu.getDayMenu();
        dayMenu.add(dish);
        menuEJB.createMenu(dateString, dayMenu);
    }

    @Test
    public void testNoMenues(){

        List<Menu> list = menuEJB.getAllMenues();

        assertEquals(0, list.size());
    }

    @Test
    public void testCheckDate(){
        dishEJB.createDish("Pizza", "Grandiosa");
        Dish dish = dishEJB.getDish("Pizza");

        List<Dish> dayMenu = menu.getDayMenu();
        dayMenu.add(dish);
        createMenu("2017-08-06", dayMenu);
        assertEquals("2017-08-06",menuEJB.getDate("2017-08-06"));
    }

    @Test(expected = javax.ejb.EJBException.class)
    public void testCreateMenuWithNoDish(){
        List<Dish> dayMenu = menu.getDayMenu();
        createMenu("2017-08-06", dayMenu);
    }

    @Test
    public void testCreateMenuWithDish(){

        dishEJB.createDish("Pizza", "Grandiosa");
        Dish dish = dishEJB.getDish("Pizza");

        List<Dish> dayMenu = menu.getDayMenu();
        dayMenu.add(dish);
        createMenu("2017-08-06", dayMenu);
        assertEquals(1, menuEJB.getAllMenues().size());;
    }

    @Test
    public void testCheckValidFormat(){
        boolean valid = MenuEJB.isValidFormat("2017-08-06");
        assertTrue(valid);
    }

    @Test
    public void testGetCurrentMenu(){
        dishEJB.createDish("Pizza", "Grandiosa");
        Dish dish = dishEJB.getDish("Pizza");

        List<Dish> dayMenu = menu.getDayMenu();
        dayMenu.add(dish);
        createMenu("2017-06-09", dayMenu);

        List<Dish> menuList = menuEJB.getCurrentListMenu();
        assertEquals(1, menuList.size());
    }

    @Test
    public void testGetNextMenu(){
        dishEJB.createDish("Pizza", "Grandiosa");
        Dish dish = dishEJB.getDish("Pizza");

        List<Dish> dayMenu = menu.getDayMenu();
        dayMenu.add(dish);
        createMenu("2017-06-09", dayMenu);
        boolean nextMenu = menuEJB.getNextListMenu("2017-06-08");
        assertTrue(nextMenu);
    }

    @Test
    public void testGetAbsentNextMenu(){
        boolean nextMenu = menuEJB.getNextListMenu("2017-06-08");
        assertFalse(nextMenu);
    }

    @Test
    public void testGetAbsentPreviousMenu(){
        boolean previousMenu = menuEJB.getPreviousListMenu("2017-06-08");
        assertFalse(previousMenu);
    }

    @Test
    public void testGetPreviousMenu(){
        dishEJB.createDish("Pizza", "Grandiosa");
        Dish dish = dishEJB.getDish("Pizza");

        List<Dish> dayMenu = menu.getDayMenu();
        dayMenu.add(dish);
        createMenu("2017-06-07", dayMenu);
        boolean previousMenu = menuEJB.getPreviousListMenu("2017-06-08");
        assertTrue(previousMenu);
    }

    @Test
    public void testThreeMenus(){
        createMenuWithDish("Pizza", "Grandiosa", "2017-06-07");
        createMenuWithDish("Spaghetti Bolognese", "Spaghetti with bolognese sauce", "2017-06-08");
        createMenuWithDish("Taco", "Meat, salad, and tortillas", "2017-06-09");


        boolean hasTomorrowAsNext = menuEJB.getNextListMenu("2017-06-08");
        boolean tomorrowHasNoNext = menuEJB.getNextListMenu("2017-06-09");
        boolean tomorrowHasTodayAsPrevious = menuEJB.getPreviousListMenu("2017-06-09");
        boolean yesterdayHasNoPrevious = menuEJB.getPreviousListMenu("2017-06-07");
        boolean yesterdayHasTodayAsNext = menuEJB.getNextListMenu("2017-06-07");

        assertTrue(hasTomorrowAsNext);
        assertFalse(tomorrowHasNoNext);
        assertTrue(tomorrowHasTodayAsPrevious);
        assertFalse(yesterdayHasNoPrevious);
        assertTrue(yesterdayHasTodayAsNext);
    }
}
