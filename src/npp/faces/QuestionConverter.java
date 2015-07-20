package npp.faces;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import npp.dto.QuestionDto;

import org.primefaces.component.orderlist.OrderList;

@FacesConverter(value = "questionConverter")
public class QuestionConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
    	if(entity instanceof QuestionDto)
    	{
    		return ((QuestionDto)entity).getSeq()+"";
    	}
    	return "";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String seq) {
    	Object ret = null;
        if (component instanceof OrderList) {
            Object list = ((OrderList) component).getValue();
            List<QuestionDto> dl = (List<QuestionDto>) list;
            System.out.println("list : " + list);
            for (QuestionDto o : dl) {
                if (seq.equals(o.getSeq()+"")) {
                    ret = o;
                    break;
                }
            }
        }
        return ret;

    }

}