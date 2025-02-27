package dao;

import core.Db;
import entity.Brand;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection conn;


    public BrandDao() {
        this.conn = Db.getInstance();
    }
    public ArrayList<Brand> findAll(){
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand ORDER BY brand_id ASC";
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(sql);
            while (rs.next()){
                brandList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return brandList;
    }

    public boolean save(Brand brand){
        String query = "INSERT INTO public.brand (brand_name) VALUES (?)";
        try {
            PreparedStatement pr = this.conn.prepareStatement(query);
            pr.setString(1, brand.getName());
            return pr.executeUpdate() != -1;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean update(Brand brand){
        String query = "UPDATE public.brand SET brand_name = ? WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.conn.prepareStatement(query);
            pr.setString(1, brand.getName());
            pr.setInt(2, brand.getId());
            return pr.executeUpdate() != -1;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete(int id){
        String query = "DELETE FROM public.brand WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.conn.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Brand getById(int id){
        Brand obj = null;
        String query = "SELECT * FROM public.brand WHERE brand_id = ?";
        try {
            PreparedStatement pr = this.conn.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }

    public Brand match(ResultSet rs) throws SQLException {
        Brand obj = new Brand();
        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));
        return obj;
    }
}
