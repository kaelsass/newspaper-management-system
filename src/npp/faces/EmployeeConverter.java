package npp.faces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import npp.dto.WorkInfoDto;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(value = "employeeConverter")
public class EmployeeConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
    	if(entity instanceof WorkInfoDto)
    	{
    		return ((WorkInfoDto)entity).getId();
    	}
    	return "";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String id) {
    	Object ret = null;
        if (component instanceof PickList) {
            Object dualList = ((PickList) component).getValue();
            DualListModel<WorkInfoDto> dl = (DualListModel<WorkInfoDto>) dualList;
            for (WorkInfoDto o : dl.getSource()) {
                if (id.equals(o.getId())) {
                    ret = o;
                    break;
                }
            }
            if (ret == null)
                for (WorkInfoDto o : dl.getTarget()) {
                    if (id.equals(o.getId())) {
                        ret = o;
                        break;
                    }
                }
        }
        return ret;

    }

}