import chess.ChessBoard;
import chess.ChessBoard.Field;
import chess.Figure;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ChessBoardTest {

    // Ура :)
    @Test
    public void constructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChessBoard(new Field(0, 0), new Field(0, 0)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChessBoard(new Field(0, 0), new Field(1, 1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChessBoard(new Field(1, 0), new Field(0, 1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChessBoard(new Field(-1, 0), new Field(2, 2)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ChessBoard(new Field(1, 1), new Field(8, 8)));
        Assertions.assertDoesNotThrow(() -> new ChessBoard(new Field(1, 0), new Field(0, 2)));
        Assertions.assertDoesNotThrow(() -> new ChessBoard(new Field(1, 1), new Field(7, 7)));
    }

    @Test
    public void addFigure() {
        ChessBoard board = new ChessBoard(new Field(0, 0), new Field(7, 7));
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.addFigure(new Figure(Figure.FigureTypes.KING, Figure.FigureColors.WHITE), new Field(3, 3)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.addFigure(new Figure(Figure.FigureTypes.KING, Figure.FigureColors.BLACK), new Field(3, 3)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.addFigure(new Figure(Figure.FigureTypes.PAWN, Figure.FigureColors.WHITE), new Field(0, 0)));
        Assertions.assertDoesNotThrow(() -> board.addFigure(new Figure(Figure.FigureTypes.PAWN, Figure.FigureColors.BLACK), new Field(3, 3)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.addFigure(new Figure(Figure.FigureTypes.PAWN, Figure.FigureColors.BLACK), new Field(3, 3)));
        Assertions.assertDoesNotThrow(() -> board.addFigure(new Figure(Figure.FigureTypes.BISHOP, Figure.FigureColors.WHITE), new Field(2, 4)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.addFigure(new Figure(Figure.FigureTypes.PAWN, Figure.FigureColors.WHITE), new Field(2, 4)));

    }

    @Test
    public void moveFigure() {
        ChessBoard board = new ChessBoard(new Field(0, 0), new Field(7, 7));
        board.addFigure(new Figure(Figure.FigureTypes.BISHOP, Figure.FigureColors.WHITE), new Field(1, 3));
        board.addFigure(new Figure(Figure.FigureTypes.QUEEN, Figure.FigureColors.BLACK), new Field(4, 1));

        // перемещение пустой клетки
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.moveFigure(new Field(0, 5), new Field(6, 3)));

        // перемещение слона на место короля
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.moveFigure(new Field(1, 3), new Field(0, 0)));

        // перемещение черного короля на поле белого
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.moveFigure(new Field(7, 7), new Field(0, 0)));

        // перемещение белого короля на пустую клетку
        Assertions.assertDoesNotThrow(() -> board.moveFigure(new Field(0, 0), new Field(4, 4)));
        Assertions.assertDoesNotThrow(() -> board.moveFigure(new Field(4, 4), new Field(2, 7)));
        // поле занято
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.moveFigure(new Field(2, 7), new Field(2, 7)));

    }

    @Test
    public void clearField() {
        ChessBoard board = new ChessBoard(new Field(0, 0), new Field(7, 7));
        board.addFigure(new Figure(Figure.FigureTypes.BISHOP, Figure.FigureColors.WHITE), new Field(1, 3));
        board.addFigure(new Figure(Figure.FigureTypes.QUEEN, Figure.FigureColors.BLACK), new Field(4, 1));

        // удаление короля с поля
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.clearField(new Field(0, 0)));

        board.moveFigure(new Field(1, 3), new Field(2, 5));
        Assertions.assertDoesNotThrow(() -> board.clearField(new Field(2, 5)));
        // очистка пустого поля
        Assertions.assertDoesNotThrow(() -> board.clearField(new Field(2, 5)));


    }
}
