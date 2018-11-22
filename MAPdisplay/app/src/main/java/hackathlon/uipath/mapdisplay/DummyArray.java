package hackathlon.uipath.mapdisplay;

import java.util.ArrayList;
import java.util.List;

public class DummyArray {

    //public List<Object> line43;

    public DummyArray(){

    }

    public List<Object> getList(){
        List<Object> dummyList = new ArrayList<Object>();
        dummyList.add(new DummyElement(44.4501548,26.09533,49,1542446272156l));
        dummyList.add(new DummyElement(45.4501648,27.095328,52.999996185302734,1542441612909l));
        return dummyList;
    }

}
