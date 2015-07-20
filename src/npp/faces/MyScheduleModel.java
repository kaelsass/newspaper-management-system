package npp.faces;


import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;

public class MyScheduleModel extends DefaultScheduleModel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void addEvent(ScheduleEvent event)
    {
        this.getEvents().add(event);
    }

}
