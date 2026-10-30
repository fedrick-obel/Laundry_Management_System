/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple_client_server_with_serializable;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author HP
 */
public class SerializableClass implements Serializable{
    String regno,surname;
    File myfile;
    public SerializableClass(){
        regno="";
        surname = "";
        myfile = null;
    }
}
