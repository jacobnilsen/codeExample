package frontend;

/**
 * Created by Jacob on 09.06.2017.
 */
import backend.ejb.DishEJB;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CreateDishController {

    private String formName;
    private String formDescription;

    @EJB
    private DishEJB dishEJB;

    public String createDish(){
        try{
            dishEJB.createDish(formName, formDescription);
        }
        catch (Exception e){
            return "index.jsf";
        }
        return "dishes.jsf";
    }

    public String getFormName(){return formName;}

    public void setFormName(String formName){this.formName = formName;}

    public String getFormDescription(){return formDescription;}

    public void setFormDescription(String formDescription){this.formDescription = formDescription;}
}
