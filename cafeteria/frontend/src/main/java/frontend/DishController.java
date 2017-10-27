package frontend;

/**
 * Created by Jacob on 09.06.2017.
 */
import backend.ejb.DishEJB;
import backend.entity.Dish;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@SessionScoped
public class DishController implements Serializable{

    @EJB
    private DishEJB dishEJB;

    public List<Dish> getDishes(){
        List<Dish> dishes = dishEJB.getAllDishes();

        return dishes;
    }
}
