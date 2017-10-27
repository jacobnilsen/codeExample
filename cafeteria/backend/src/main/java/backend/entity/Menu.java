package backend.entity;

import backend.ejb.MenuEJB;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jacob on 07.06.2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Menu.GET_ALL, query = "SELECT m FROM Menu m")
        })

public class Menu {

    public static final String GET_ALL = "MENU_GET_ALL";



    private Long id;

    @NotEmpty @Size(min=1, max = 20)
    @Type(type="date")
    @Column(unique = true)
    private String date;


    private List<Dish> dayMenu;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(targetEntity = Dish.class, fetch = FetchType.EAGER)
    @NotEmpty
    public List<Dish> getDayMenu(){
        if(dayMenu == null) {
            return new ArrayList<Dish>();
        }

        return dayMenu;
    }

    public void setDayMenu(List<Dish> dayMenu) {
        this.dayMenu = dayMenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}