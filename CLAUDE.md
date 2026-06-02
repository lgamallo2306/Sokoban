# CLAUDE.md — Sokoban
> Documento de contexto para Claude Code. Describe la arquitectura, decisiones de diseño, patrones aplicados y el diagrama UML completo del proyecto.

---

## 1. Descripción del proyecto

Implementación del videojuego **Sokoban** en **Java con Swing** como Trabajo Integrador de la asignatura *Proceso de Desarrollo de Software (UADE)*. El objetivo es aplicar correctamente los principios y patrones de diseño vistos en clase: **SOLID, GRASP, MVC y GOF**.

---

## 2. Tecnologías

| Elemento | Tecnología |
|---|---|
| Lenguaje | Java |
| GUI | Swing (JFrame, JPanel) |
| Build | Maven |
| Testing | JUnit |
| Diagramas | StarUML / FigJam |

---

## 3. Arquitectura general: MVC

El proyecto sigue estrictamente el patrón **Model-View-Controller**:

```
INPUT (teclado)
    ↓
GamePanel (KeyListener)
    ↓
GameController         ← solo coordina, sin lógica de negocio
    ↓
GameEngine             ← toda la lógica del juego
    ↓
Board + Entidades      ← estado del tablero
    ↓
GameStateDTO           ← transporte de datos hacia la vista
    ↓
GamePanel + HUDPanel   ← renderización
```

---

## 4. Mapa de caracteres (.txt → entidad)

Los niveles se cargan desde archivos `.txt`. Cada carácter representa una entidad:

| Carácter | Entidad |
|---|---|
| `#` | Wall |
| ` ` | Empty |
| `@` | Sokoban (jugador) |
| `$` | NormalBox |
| `F` | FragileBox |
| `K` | KeyBox |
| `.` | Target |
| `~` | Slippery |
| `L` | Lock |
| `O` | Gate (abierto) |
| `X` | Gate (cerrado) |

---

## 5. Diagrama UML

