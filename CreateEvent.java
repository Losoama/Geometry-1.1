import java.util.EventObject;

//Событие при выполнении метода main
public class CreateEvent extends EventObject {

    public CreateEvent(Object source) {
        super(source);
    }

    public CreateEvent() {
        this(null);
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "[source = " + getSource() + "]";
    }
}
