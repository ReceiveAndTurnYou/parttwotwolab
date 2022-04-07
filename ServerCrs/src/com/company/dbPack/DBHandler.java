package com.company.dbPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DBHandler extends DBConfig{
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public int signUpUser(String name, String surname, String login,
                           String password, String email, String country){
        int result = 0;
        String insert = "INSERT INTO " + DBConst.USER_TABLE + " ("
                + DBConst.USERS_FIRSTNAME +"," + DBConst.USERS_SURNAME + ","
                + DBConst.USERS_LOGIN + "," + DBConst.USERS_PASSWORD + ","
                + DBConst.USERS_EMAIL + "," + DBConst.USERS_COUNTRY + ",block)"
                + " VALUES (?,?,?,?,?,?,?)";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, name);
            prSt.setString(2, surname);
            prSt.setString(3, login);
            prSt.setString(4, password);
            prSt.setString(5, email);
            prSt.setString(6, country);
            prSt.setString(7, "0");

            result = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getUser(String login, String password){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBConst.USER_TABLE + " WHERE " +
                DBConst.USERS_LOGIN + "=? AND " +  DBConst.USERS_PASSWORD + "=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, login);
            prSt.setString(2, password);

            resultSet = prSt.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int addProduct(String prNumb, String prDen,
                           String prCond, String prCount, String prType){
        String addpr = "INSERT INTO product_wh (product_number,product_type,product_density,product_conditions,product_count)"
                + " VALUES (?,?,?,?,?)";
        int count =0;
        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(addpr);

            prSt.setString(1, prNumb);
            prSt.setString(2, prType);
            prSt.setString(3, prDen);
            prSt.setString(4, prCond);
            prSt.setString(5, prCount);

            count = prSt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getProductNumb(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT product_number FROM product_wh";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getProductArr(String product_number){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh WHERE product_number=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,product_number);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int deleteProduct(String productNumb){
        int count = 0;

        String select = "DELETE FROM product_wh WHERE product_number=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, productNumb);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int editProduct(String prNumb, String prType,
                          String prDen, String prCond,
                           String prCount, String newPrNumb){
        String addpr = "UPDATE product_wh SET " +
                "product_number =?, product_type =? , product_density=? , " +
                "product_conditions =? , product_count=? WHERE product_number=?";
        int count =0;
        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(addpr);
            prSt.setString(1, newPrNumb);
            prSt.setString(2, prType);
            prSt.setString(3, prDen);
            prSt.setString(4, prCond);
            prSt.setString(5, prCount);
            prSt.setString(6, prNumb);

            count = prSt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getProductFullArr(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<String> getProductFullArrByNumb(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh ORDER BY product_number";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getProductFullArrByDen(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh ORDER BY product_density";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getProductFullArrByCount(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh ORDER BY product_count";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getProductByDen(String prDen){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh WHERE product_density =?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, prDen);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getProductByType(String prType){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh WHERE product_type =?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, prType);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getProductByNumb(String prNumb){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_wh WHERE product_number =?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, prNumb);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int filtrProductDes(String productParam){
        int count = 0;

        String select = "DELETE FROM product_wh WHERE product_density=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, productParam);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int filtrProductType(String productParam){
        int count = 0;

        String select = "DELETE FROM product_wh WHERE product_type=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, productParam);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int filtrProductNumb(String productParam){
        int count = 0;

        String select = "DELETE FROM product_wh WHERE product_number=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, productParam);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getProductType(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT product_type FROM product_tp";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getCompanyFullArr(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT company_name, trust_factor, company_description FROM delivery_company ORDER BY company_name";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getCompanyName(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT company_name FROM delivery_company";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getCompanyArr(String company_name){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM delivery_company WHERE company_name=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,company_name);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addOrder(String clientLogin, String prCount,
                          String prNumb, String cmpName, String address){
        ResultSet resultSet = null;
        PreparedStatement prSt = null;
        int endResult = 0;
        String email="";

        String selectEmail = "SELECT email FROM users WHERE login =?";

        try {
            prSt = getDbConnection().prepareStatement(selectEmail);

            prSt.setString(1, clientLogin);

            resultSet = prSt.executeQuery();

            resultSet.next();
            email = resultSet.getString(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String select = "SELECT product_count FROM product_wh WHERE product_number =?";

        try {
            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, prNumb);

            resultSet = prSt.executeQuery();

            resultSet.next();
            String result = resultSet.getString(1);
            endResult = Integer.parseInt(result) - Integer.parseInt(prCount);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String reloadWh = "UPDATE product_wh SET product_count=?"
                + " WHERE product_number =?";
        prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(reloadWh);
            prSt.setString(1, Integer.toString(endResult));
            prSt.setString(2, prNumb);
            prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dt = sdf.format(date);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 7);
        dt = sdf.format(c.getTime());

        String addpr = "INSERT INTO product_order(login,product_count,product_number,company_name,address,email,delivery_date)"
                + " VALUES (?,?,?,?,?,?,?)";
        int count =0;
        prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(addpr);

            prSt.setString(1, clientLogin);
            prSt.setString(2, prCount);
            prSt.setString(3, prNumb);
            prSt.setString(4, cmpName);
            prSt.setString(5, address);
            prSt.setString(6, email);
            prSt.setString(7, dt);

            count = prSt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getOrderArray(String login){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_order WHERE login=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,login);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getOrdersID(String login){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT idproduct_order FROM product_order WHERE login=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,login);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getOrdersArr(String id){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_order WHERE idproduct_order=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,id);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int removeOrderByID(String id){
        int count = 0;

        String select = "DELETE FROM product_order WHERE idproduct_order=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, id);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int registerCompany(String name, String trFactor, String desc){
        int count = 0;

        String insert = "INSERT INTO delivery_company (company_name,trust_factor,company_description) VALUES (?,?,?)";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, name);
            prSt.setString(2, trFactor);
            prSt.setString(3, desc);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getCompanyFullArrID(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM delivery_company";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int deleteCompany(String name){
        int count = 0;

        String delete = "DELETE FROM delivery_company WHERE company_name=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(delete);

            prSt.setString(1, name);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int changeCompany(String changeName, String name, String trFactor, String cmpDesc){
        int count = 0;

        String changeCmp = "UPDATE delivery_company SET " +
                "company_name =?, trust_factor =? , company_description=? WHERE company_name=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(changeCmp);

            prSt.setString(1, name);
            prSt.setString(2, trFactor);
            prSt.setString(3, cmpDesc);
            prSt.setString(4, changeName);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int addOrderAdmin(String clientLogin, String prCount,
                        String prNumb, String cmpName, String address, String email){
        ResultSet resultSet = null;
        PreparedStatement prSt = null;
        int endResult = 0;

        String select = "SELECT product_count FROM product_wh WHERE product_number =?";

        try {
            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, prNumb);

            resultSet = prSt.executeQuery();

            resultSet.next();
            String result = resultSet.getString(1);
            endResult = Integer.parseInt(result) - Integer.parseInt(prCount);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String reloadWh = "UPDATE product_wh SET product_count=?"
                + " WHERE product_number =?";
        prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(reloadWh);
            prSt.setString(1, Integer.toString(endResult));
            prSt.setString(2, prNumb);
            prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dt = sdf.format(date);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 7);
        dt = sdf.format(c.getTime());

        String addpr = "INSERT INTO product_order(login,product_count,product_number,company_name,address,email,delivery_date)"
                + " VALUES (?,?,?,?,?,?,?)";
        int count =0;
        prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(addpr);

            prSt.setString(1, clientLogin);
            prSt.setString(2, prCount);
            prSt.setString(3, prNumb);
            prSt.setString(4, cmpName);
            prSt.setString(5, address);
            prSt.setString(6, email);
            prSt.setString(7, dt);

            count = prSt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getAllOrderArray(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM product_order";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getOrderID(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT idproduct_order FROM product_order";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int deleteOrderByID(String id){
        int count = 0;

        String delete = "DELETE FROM product_order WHERE idproduct_order=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(delete);

            prSt.setString(1, id);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int editOrderAdmin(String id, String clientLogin, String prCount,
                             String prNumb, String cmpName, String address,
                              String email, String date){
        ResultSet resultSet = null;
        PreparedStatement prSt = null;
        int endResult = 0;

        String select = "SELECT product_count FROM product_wh WHERE product_number =?";

        try {
            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, prNumb);

            resultSet = prSt.executeQuery();

            resultSet.next();
            String result = resultSet.getString(1);
            endResult = Integer.parseInt(result) - Integer.parseInt(prCount);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String reloadWh = "UPDATE product_wh SET product_count=?"
                + " WHERE product_number =?";
        prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(reloadWh);
            prSt.setString(1, Integer.toString(endResult));
            prSt.setString(2, prNumb);
            prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String addpr = "UPDATE product_order SET login=?, product_count=?, product_number=?, " +
                "company_name=?, address=?, email=?, delivery_date=? WHERE idproduct_order=?";
        int count =0;
        prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(addpr);

            prSt.setString(1, clientLogin);
            prSt.setString(2, prCount);
            prSt.setString(3, prNumb);
            prSt.setString(4, cmpName);
            prSt.setString(5, address);
            prSt.setString(6, email);
            prSt.setString(7, date);
            prSt.setString(8, id);

            count = prSt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, Integer> getCompanyCount(){
        ResultSet resultSet = null;

        Map<String, Integer> map = new HashMap<String, Integer>();

        String select = "SELECT company_name FROM delivery_company";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    int counter = 0;
                    ResultSet resultSetSec = null;
                    prSt=null;
                    String selectSec = "SELECT company_name FROM product_order WHERE company_name=?";
                    prSt = getDbConnection().prepareStatement(selectSec);
                    prSt.setString(1, resultSet.getString(i));
                    resultSetSec = prSt.executeQuery();
                    String cmpName = resultSet.getString(i);
                    while (resultSetSec.next()) {
                    counter++;
                    }
                    map.put(cmpName, counter);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public ArrayList<String> getProductNumberCount(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT product_number,product_count FROM product_wh";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addType(String type, String desc){
        String addpr = "INSERT INTO product_tp (product_type, description)"
                + " VALUES (?,?)";
        int count =0;
        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(addpr);

            prSt.setString(1, type);
            prSt.setString(2, desc);

            count = prSt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<String> getUsersID(){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT idusers FROM users WHERE login!='Admin'";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getUserArrByID(String userID){
        ResultSet resultSet = null;
        ArrayList<String> list = new ArrayList<String>();
        String select = "SELECT * FROM users WHERE idusers=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,userID);
            resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columns; i++) {
                    list.add(resultSet.getString(i));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int blockUserByID(String id){
        int count = 0;

        String delete = "UPDATE users SET block=? WHERE idusers=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(delete);

            prSt.setString(1, "1");
            prSt.setString(2, id);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int unBlockUserByID(String id){
        int count = 0;

        String delete = "UPDATE users SET block=? WHERE idusers=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(delete);

            prSt.setString(1, "0");
            prSt.setString(2, id);

            count = prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ResultSet checkUser(String login, String password){
        ResultSet resultSet = null;

        String select = "SELECT block FROM " + DBConst.USER_TABLE + " WHERE " +
                DBConst.USERS_LOGIN + "=? AND " +  DBConst.USERS_PASSWORD + "=?";

        PreparedStatement prSt = null;

        try {

            prSt = getDbConnection().prepareStatement(select);

            prSt.setString(1, login);
            prSt.setString(2, password);

            resultSet = prSt.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
