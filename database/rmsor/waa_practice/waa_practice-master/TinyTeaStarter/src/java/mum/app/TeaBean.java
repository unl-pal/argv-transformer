package mum.app;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import mum.model.TeaEntity;
import mum.model.Order;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tina
 */
@Named
@SessionScoped
public class TeaBean implements Serializable {

    @EJB //this annotation causes the container to inject this dependency
    private mum.model.TeaEntityFacade ejbTeaFacade;

    private List<TeaEntity> teaList;
    private List<Order> orderList;
}
