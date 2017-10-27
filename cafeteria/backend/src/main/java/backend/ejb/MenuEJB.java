package backend.ejb;

import backend.entity.Dish;
import backend.entity.Menu;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jacob on 07.06.2017.
 */
@Stateless
public class MenuEJB {


    @PersistenceContext
    private EntityManager em;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date = new Date();
    private String currentDate = dateFormat.format(date);


    public MenuEJB(){
    }

    public Long createMenu(String date, List<Dish> dayMenu){

        if(dayMenu.isEmpty()){
            throw new IllegalArgumentException("Menu can't be empty!");
        }
        else if(!isValidFormat(date)){
            throw new IllegalArgumentException("Invalid dateformat!");
        }
        else {
            Menu menu = new Menu();
            menu.setDate(date);
            menu.setDayMenu(dayMenu);

            em.persist(menu);
            return menu.getId();
        }

    }
    public static boolean isValidFormat(String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public List<Dish> getCurrentListMenu() {
        List<Dish> dayMenu;
        dayMenu = em.createQuery("SELECT m FROM Menu m WHERE m.date = :date")
                .setParameter("date", currentDate)
                .getResultList();
        if(dayMenu.size() <= 0 ){
            throw new IllegalArgumentException("Empty menu!");
        }
        return dayMenu;
    }

    public Menu getCurrentMenu(){
        TypedQuery<Menu> query = em.createQuery(
                "SELECT DISTINCT m FROM Menu m WHERE m.date=?1", Menu.class);
        query.setParameter(1, currentDate);
        return query.getSingleResult();
    }

    public Menu getNextMenu(){
        TypedQuery<Menu> query = em.createQuery(
                "SELECT DISTINCT m FROM Menu m WHERE m.date > ?1", Menu.class);
        query.setParameter(1, currentDate);
        if (query.getSingleResult().getDate().equals("")){
            return null;
        }
        else {
            return query.getSingleResult();
        }

    }

    public Menu getPreviousMenu(){
        TypedQuery<Menu> query = em.createQuery(
                "SELECT DISTINCT m FROM Menu m WHERE m.date < ?1", Menu.class);
        query.setParameter(1, currentDate);
        if (query.getSingleResult().getDate().equals("")){
            return null;
        }
        else {
            return query.getSingleResult();
        }
    }


    public boolean getNextListMenu(String date){
        List<Dish> dayMenu = em.createQuery("SELECT m FROM Menu m WHERE m.date > :date ORDER BY m.date DESC")
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultList();
        if (dayMenu.size() <= 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean getPreviousListMenu(String date){
        List<Dish> dayMenu = em.createQuery("SELECT m FROM Menu m WHERE m.date < :date ORDER BY m.date ASC")
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultList();
        if (dayMenu.size() <= 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<Menu> getAllMenues(){
        return em.createNamedQuery(Menu.GET_ALL).getResultList();
    }

    public String getDate(String date){
        return em.createQuery("SELECT date FROM Menu m WHERE date = :date")
                .setParameter("date", date)
                .getResultList().get(0).toString();
    }
}
