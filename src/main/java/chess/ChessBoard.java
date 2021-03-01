package chess;

import org.jetbrains.annotations.NotNull;

import static chess.Figure.FigureColors.BLACK;
import static chess.Figure.FigureColors.WHITE;
import static chess.Figure.FigureTypes.KING;

// Класс, описывающий шахматную доску и допустимые операции над ним
public class ChessBoard {

    // Класс, описывающий отдельную клетку - поле шахматной доски
    public static class Field {
        private final int vertical;
        private final int horizontal;

        // Vertical is column
        // horizontal is row
        public Field(int horizontal, int vertical) {
            if (horizontal < 0 || horizontal > 7)
                throw new IllegalArgumentException("Horizontal must be in 0..7 but was: " + horizontal);
            if (vertical < 0 || vertical > 7)
                throw new IllegalArgumentException("Vertical must be in 0..7 but was: " + vertical);

            this.horizontal = horizontal;
            this.vertical = vertical;
        }

        public int getVertical() {
            return vertical;
        }

        public int getHorizontal() {
            return horizontal;
        }
    }

    private static final int MAX_PAWNS = 8;


    private final Figure[][] board;


    public ChessBoard(@NotNull Field whiteKing, @NotNull Field blackKing) {
        if (Math.abs(whiteKing.horizontal - blackKing.horizontal) <= 1 && Math.abs(whiteKing.vertical - blackKing.vertical) <= 1)
            throw new IllegalArgumentException("Kings must not stay at neighbours fields!");

        board = new Figure[8][8];

        board[whiteKing.getHorizontal()][whiteKing.getVertical()] = new Figure(KING, WHITE);
        board[blackKing.getHorizontal()][blackKing.getVertical()] = new Figure(KING, BLACK);

    }


    public void clearField(@NotNull Field field) {
        if (figure(field) != null && figure(field).getType() == KING) {
            throw new IllegalArgumentException("Clearing of the field with a king is not allowed!");
        }

        board[field.horizontal][field.vertical] = null;
    }

    public void addFigure(@NotNull Figure figure, @NotNull Field field) {
        if (figure(field) != null)
            throw new IllegalArgumentException("Failure to add the figure! Field is already busy!");

        switch (figure.getType()) {
            case KING -> throw new IllegalArgumentException("Failure to add the king on the board! Already plays 1 king!");
            case PAWN -> {
                if (countOf(figure) == MAX_PAWNS)
                    throw new IllegalArgumentException("Failure to add the pawn on the board! Already plays " + MAX_PAWNS + " pawns!");
            }
        }

        board[field.horizontal][field.vertical] = figure;
    }

    public void moveFigure(@NotNull Field from, @NotNull Field to) {
        if (figure(from) == null)
            throw new IllegalArgumentException("Failure to move the figure! Field 'from' is free!");

        if (figure(to) != null)
            throw new IllegalArgumentException("Failure to move the figure! Field 'to' is already busy!");

        if (figure(from).getType() == KING) {
            if (isKingNear(to, figure(from).getColor() == WHITE ? BLACK : WHITE))
                throw new IllegalArgumentException("Failure to move the figure! Kings must not stay at neighbours fields!");
        }

        board[to.horizontal][to.vertical] = figure(from);
        board[from.horizontal][from.vertical] = null;
    }

    private int countOf(@NotNull Figure figure) {
        int count = 0;

        for (int horizontal = 0; horizontal < board.length; horizontal++) {
            for (int vertical = 0; vertical < board[horizontal].length; vertical++) {
                if (figure.equals(figure(horizontal, vertical)))
                    count++;
            }
        }
        return count;
    }

    private boolean isKingNear(Field field, Figure.FigureColors color) {
        for (int i = field.horizontal - 1; i < field.horizontal + 2; i++) {
            for (int j = field.vertical - 1; j < field.vertical + 2; j++) {
                if (0 <= i && i <= 7 && 0 <= j && j <= 7)
                    if (figure(i, j) != null && figure(i, j).getType() == KING && figure(i, j).getColor() == color)
                        return true;
            }
        }
        return false;
    }

    private Figure figure(@NotNull Field field) {
        return board[field.horizontal][field.vertical];
    }

    private Figure figure(int horizontal, int vertical) {
        return board[horizontal][vertical];
    }

}