```
┌──────────────────────────────────────────────────────────────────────────┐
│                        «abstract» BoardEntity                            │
│──────────────────────────────────────────────────────────────────────────│
│ # position: Position                                                     │
│──────────────────────────────────────────────────────────────────────────│
│ + getPosition(): Position                                                │
│ + setPosition(pos: Position): void                                       │
│ + getType(): EntityType          «abstract»                              │
│ + isWalkableBy(actor: BoardEntity): boolean   (default: false)           │
│ + isBoxTraversable(box: BoardEntity): boolean (default: false)           │
└──────┬───────────────────────┬──────────────────────────────────────────┘
       │                       │
┌──────┴──────────┐   ┌────────┴────────────────────────────────────────┐
│ «abstract» Floor│   │ «abstract» Box      Sokoban / Wall / Gate / Lock │
│─────────────────│   │─────────────────                                 │
│ + isWalkable()  │   │ + canBePushed()     Sokoban: getType()=SOKOBAN   │
│ + isGoal()      │   │ + onPushed(Board)   Wall: bloqueante              │
│ + propagates    │   │                    Gate: -isOpen; open()/close() │
│     Slide()     │   │                    Lock: solo Sokoban/KeyBox      │
│ + onBoxLanded   │   │                                                   │
│   (Box,Dir,Brd) │   │                                                   │
└──────┬──────────┘   └────────┬────────────────────────────────────────┘
       │                       │
  ┌────┴────────┐         ┌────┴──────────────────────────┐
  │             │         │                               │
  ▼             ▼         ▼           ▼                   ▼
Empty         Target   NormalBox  FragileBox           KeyBox
Slippery               canBePushed  -resistance:int    onPushed→
                       =true        canBePushed si >0  board.openAllGates
isGoal=false  isGoal   onPushed:    onPushed: decr.    si hasLockAt
              =true    no-op        + removeEntity


┌──────────────────────────────────────────────────────────────────────────┐
│                              Position                                    │
│──────────────────────────────────────────────────────────────────────────│
│ - row: int                                                               │
│ - col: int                                                               │
│──────────────────────────────────────────────────────────────────────────│
│ + getRow(): int                                                          │
│ + getCol(): int                                                          │
│ + translate(dir: Direction): Position                                    │
│ + equals / hashCode                                                      │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│                          «enum» Direction                                │
│──────────────────────────────────────────────────────────────────────────│
│ UP(-1,0) | DOWN(1,0) | LEFT(0,-1) | RIGHT(0,1)                          │
│ + getDeltaRow(): int                                                     │
│ + getDeltaCol(): int                                                     │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│                               Board                                      │
│──────────────────────────────────────────────────────────────────────────│
│ - entities: List<BoardEntity>                                            │
│ - rows: int                                                              │
│ - cols: int                                                              │
│──────────────────────────────────────────────────────────────────────────│
│ + getEntityAt(pos: Position): BoardEntity                                │
│ + getFloorAt(pos: Position): Floor                                       │
│ + getBoxes(): List<Box>                                                  │
│ + hasLockAt(pos: Position): boolean                                      │
│ + openAllGates(): void                                                   │
│ + isInside(pos: Position): boolean                                       │
│ + removeEntity(e: BoardEntity): void                                     │
│ + addEntity(e: BoardEntity): void                                        │
└──────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────┐      ┌──────────────────────────────────────┐
│      LevelParser        │      │         BoardEntityFactory           │
│─────────────────────────│      │──────────────────────────────────────│
│ + parse(path): Board    │─────►│ + create(c: char, pos): List<Entity> │
│ - readLines(path): List │      │   switch(c) → instancias             │
└─────────────────────────┘      └──────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│                             GameEngine                                   │
│──────────────────────────────────────────────────────────────────────────│
│ - board: Board                                                           │
│ - sokoban: Sokoban                                                       │
│──────────────────────────────────────────────────────────────────────────│
│ + move(dir: Direction): MoveResult                                       │
│ + checkVictory(): boolean                                                │
│ - tryPushBox(box: Box, dir: Direction): boolean                          │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│                             MoveResult                                   │
│──────────────────────────────────────────────────────────────────────────│
│ - moved: boolean                                                         │
│ - pushed: boolean                                                        │
│──────────────────────────────────────────────────────────────────────────│
│ + isMoved(): boolean                                                     │
│ + isPushed(): boolean                                                    │
│ + blocked(): MoveResult   «static factory»                               │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│                              GameStats                                   │
│──────────────────────────────────────────────────────────────────────────│
│ - movimientos: int                                                       │
│ - empujes: int                                                           │
│──────────────────────────────────────────────────────────────────────────│
│ + record(result: MoveResult): void                                       │
│ + getMovimientos(): int                                                  │
│ + getEmpujes(): int                                                      │
│ + reset(): void                                                          │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│                           GameController                                 │
│──────────────────────────────────────────────────────────────────────────│
│ - engine: GameEngine                                                     │
│ - stats: GameStats                                                       │
│ - levelParser: LevelParser                                               │
│ - nivelActual: int                                                       │
│──────────────────────────────────────────────────────────────────────────│
│ + loadLevel(path: String, nivel: int): void                              │
│ + handleInput(dir: Direction): void                                      │
│ + getCurrentState(): GameStateDTO                                        │
│ + isVictoria(): boolean                                                  │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐   ┌──────────────────────┐   ┌────────────────────┐
│    SokobanFrame      │   │      GamePanel        │   │     HUDPanel       │
│──────────────────────│   │──────────────────────│   │────────────────────│
│ - gamePanel          │   │ + update(dto): void   │   │ + update(dto): void│
│ - hudPanel           │   │ + paintComponent(g)   │   │ movimientos, nivel │
└──────────────────────┘   │ implements KeyListener│   └────────────────────┘
                           └──────────────────────┘

┌─────────────────────────────────────┐   ┌─────────────────────────────┐
│           GameStateDTO              │   │       BoardEntityDTO         │
│─────────────────────────────────────│   │─────────────────────────────│
│ + entities: List<BoardEntityDTO>    │──►│ + position: Position        │
│ + rows: int                         │   │ + tipo: EntityType          │
│ + cols: int                         │   └─────────────────────────────┘
│ + movimientos: int                  │
│ + empujes: int                      │   ┌─────────────────────────────┐
│ + nivelActual: int                  │   │      «enum» EntityType      │
│ + victoria: boolean                 │   │─────────────────────────────│
└─────────────────────────────────────┘   │ WALL, EMPTY, SOKOBAN,       │
                                          │ NORMAL_BOX, FRAGILE_BOX,    │
                                          │ KEY_BOX, TARGET, SLIPPERY,  │
                                          │ LOCK, GATE_OPEN, GATE_CLOSED│
                                          │─────────────────────────────│
                                          │ + getSymbol(): char         │
                                          │ + getRenderPriority(): int  │
                                          └─────────────────────────────┘
```

---

## 6. Patrones y principios aplicados

### SOLID

| Principio | Aplicación |
|---|---|
| **SRP** | `LevelParser` solo parsea. `BoardEntityFactory` solo crea. `GameEngine` solo ejecuta lógica de movimiento. `GameController` solo coordina. `GameStats` solo lleva contadores. |
| **OCP** | `BoardEntity` expone métodos polimórficos (`isWalkableBy`, `isBoxTraversable`, `getType`, `isGoal`, `propagatesSlide`, `onBoxLanded`, `onPushed`). Agregar una entidad nueva no requiere modificar `GameEngine`, `Board` ni el controller. |
| **LSP** | `FragileBox`, `NormalBox` y `KeyBox` son intercambiables donde se espera `Box`. `Empty`, `Slippery`, `Target` son intercambiables donde se espera `Floor`. |
| **ISP** | `Floor` tiene métodos de suelo (`isWalkable`, `isGoal`, `propagatesSlide`, `onBoxLanded`). `Box` tiene métodos de caja (`canBePushed`, `onPushed`). Interfaces pequeñas y cohesivas. |
| **DIP** | `GameController` y `GameEngine` dependen de abstracciones (`Board`, `BoardEntity`, `Box`, `Floor`). `LevelParser` recibe `BoardEntityFactory` por constructor. |

