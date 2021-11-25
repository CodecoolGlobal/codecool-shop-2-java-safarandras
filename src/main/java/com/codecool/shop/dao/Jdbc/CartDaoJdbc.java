package com.codecool.shop.dao.Jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CartDaoJdbc implements CartDao {

    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(Cart cart) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO carts(id, date, userID, items) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            java.util.Date utilDate = new java.util.Date();
            Date savedAt = new java.sql.Date(utilDate.getTime());

            String items = new Gson().toJson(cart.getAllLineItem());

            statement.setInt(1, cart.getCartId());
            statement.setDate(2, savedAt);
            statement.setInt(3, cart.getUserId());
            statement.setString(4, items);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Cart cart) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE carts SET date = ?, items = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            java.util.Date utilDate = new java.util.Date();
            Date savedAt = new java.sql.Date(utilDate.getTime());

            String items = new Gson().toJson(cart.getAllLineItem());

            st.setDate(1, savedAt);
            st.setString(2, items);
            st.setInt(3, cart.getCartId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(int cartId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM carts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()) {
                return null;
            }

            Cart cart = new Cart();
            cart.setCartId(resultSet.getInt(1));
            cart.setUserId(resultSet.getInt(2));
            String jsonString = resultSet.getString(3);
            Type setType = new TypeToken<HashSet<String>>(){}.getType();
            Gson gson = new Gson();
            HashSet<LineItem> items = gson.fromJson(jsonString, setType);
            cart.setLineItems(items);
            return cart;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(int cartId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM carts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, cartId);
            statement.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Set<Cart> getAll(int userId) {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM productcategories";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            HashSet<Cart> carts = new HashSet<>();
            while(resultSet.next()){
                Cart cart = new Cart();
                cart.setCartId(resultSet.getInt(1));
                cart.setUserId(resultSet.getInt(2));
                String jsonString = resultSet.getString(3);
                Type setType = new TypeToken<HashSet<String>>(){}.getType();
                Gson gson = new Gson();
                HashSet<LineItem> items = gson.fromJson(jsonString, setType);
                cart.setLineItems(items);
                carts.add(cart);
            }
            return carts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
