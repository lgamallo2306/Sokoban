package Model;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position translate(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy);
    }
}

