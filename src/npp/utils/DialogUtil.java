package npp.utils;

import org.primefaces.context.RequestContext;

public class DialogUtil {

	public static void hideDialog(String dialog) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute(dialog + ".hide();");
	}
	public static void showDialog(String dialog) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute(dialog + ".show();");
	}

}
