
package scrummaster.dataclasses;
import java.util.Scanner;
import java.sql.*;
public class ScrumMasterCommand {

    public void excuteCommandFuncation(ScrumMasterCommand scrum, Scanner scan, Request req, Connection dbcon) {
        req.callMethod(req, scrum, scan, dbcon);
    }

    public void getFuncation(Scanner scan, Request req, Connection dbcon) {
        System.out.println("no support for get  hello ");
    }

    public void createFuncation(Scanner scan, Request req, Connection dbcon) {
        System.out.println("no support create function");
    }
}
/**
 *
 * @author Jordan
 */
//connect to the scrumMaster Comnmand line defualt option 
enum Request {
    GET,
    CREATE,
    INVALID;

    public static Request setRequest(String commandLineRequest) {
        try {
            return Request.valueOf(commandLineRequest.toUpperCase());
        } catch (Exception e) {
            return INVALID;
        }

    }
    // ask brandon 
    public void callMethod(Request req, ScrumMasterCommand scrum, Scanner scan, Connection dbcon){
         if(req == Request.CREATE){
             scrum.createFuncation(scan, req,  dbcon);
             
         }
         else if (req == Request.GET)
             scrum.getFuncation(scan, req,  dbcon);
    }
}