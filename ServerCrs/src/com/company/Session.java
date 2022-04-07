package com.company;

import com.company.ClientReview.WriteReview;
import com.company.LoggerPcg.Logger;
import com.company.dbPack.DBHandler;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


public class Session extends Thread{
    private Socket socket;

    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;


    public Session(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        int sw = 1;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            sw =Integer.parseInt((String)objectInputStream.readObject());
            switch (sw){
                case 0:{
                    DBHandler dbHandler = new DBHandler();
                    String login = (String)objectInputStream.readObject();
                    String password = (String)objectInputStream.readObject();
                    System.out.println("Login: " + login + "\nPassword: " + password );
                    ResultSet resultSet = dbHandler.getUser(login,password);
                    int counter = 0;

                    while(resultSet.next()){
                        counter++;
                    }
                    if(counter!=0) {
                        Logger.getLogger().addLogInfo("User: " + login + " auth in programm");
                        objectOutputStream.writeObject("true");
                    }
                    else objectOutputStream.writeObject("false");
                }
                break;
                case 1:{
                    String name = (String)objectInputStream.readObject();
                    String surname = (String)objectInputStream.readObject();
                    String login = (String)objectInputStream.readObject();
                    String password = (String)objectInputStream.readObject();
                    String email = (String)objectInputStream.readObject();
                    String country = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();
                    int result = dbHandler.signUpUser(name, surname, login, password, email, country);
                    if(result>0){
                        Logger.getLogger().addLogInfo("User: " + name + " reg in programm");
                    }
                    objectOutputStream.writeObject(result);
                }
                    break;
                case 2:{
                    String prNumb = (String)objectInputStream.readObject();
                    String prDen = (String)objectInputStream.readObject();
                    String prCond = (String)objectInputStream.readObject();
                    String prCount = (String)objectInputStream.readObject();
                    String prType = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();

                    int result = dbHandler.addProduct(prNumb, prDen, prCond, prCount, prType);
                    if(result>0){
                        Logger.getLogger().addLogInfo("Product added");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 3:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductNumb();
                    Logger.getLogger().addLogInfo("Product numb list selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 4:{
                    DBHandler dbHandler = new DBHandler();
                    String prType = (String)objectInputStream.readObject();
                    ArrayList<String> list = dbHandler.getProductArr(prType);
                    Logger.getLogger().addLogInfo("ProductArr by type selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 5:{
                    DBHandler dbHandler = new DBHandler();
                    String productNumb = (String)objectInputStream.readObject();
                    int count = dbHandler.deleteProduct(productNumb);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Product :" + productNumb + " deleted");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 6:{
                    String prNumb = (String)objectInputStream.readObject();
                    String prType = (String)objectInputStream.readObject();
                    String prDen = (String)objectInputStream.readObject();
                    String prCond = (String)objectInputStream.readObject();
                    String prCount = (String)objectInputStream.readObject();
                    String newPrNumb = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();

                    int result = dbHandler.editProduct(prNumb, prType, prDen, prCond, prCount,newPrNumb);
                    if(result>0){
                        Logger.getLogger().addLogInfo("Product :" + prNumb + " edited");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 7:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductFullArr();
                    Logger.getLogger().addLogInfo("Product full arr selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 8:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductFullArrByNumb();
                    Logger.getLogger().addLogInfo("Product full arr by numb selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 9:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductFullArrByDen();
                    Logger.getLogger().addLogInfo("Product full arr by den selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 10:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductFullArrByCount();
                    Logger.getLogger().addLogInfo("Product full arr by count selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 11:{
                    String prDen = (String)objectInputStream.readObject();
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductByDen(prDen);
                    Logger.getLogger().addLogInfo("Product by den selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 12:{
                    String prType = (String)objectInputStream.readObject();
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductByType(prType);
                    Logger.getLogger().addLogInfo("Product by type selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 13:{
                    String prNumb = (String)objectInputStream.readObject();
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductByNumb(prNumb);
                    Logger.getLogger().addLogInfo("Product by numb selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 14:{
                    DBHandler dbHandler = new DBHandler();
                    String productParam = (String)objectInputStream.readObject();
                    int count = dbHandler.filtrProductDes(productParam);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Product by "+ productParam+" deleted");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 15:{
                    DBHandler dbHandler = new DBHandler();
                    String productParam = (String)objectInputStream.readObject();
                    int count = dbHandler.filtrProductType(productParam);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Product by "+ productParam+" deleted");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 16:{
                    DBHandler dbHandler = new DBHandler();
                    String productParam = (String)objectInputStream.readObject();
                    int count = dbHandler.filtrProductNumb(productParam);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Product by "+ productParam+" deleted");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 17:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductType();
                    Logger.getLogger().addLogInfo("Product type selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 18:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getCompanyFullArr();
                    Logger.getLogger().addLogInfo("Company full arr selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 19:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getCompanyName();
                    Logger.getLogger().addLogInfo("Company name selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 20:{
                    DBHandler dbHandler = new DBHandler();
                    String cmpName = (String)objectInputStream.readObject();
                    ArrayList<String> list = dbHandler.getCompanyArr(cmpName);
                    Logger.getLogger().addLogInfo("Company arr by name selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 21:{
                    String clientLogin = (String)objectInputStream.readObject();
                    String prCount = (String)objectInputStream.readObject();
                    String prNumb = (String)objectInputStream.readObject();
                    String cmpName = (String)objectInputStream.readObject();
                    String address = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();

                    int result = dbHandler.addOrder(clientLogin, prCount, prNumb, cmpName, address);
                    if(result>0){
                        Logger.getLogger().addLogInfo("Order for: "+clientLogin+" added");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 22:{
                    DBHandler dbHandler = new DBHandler();
                    String login = (String)objectInputStream.readObject();
                    ArrayList<String> list = dbHandler.getOrderArray(login);
                    Logger.getLogger().addLogInfo("Order arr by login selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 23:{
                    DBHandler dbHandler = new DBHandler();
                    String login = (String)objectInputStream.readObject();
                    ArrayList<String> list = dbHandler.getOrdersID(login);
                    Logger.getLogger().addLogInfo("Orders id by login selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 24:{
                    DBHandler dbHandler = new DBHandler();
                    String id = (String)objectInputStream.readObject();
                    ArrayList<String> list = dbHandler.getOrdersArr(id);
                    Logger.getLogger().addLogInfo("Orders arr by id selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 25:{
                    DBHandler dbHandler = new DBHandler();
                    String id = (String)objectInputStream.readObject();
                    int count = dbHandler.removeOrderByID(id);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Order by id:" + id + " removed");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 26:{
                    WriteReview writeReview = new WriteReview();
                    String cmpName = (String)objectInputStream.readObject();
                    String login = (String)objectInputStream.readObject();
                    String text = (String)objectInputStream.readObject();
                    int res = writeReview.writeReviewInFile(cmpName, login, text);
                    if(res>0){
                        Logger.getLogger().addLogInfo("User: "+ login +" write review in file");
                    }
                    objectOutputStream.writeObject(res);
                }
                break;
                case 27:{
                    DBHandler dbHandler = new DBHandler();
                    String name = (String)objectInputStream.readObject();
                    String trFactor = (String)objectInputStream.readObject();
                    String desc = (String)objectInputStream.readObject();
                    int res = dbHandler.registerCompany(name, trFactor, desc);
                    if(res>0){
                        Logger.getLogger().addLogInfo("Company: "+ name +" registred");
                    }
                    objectOutputStream.writeObject(res);
                }
                break;
                case 28:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getCompanyFullArrID();
                    Logger.getLogger().addLogInfo("Company full arr id selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 29:{
                    DBHandler dbHandler = new DBHandler();
                    String cmpName = (String)objectInputStream.readObject();
                    int count = dbHandler.deleteCompany(cmpName);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Company: "+ cmpName +" deleted");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 30:{
                    DBHandler dbHandler = new DBHandler();
                    String changeName = (String)objectInputStream.readObject();
                    String cmpName = (String)objectInputStream.readObject();
                    String trFactor = (String)objectInputStream.readObject();
                    String cmpDesc = (String)objectInputStream.readObject();
                    int count = dbHandler.changeCompany(changeName, cmpName, trFactor, cmpDesc);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Company: "+ cmpName +" changed");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 31:{
                    String clientLogin = (String)objectInputStream.readObject();
                    String prCount = (String)objectInputStream.readObject();
                    String prNumb = (String)objectInputStream.readObject();
                    String cmpName = (String)objectInputStream.readObject();
                    String address = (String)objectInputStream.readObject();
                    String email = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();

                    int result = dbHandler.addOrderAdmin(clientLogin, prCount, prNumb, cmpName, address, email);
                    if(result>0){
                        Logger.getLogger().addLogInfo("Order for: "+ clientLogin +" added");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 32:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getAllOrderArray();
                    Logger.getLogger().addLogInfo("All order array selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 33:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getOrderID();
                    Logger.getLogger().addLogInfo("Order id selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 34:{
                    DBHandler dbHandler = new DBHandler();
                    String id = (String)objectInputStream.readObject();
                    int count = dbHandler.deleteOrderByID(id);
                    if(count>0){
                        Logger.getLogger().addLogInfo("Order by: "+ id +" deleted");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 35:{
                    String id = (String)objectInputStream.readObject();
                    String clientLogin = (String)objectInputStream.readObject();
                    String prCount = (String)objectInputStream.readObject();
                    String prNumb = (String)objectInputStream.readObject();
                    String cmpName = (String)objectInputStream.readObject();
                    String address = (String)objectInputStream.readObject();
                    String email = (String)objectInputStream.readObject();
                    String date = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();

                    int result = dbHandler.editOrderAdmin(id, clientLogin, prCount, prNumb, cmpName, address, email, date);
                    if(result>0){
                        Logger.getLogger().addLogInfo("Order by: "+ id +" edited");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 36:{
                    DBHandler dbHandler = new DBHandler();
                    Map<String, Integer> map = dbHandler.getCompanyCount();
                    Logger.getLogger().addLogInfo("Company count selected");
                    objectOutputStream.writeObject(map);
                }
                break;
                case 37:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getProductNumberCount();
                    Logger.getLogger().addLogInfo("Product number count selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 38:{
                    WriteReview writeReview = new WriteReview();
                    ArrayList<String> result = writeReview.selectReview();
                    Logger.getLogger().addLogInfo("Review selected");
                    objectOutputStream.writeObject(result);
                }
                break;
                case 39:{
                    WriteReview writeReview = new WriteReview();
                    int result = writeReview.clearFile();
                    if(result>0){
                        Logger.getLogger().addLogInfo("File cleared");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 40:{
                    WriteReview writeReview = new WriteReview();
                    ArrayList<String> result = writeReview.selectLogs();
                    Logger.getLogger().addLogInfo("Logs selected");
                    objectOutputStream.writeObject(result);
                }
                break;
                case 41:{
                    WriteReview writeReview = new WriteReview();
                    int result = writeReview.clearLogs();
                    if(result>0){
                        Logger.getLogger().addLogInfo("Logs cleared");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 42:{
                    String type = (String)objectInputStream.readObject();
                    String desc = (String)objectInputStream.readObject();

                    DBHandler dbHandler =new DBHandler();

                    int result = dbHandler.addType(type, desc);
                    if(result>0){
                        Logger.getLogger().addLogInfo("Type added");
                    }
                    objectOutputStream.writeObject(result);
                }
                break;
                case 43:{
                    DBHandler dbHandler = new DBHandler();
                    ArrayList<String> list = dbHandler.getUsersID();
                    Logger.getLogger().addLogInfo("Users ID list selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 44:{
                    DBHandler dbHandler = new DBHandler();
                    String userID = (String)objectInputStream.readObject();
                    ArrayList<String> list = dbHandler.getUserArrByID(userID);
                    Logger.getLogger().addLogInfo("User by id selected");
                    objectOutputStream.writeObject(list);
                }
                break;
                case 45:{
                    DBHandler dbHandler = new DBHandler();
                    String id = (String)objectInputStream.readObject();
                    int count = dbHandler.blockUserByID(id);
                    if(count>0){
                        Logger.getLogger().addLogInfo("User by: "+ id +" blocked");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 46:{
                    DBHandler dbHandler = new DBHandler();
                    String id = (String)objectInputStream.readObject();
                    int count = dbHandler.unBlockUserByID(id);
                    if(count>0){
                        Logger.getLogger().addLogInfo("User by: "+ id +" unblocked");
                    }
                    objectOutputStream.writeObject(count);
                }
                break;
                case 47:{
                    DBHandler dbHandler = new DBHandler();
                    String login = (String)objectInputStream.readObject();
                    String password = (String)objectInputStream.readObject();
                    ResultSet resultSet = dbHandler.checkUser(login,password);
                    resultSet.next();
                    System.out.println(resultSet.getString(1));
                    if(!resultSet.getString(1).equals("1")) {
                        Logger.getLogger().addLogInfo("User: " + login + " checked on blocked");
                        objectOutputStream.writeObject("true");
                    }
                    else objectOutputStream.writeObject("false");
                }
                break;


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
