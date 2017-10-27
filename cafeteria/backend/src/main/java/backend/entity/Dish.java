package backend.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Jacob on 07.06.2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Dish.GET_ALL, query = "SELECT d FROM Dish d"),
        @NamedQuery(name = Dish.GET_DISH, query = "SELECT d FROM Dish d WHERE d.name = :dname")
})

public class Dish {

    public static final String GET_ALL = "DISH_GET_ALL";
    public static final String GET_DISH = "DISH_GET_DISH";

    @Id
    @NotEmpty @Size(min=1, max=40)
    private String name;

    @NotEmpty @Size(min=1, max=100)
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desctiption) {
        this.description = desctiption;
    }

}
