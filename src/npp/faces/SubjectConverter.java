package npp.faces;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import npp.dto.NewspaperDto;
import npp.dto.SubjectDto;

import org.primefaces.component.orderlist.OrderList;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(value = "subjectConverter")
public class SubjectConverter implements Converter {

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object entity) {
		if (entity instanceof SubjectDto) {
			return ((SubjectDto) entity).getSeq() + "";
		}
		return "";
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String seq) {
		Object ret = null;
		if (component instanceof OrderList) {
			Object list = ((OrderList) component).getValue();
			System.out.println("orderList value: " + list);
			List<SubjectDto> dl = (List<SubjectDto>) list;
			for (SubjectDto o : dl) {
				if (seq.equals(o.getSeq() + "")) {
					ret = o;
					break;
				}
			}
		}
		return ret;

	}

}