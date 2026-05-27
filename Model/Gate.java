package Model;

public class Gate extends BoardEntity{

    private boolean isOpen = false;

    private void open()
    {
        isOpen = true;
    }

    public Gate(Position position) {
        super(position);
    }


}
