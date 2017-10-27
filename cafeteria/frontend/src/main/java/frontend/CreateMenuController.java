package frontend;

import backend.ejb.DishEJB;
import backend.ejb.MenuEJB;
import backend.entity.Dish;
import backend.entity.Menu;
import com.sun.mail.imap.protocol.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.inject.Named;

/**
 * Created by Jacob on 09.06.2017.
 */



@Named
@RequestScoped
public class CreateMenuController {

    @EJB
    private MenuEJB menuEJB;

    @EJB
    private DishEJB dishEJB;

    private String formDate;
    private Map<String, Boolean> checked = new HashMap<String, Boolean>();



    public String createMenu(){
        List<Dish> checkedDishes = new ArrayList<Dish>();

        for(Dish dish : dishEJB.getAllDishes()){
            if(checked.get(dish.getName())){
                checkedDishes.add(dish);
            }
        }

        try{
            menuEJB.createMenu(formDate, checkedDishes);
            checked.clear();
        }
        catch (Exception e){
            return "index.jsf";
        }
        return "menu.jsf";
    }

    public String getFormDate(){return formDate;}

    public void setFormDate(String formDate){this.formDate = formDate;}

    public Map<String, Boolean> getChecked(){return checked;}

    public void setChecked(Map<String, Boolean> checked){this.checked = checked;}
}
