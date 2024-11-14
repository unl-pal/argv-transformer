package travelling.salesman.genetic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by arpit on 8/4/15.
 */
public class Route {

    private int numberOfCities ;
    private double distance = 0;
    private double fitness = 0;

    public ArrayList<City> routeCities;

    public double calculateDistance()
    {
        double m_distance=0;

        for(int i=0; i<numberOfCities; i++)
        {
            City nextCity;
            City city = routeCities.get(i);

            if((i+1)==numberOfCities)
            { nextCity = routeCities.get(0);}
            else
            { nextCity = routeCities.get(i+1);}

            m_distance = m_distance+city.distanceFromCity(nextCity);
        }
        System.out.println("M_distance: "+m_distance);
        distance = m_distance;
        System.out.println("Distance = "+distance);
        return distance;
    }
}
