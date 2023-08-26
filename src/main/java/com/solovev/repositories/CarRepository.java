package com.solovev.repositories;

import com.solovev.model.Car;
import com.solovev.util.Constants;

public class CarRepository extends AbstractRepository<Car> {
    public CarRepository(){
        super(Constants.SERVER_URL,"/cars", Car.class);
    }
}
