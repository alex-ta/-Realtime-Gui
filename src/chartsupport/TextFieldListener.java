package chartsupport;

import java.awt.TextField;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

public class TextFieldListener extends DataListener{

	/**
	 * TextFieldListener implementation
	 * @author alex
	 * 
	 * A Textfield, which gets updated by
	 * bei the call on its DataListener Methods.
	 * 
	 * */
	
	
	public TextFieldListener(){
		field = new TextField();
	}
	
	private TextField field;
	
	@Override
	void update(EventBean event, String[] names, EventType eType) {
		for(String n: names){
			/**displays the last event as Text.*/
			field.setText(n+": "+event.get(n));
		}
	}

	public TextField getComponent() {
		return field;
	}

	public void setComponent(TextField field) {
		this.field = field;
	}

}
