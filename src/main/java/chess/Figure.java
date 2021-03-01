package chess;

import org.jetbrains.annotations.NotNull;

// Класс, описывающий шахматную фигуру (тип и цвет)
public class Figure {
    // Перечисление, описывающее тип фигуры
    public enum FigureTypes {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }

    // Перечисление, описывающее цвет фигуры
    public enum FigureColors {
        WHITE, BLACK
    }

    private final FigureTypes type;
    private final FigureColors color;

    public Figure(@NotNull FigureTypes figureType, @NotNull FigureColors figureColor) {
        this.type = figureType;
        this.color = figureColor;
    }

    public FigureTypes getType() {
        return type;
    }

    public FigureColors getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Figure figure = (Figure) o;

        if (getType() != figure.getType()) return false;
        return color == figure.color;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}