### GRASP

| Patrón | Aplicación |
|---|---|
| **Information Expert** | `Board` sabe qué hay en cada posición (`getEntityAt`, `hasLockAt`, `openAllGates`). `FragileBox` sabe su resistencia. `Gate` sabe si está abierto. `Lock` sabe quién puede atravesarlo. `Target` sabe que es un objetivo (`isGoal`). `Slippery` sabe cómo deslizar cajas (`onBoxLanded`). |
| **Creator** | `LevelParser` crea el `Board`. `BoardEntityFactory` crea las entidades. |
| **Controller** | `GameController` recibe el input y coordina sin lógica de negocio. Delega movimiento a `GameEngine`, contadores a `GameStats`, tipos a `entity.getType()`. |
| **Low Coupling** | `Sokoban` no conoce `Board`. `GamePanel` no conoce el modelo, solo el DTO. `KeyBox` no conoce `Gate` ni `Lock` directamente; opera sobre `Board` por su interfaz pública. |
| **High Cohesion** | Cada clase tiene una responsabilidad clara. `GameStats` fue extraído del controller para no mezclar coordinación con contabilidad. |
| **Polymorphism** | `GameEngine` no usa `instanceof` para decidir comportamiento: llama a `target.isWalkableBy(sokoban)`, `occupant.isBoxTraversable(box)`, `floor.isGoal()`, `floor.onBoxLanded(...)`, `box.onPushed(board)`. Cada entidad decide su propio comportamiento. |
| **Protected Variations** | Agregar una entidad nueva (`ExplosiveBox`, `IceFloor`) requiere solo: la nueva clase + un `case` en la factory + un valor en `EntityType`. Ningún otro archivo se modifica. |

### GOF

| Patrón | Clase | Descripción |
|---|---|---|
| **Simple Factory** | `BoardEntityFactory` | Crea entidades según el carácter leído del `.txt`. Retorna `List<BoardEntity>` para soportar entidades superpuestas (ej: `Empty` + `Sokoban` en la misma celda). |
| **Strategy** | `Box` y subclases, `Floor` y subclases | `Box.onPushed(Board)` encapsula el comportamiento completo al ser empujada: `FragileBox` se auto-destruye, `KeyBox` abre gates, `NormalBox` no hace nada. `Floor.onBoxLanded(...)` encapsula la reacción del suelo: `Slippery` desliza la caja, el resto no hace nada. |
| **Value Object** | `Position` | Objeto inmutable con `equals`/`hashCode`. `translate()` retorna nueva instancia. |
| **Value Object** | `MoveResult` | Objeto inmutable que transporta el resultado de un movimiento sin exponer el estado del modelo. |

### MVC

| Capa | Clases |
|---|---|
| **Modelo** | `Board`, `BoardEntity` y subclases, `GameEngine`, `GameStats`, `MoveResult`, `LevelParser`, `BoardEntityFactory`, `Position`, `Direction` |
| **Vista** | `SokobanFrame`, `GamePanel`, `HUDPanel` |
| **Controlador** | `GameController` |
| **DTO** | `GameStateDTO`, `BoardEntityDTO`, `EntityType` |

---

## 7. Flujo de un turno de juego

```
1. Usuario presiona una tecla (↑ ↓ ← →)
2. GamePanel.keyPressed(KeyEvent) captura el evento
3. Convierte el KeyEvent a Direction
4. Llama a GameController.handleInput(dir)
5. GameController llama a GameEngine.move(dir)
6. GameEngine:
   a. Calcula newPos = sokoban.getPosition().translate(dir)
   b. Consulta board.getEntityAt(newPos)
   c. Si la entidad es Box → tryPushBox(box, dir):
      - Consulta board.getEntityAt(destino) → occupant.isBoxTraversable(box)
      - Mueve la caja: box.setPosition(destino)
      - box.onPushed(board) [FragileBox se destruye; KeyBox abre gates si hay Lock]
      - floor.onBoxLanded(box, dir, board) [Slippery desliza la caja]
   d. Si no es Box → target.isWalkableBy(sokoban) decide si puede moverse
   e. Mueve al sokoban: sokoban.setPosition(newPos)
   f. Retorna MoveResult(moved, pushed)
7. GameController pasa MoveResult a GameStats.record()
8. GameController llama a GameEngine.checkVictory()
   - Para cada Box: floor.isGoal() debe ser true
9. GameController construye GameStateDTO con entity.getType() de cada entidad
10. GameController llama a gamePanel.update(dto) y hudPanel.update(dto)
11. Ambos paneles llaman a repaint()
```

