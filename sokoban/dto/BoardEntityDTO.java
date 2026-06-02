package sokoban.dto;

import sokoban.model.Position;

public class BoardEntityDTO {

    private final Position position;
    private final EntityType tipo;

    public BoardEntityDTO(Position position, EntityType tipo) {
        this.position = position;
        this.tipo = tipo;
    }

    public Position getPosition() {
        return position;
    }

    public EntityType getTipo() {
        return tipo;
    }
}
