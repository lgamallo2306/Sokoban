package sokoban.model.factory;

import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.Sokoban;
import sokoban.model.entity.box.FragileBox;
import sokoban.model.entity.box.KeyBox;
import sokoban.model.entity.box.NormalBox;
import sokoban.model.entity.floor.Empty;
import sokoban.model.entity.floor.Slippery;
import sokoban.model.entity.floor.Target;
import sokoban.model.entity.obstacle.Gate;
import sokoban.model.entity.obstacle.Lock;
import sokoban.model.entity.obstacle.Wall;

import java.util.ArrayList;
import java.util.List;

public class BoardEntityFactory {

    public List<BoardEntity> create(char c, Position pos) {
        List<BoardEntity> result = new ArrayList<>();
        switch (c) {
            case '#':
                result.add(new Wall(pos));
                break;
            case ' ':
                result.add(new Empty(pos));
                break;
            case '@':
                result.add(new Empty(pos));
                result.add(new Sokoban(pos));
                break;
            case '$':
                result.add(new Empty(pos));
                result.add(new NormalBox(pos));
                break;
            case 'F':
                result.add(new Empty(pos));
                result.add(new FragileBox(pos));
                break;
            case 'K':
                result.add(new Empty(pos));
                result.add(new KeyBox(pos));
                break;
            case '.':
                result.add(new Target(pos));
                break;
            case '~':
                result.add(new Slippery(pos));
                break;
            case 'L':
                result.add(new Empty(pos));
                result.add(new Lock(pos));
                break;
            case 'O':
                result.add(new Empty(pos));
                result.add(new Gate(pos, true));
                break;
            case 'X':
                result.add(new Empty(pos));
                result.add(new Gate(pos, false));
                break;
            default:
                throw new IllegalArgumentException("Carácter de nivel desconocido: '" + c + "'");
        }
        return result;
    }
}