---

## 8. Flujo de carga de nivel

```
1. GameController.loadLevel(path, nivelActual)
2. LevelParser.parse(path)
3. Por cada línea del .txt:
   Por cada char c en la línea:
     a. Crea Position(row, col)
     b. Llama a BoardEntityFactory.create(c, pos) → List<BoardEntity>
     c. switch(c) → [Empty + Sokoban] / [Empty + NormalBox] / [Wall] / [Target] / etc.
     d. Agrega todas las entidades a la lista
4. Retorna new Board(entities, rows, cols)
5. GameController localiza la instancia Sokoban del Board
6. GameController crea new GameEngine(board, sokoban)
7. GameStats.reset()
```

---

## 9. Reglas de negocio

### Movimiento del jugador
- `target.isWalkableBy(sokoban)` determina si Sokoban puede entrar a una celda
  - `Floor` (Empty, Target, Slippery): true
  - `Lock`: true (solo para Sokoban)
  - `Gate`: true solo si `isOpen`
  - `Wall`, `Box`: false

### Tipos de caja
- **NormalBox**: `canBePushed()` = true. `onPushed` no hace nada.
- **FragileBox**: `canBePushed()` true si `resistance > 0`. `onPushed` decrementa resistencia y se auto-elimina del board si llega a 0.
- **KeyBox**: `canBePushed()` = true. `onPushed` consulta `board.hasLockAt(pos)` y llama `board.openAllGates()`.

### Terreno resbaladizo
- `Slippery.onBoxLanded(box, dir, board)` desliza la caja en la misma dirección mientras `occupant.isBoxTraversable(box)` sea true y el suelo siguiente tenga `propagatesSlide() = true`.

### Victoria
- `GameEngine.checkVictory()` retorna true cuando para cada `Box` en el board, `board.getFloorAt(box.getPosition()).isGoal()` es true.

---

## 10. Estructura de paquetes

```
src/
├── Main.java                          (punto de entrada, sin package)
└── sokoban/
    ├── model/
    │   ├── entity/
    │   │   ├── BoardEntity.java        (abstract)
    │   │   ├── Sokoban.java
    │   │   ├── floor/
    │   │   │   ├── Floor.java          (abstract)
    │   │   │   ├── Empty.java
    │   │   │   ├── Slippery.java       (onBoxLanded: deslizamiento)
    │   │   │   └── Target.java         (isGoal = true)
    │   │   ├── box/
    │   │   │   ├── Box.java            (abstract)
    │   │   │   ├── NormalBox.java
    │   │   │   ├── FragileBox.java     (auto-destrucción en onPushed)
    │   │   │   └── KeyBox.java         (abre gates en onPushed)
    │   │   └── obstacle/
    │   │       ├── Wall.java
    │   │       ├── Gate.java           (isOpen, isWalkableBy, isBoxTraversable)
    │   │       └── Lock.java           (isWalkableBy: Sokoban; isBoxTraversable: KeyBox)
    │   ├── Board.java
    │   ├── Position.java               (Value Object)
    │   ├── Direction.java              (enum con deltas)
    │   ├── GameEngine.java
    │   ├── GameStats.java
    │   ├── MoveResult.java             (Value Object)
    │   └── factory/
    │       ├── LevelParser.java
    │       └── BoardEntityFactory.java
    ├── controller/
    │   └── GameController.java
    ├── view/
    │   ├── SokobanFrame.java           (pendiente)
    │   ├── GamePanel.java              (pendiente)
    │   └── HUDPanel.java               (pendiente)
    └── dto/
        ├── EntityType.java             (enum: symbol + renderPriority)
        ├── GameStateDTO.java
        └── BoardEntityDTO.java
```

---

## 11. Pendiente de implementar

- [ ] Vista Swing: `SokobanFrame`, `GamePanel` (KeyListener + render), `HUDPanel`
- [ ] Sistema de Undo (Command o Memento, últimos 15 movimientos, 5 por uso, máximo 3 usos)
- [ ] Sistema de puntaje al finalizar cada nivel
- [ ] Dos funcionalidades adicionales con patrones GOF
- [ ] Integración de assets gráficos (imágenes por tipo de entidad)
- [ ] Efectos de sonido por acción

---

## 12. Criterios de evaluación (recordatorio)

- Correcta aplicación de SOLID, GRASP, MVC y GOF
- Calidad y legibilidad del código
- Documentación y decisiones de diseño justificadas
- **Defensa individual** exhaustiva de cada decisión
- Uso de IA declarado en el informe técnico
