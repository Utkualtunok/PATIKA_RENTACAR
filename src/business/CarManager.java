package business;

import core.Helper;
import dao.CarDao;
import dao.ModelDao;
import entity.Car;
import entity.Model;

import java.util.ArrayList;

public class CarManager {
    private final CarDao carDao;
    private final ModelDao modelDao;
    public CarManager(){
        this.carDao = new CarDao();
        this.modelDao = new ModelDao();
    }
    public Car getById(int id){return this.carDao.getById(id);}
    public ArrayList<Car> findAll(){return this.carDao.findAll();}

    public ArrayList<Object[]> getForTable(int size, ArrayList<Car> cars){
        ArrayList<Object[]> carList = new ArrayList<>();
        for (Car obj : cars) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getModel().getBrand().getName();
            rowObject[i++] = obj.getModel().getName();
            rowObject[i++] = obj.getModel().getYear();
            rowObject[i++] = obj.getModel().getType();
            rowObject[i++] = obj.getModel().getFuel();
            rowObject[i++] = obj.getModel().getGear();
            rowObject[i++] = obj.getPlate();
            rowObject[i++] = obj.getColor();
            rowObject[i++] = obj.getKm();
            carList.add(rowObject);
        }
        return carList;
    }
    public boolean save (Car car){
        if (this.getById(car.getId()) != null){
            Helper.showMessage("error");
            return false;
        }
        return this.carDao.save(car);
    }
    public boolean update(Car car){
        if (this.getById(car.getId()) == null){
            Helper.showMessage(car.getId() + "ID Kayıtlı araç bulunamadı.");
            return false;
        }
        return this.carDao.update(car);
    }
    public boolean delete(int id){
        if (this.getById(id) == null){
            Helper.showMessage(id + "ID Kayıtlı araç bulunamadı");
            return false;
        }
        return this.carDao.delete(id);
    }
    public ArrayList<Car> serchForBooking (String strt_date, String fnsh_date, Model.Type type, Model.Gear gear, Model.Fuel fuel ){
        String query = "SELECT * FROM public.car as c LEFT JOIN public.model as m";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> joinWhere = new ArrayList<>();

        joinWhere.add("c.car_model_id = m.model_id");

        if (fuel != null){
            where.add("m.model_fuel = '"+ fuel.toString() + "'");
        }
        if (gear != null){
            where.add("m.model_gear = '"+ gear.toString() + "'");
        }
        if (type != null){
            where.add("m.model_type = '"+ type.toString() + "'");
        }

        String whereStr = String.join(" AND " ,where);
        String joinStr = String.join(" AND ", joinWhere);
        if (joinStr.length() > 0 ){
            query += " ON " + joinStr;
        }
        if (whereStr.length() > 0){
            query += " WHERE " + whereStr;
        }
        return this.carDao.selectByQuery(query);
    }


}
