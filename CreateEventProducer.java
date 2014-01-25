import java.util.ArrayList;

//Класс - "оповещатель"
public class CreateEventProducer
{
    private ArrayList<CreateListener> listeners = new ArrayList<CreateListener>();

    public void addCreateListener(CreateListener listener)
    {
        listeners.add(listener);
    }

    public CreateListener[] getCreateListeners()
    {
        return listeners.toArray(new CreateListener[listeners.size()]);
    }

    public void removeCreateListener(CreateListener listener)
    {
        listeners.remove(listener);
    }

    protected void fireCreate()
    {
        CreateEvent ev = new CreateEvent(this);
        for(CreateListener listener : listeners)
            listener.create(ev);
    }

    public void doWork()
    {
        fireCreate();
    }
}
