import { mockUseTranslation } from "@/lib/test-utils/mocki18n";
mockUseTranslation();

import { Movement } from "@/lib/types/Movement";
import { fireEvent, render } from "@testing-library/react";
import Board from "./Board";
import { BoardCoordinateValues } from "@/lib/const/BoardCoordinateValues";


describe("Board component", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test("renders board with cells without movements", () => {
    // given
    const movements: Movement[] = [];
    const board = render(
      <Board
        moves={movements}
        finished={false}
        clickable={true}
        onMove={() => {}}
      />
    );

    // then
    for (const row of BoardCoordinateValues) {
      for (const col of BoardCoordinateValues) {
        const cell = board.getByTestId(`cell-${row}-${col}`);
        expect(cell).toHaveTextContent("");
      }
    }
  });

  test("renders overlay text on opponents turn (blocked and !finished)", () => {
    // given
    const movements: Movement[] = [];
    const { container } = render(
      <Board
        moves={movements}
        finished={false}
        clickable={false}
        blocked={true}
        onMove={() => {}}
      />
    );

    // then
    const overlay = container.querySelector(".overlay");
    expect(overlay).toBeInTheDocument();
  });

  test("fires onClick event on available Cells", () => {
    // given
    const onClick = jest.fn();

    const board = render(
      <Board moves={[]} finished={false} clickable={true} onMove={onClick} />
    );

    // when
    const firstCell = board.getByTestId(
      `cell-${BoardCoordinateValues[0]}-${BoardCoordinateValues[0]}`
    );
    fireEvent.click(firstCell);

    // then
    expect(onClick).toHaveBeenCalledWith(
      BoardCoordinateValues[0],
      BoardCoordinateValues[0]
    );
  });

  test("highlight winning cells on game finished", () => {
    // given
    const movements: Movement[] = [
      { x: 1, y: 1, playerId: "X" },
      { x: 2, y: 2, playerId: "X" },
      { x: 3, y: 3, playerId: "X" },
    ];

    const board = render(
      <Board
        moves={movements}
        finished={true}
        clickable={false}
        onMove={() => {}}
      />
    );

    // then
    const winningCells = movements.map(
      (move) => board.getByTestId(`cell-${move.x}-${move.y}`)
    );

    for (const cell of winningCells) {
      expect(cell).toHaveClass("highlight");
    }
  });
});
