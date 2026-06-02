import sokoban.controller.GameController;
import sokoban.dto.BoardEntityDTO;
import sokoban.dto.EntityType;
import sokoban.dto.GameStateDTO;
import sokoban.model.Direction;

public class Main {

    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.loadLevel("lvl1.txt", 1);

        System.out.println("=== Estado inicial ===");
        render(controller.getCurrentState());

        Direction[] secuencia = { Direction.RIGHT, Direction.RIGHT };
        for (Direction dir : secuencia) {
            controller.handleInput(dir);
            System.out.println("\n=== Tras mover " + dir + " ===");
            render(controller.getCurrentState());
        }

        System.out.println("\nVictoria: " + controller.isVictoria());
    }

    private static void render(GameStateDTO dto) {
        char[][] grid = new char[dto.getRows()][dto.getCols()];
        int[][] prio = new int[dto.getRows()][dto.getCols()];
        for (int r = 0; r < dto.getRows(); r++) {
            for (int c = 0; c < dto.getCols(); c++) {
                grid[r][c] = ' ';
            }
        }
        for (BoardEntityDTO e : dto.getEntities()) {
            int r = e.getPosition().getRow();
            int c = e.getPosition().getCol();
            EntityType type = e.getTipo();
            if (type.getRenderPriority() >= prio[r][c]) {
                grid[r][c] = type.getSymbol();
                prio[r][c] = type.getRenderPriority();
            }
        }
        for (int r = 0; r < dto.getRows(); r++) {
            System.out.println(new String(grid[r]));
        }
        System.out.println("Mov: " + dto.getMovimientos() + " | Empujes: " + dto.getEmpujes()
                + " | Nivel: " + dto.getNivelActual());
    }
}
