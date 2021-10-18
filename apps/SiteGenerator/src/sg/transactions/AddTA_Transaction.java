package sg.transactions;

import jtps.jTPS_Transaction;
import sg.Data.OfficeHoursData;
import sg.Data.TeachingAssistantPrototype;


/**
 *
 * @author McKillaGorilla
 */
public class AddTA_Transaction implements jTPS_Transaction {
    OfficeHoursData data;
    TeachingAssistantPrototype ta;
    
    public AddTA_Transaction(OfficeHoursData initData, TeachingAssistantPrototype initTA) {
        data = initData;
        ta = initTA;
    }

    @Override
    public void doTransaction() {
        data.addTA(ta);        
    }

    @Override
    public void undoTransaction() {
        data.removeTA(ta);
    }
}
