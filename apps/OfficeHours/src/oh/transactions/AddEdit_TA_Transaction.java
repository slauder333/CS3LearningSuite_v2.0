/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.transactions;

import jtps.jTPS_Transaction;
import oh.data.TeachingAssistantPrototype;

/**
 *
 * @author Brandon
 */
public class AddEdit_TA_Transaction implements jTPS_Transaction{
    TeachingAssistantPrototype oldTa;
    TeachingAssistantPrototype ta;
    String name;
    String email;
    String taType;
    
    public AddEdit_TA_Transaction(TeachingAssistantPrototype initoldTa, TeachingAssistantPrototype initta, String initName, String initEmail, String initTAType){
        ta = initta;
        name = initName;
        email = initEmail;
        taType = initTAType;
        oldTa = initoldTa;
    }
    @Override
    public void doTransaction(){
        ta.setName(name);
        ta.setEmail(email);
        ta.setTaType(taType);
    }
    @Override
    public void undoTransaction(){
        ta.setName(oldTa.getName());
        ta.setEmail(oldTa.getEmail());
        ta.setTaType(oldTa.getTaType());
    }
}
