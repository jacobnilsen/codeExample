package backend.ejb;


import backend.entity.Dish;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Jacob on 07.06.2017.
 */
@Stateless
public class DishEJB {

    @PersistenceContext
    private EntityManager em;

    public DishEJB(){
    }

    // @return false if it was not possible to create the dish
    public boolean createDish(String dishName, String dishDescription){
        if(dishName.equals("") || dishName == null || dishDescription.equals("") || dishDescription == null){
            //throw new IllegalArgumentException("Name or description can't be empty!");
            return false;
        }
        Dish dish = getDish(dishName);
        if(dish != null){
            // a dish with the same name already exists
            //throw new IllegalArgumentException("Dish with the same name exists!");
            return false;
        }

            dish = new Dish();
            dish.setName(dishName);
            dish.setDescription(dishDescription);

            em.persist(dish);

            return true;
    }



    public Dish getDish(String dishName){
        return em.find(Dish.class, dishName);
    }

    public List<Dish> getAllDishes() {
        return em.createNamedQuery(Dish.GET_ALL).getResultList();
    }

    public List<String> getDishName (String date){
        List<String> getName = em.createQuery("SELECT name FROM Dish d WHERE d.dateId LIKE '%" + date +"%'")
                .getResultList();
        return getName;
    }
    public List<String> getDishDescription(String date){
        List<String> getDescription = em.createQuery("SELECT description FROM Dish d WHERE d.dateId LIKE '%" + date +"%'")
                .getResultList();
        return getDescription;
    }
}
