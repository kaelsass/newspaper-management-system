package npp.faces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import npp.dto.NewspaperDto;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(value = "newspaperConverter")
public class NewspaperConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
    	if(entity instanceof NewspaperDto)
    	{
    		return ((NewspaperDto)entity).getSeq()+"";
    	}
    	return "";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String seq) {
    	Object ret = null;
        if (component instanceof PickList) {
            Object dualList = ((PickList) component).getValue();
            DualListModel<NewspaperDto> dl = (DualListModel<NewspaperDto>) dualList;
            for (NewspaperDto o : dl.getSource()) {
                if (seq.equals(o.getSeq()+"")) {
                    ret = o;
                    break;
                }
            }
            if (ret == null)
                for (NewspaperDto o : dl.getTarget()) {
                    if (seq.equals(o.getSeq()+"")) {
                        ret = o;
                        break;
                    }
                }
        }
        return ret;

    }

}