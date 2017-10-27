package frontend;

/**
 * Created by Jacob on 09.06.2017.
 */
import backend.ejb.DishEJB;
import backend.ejb.MenuEJB;
import backend.entity.Dish;
import backend.entity.Menu;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Named
@SessionScoped
public class MenuController implements Serializable {

    private HtmlInputText inputComponent = new HtmlInputText();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date = new Date();
    private String thisDate = dateFormat.format(date);



    @EJB
    private MenuEJB menuEJB;

    @EJB
    private DishEJB dishEJB;

    private List<String> dishName;
    private List<String> dishDescription;

    @PostConstruct
    public void init(){
        inputComponent.setValue(thisDate);

    }

    public String getCurrentDate(){
        return menuEJB.getCurrentMenu().getDate();
    }

    public String getPreviousDate(){
        return menuEJB.getPreviousMenu().getDate();
    }

    public String getNextDate() {
        return menuEJB.getNextMenu().getDate();
    }


    public List<Menu> getMenus(){
        List<Menu> menus = menuEJB.getAllMenues();

        return menus;
    }


    public String getThisDate(){return thisDate;}

    public List<Dish> getDishes(){
        return menuEJB.getCurrentMenu().getDayMenu();
    }

    public List<Dish> getPreviousDishes(){
        List <Dish> previousDishes = menuEJB.getPreviousMenu().getDayMenu();
        if (previousDishes.size() <= 0){
            // Don't return
            return null;
        }
        else {
            return previousDishes;
        }
    }

    public List<Dish> getNextDishes(){
        List <Dish> nextDishes = menuEJB.getNextMenu().getDayMenu();
        if (nextDishes.size() <= 0){
            // Don't return
            return null;
        }
        else {
            return nextDishes;
        }
    }




    public HtmlInputText getInputComponent(){return inputComponent;}

    public void setInputComponent(HtmlInputText inputComponent){this.inputComponent = inputComponent;}


}
