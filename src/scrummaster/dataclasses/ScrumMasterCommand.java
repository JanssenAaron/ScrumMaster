
package scrummaster.dataclasses;

import java.util.Scanner;
import scrummaster.enums.*;

public class ScrumMasterCommand {

    public void excuteCommandFuncation(ScrumMasterCommand scrum,  Request req) {
        req.callMethod(req, scrum);
    }
    //this method should be used for get 1 element
    public void getFunction( Request req) {
        System.out.println("no support for get  hello ");
    }
    //this method should be used for adding elements to the database
    public void deleteFunction( Request req) {
        System.out.println("no support create function");
    }
    //this method should be used for select all the element
    public void insertFunction(Request req){
        System.out.println("no support create function");
    }
     //this method should be used for select all the element
     public void updateFunction(Request req){
         System.out.println("no support create function");
    }
    //this method should be used for select all the element
     public void listFunction(Request req){
       System.out.println("no support create function");
     }

}