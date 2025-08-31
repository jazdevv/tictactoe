import { render, screen, fireEvent } from "@testing-library/react";
import Cell from "./Cell";

describe("Cell component", () => {
  test("renders empty cell correctly", () => {
    // given
    const handleClick = jest.fn();

    render(<Cell x={1} y={1} clickable={true} onClick={handleClick} />);

    const cell = screen.getByTestId("cell-1-1");

    // then
    expect(cell).toHaveClass("cell clickable");
    expect(cell).not.toHaveClass("filled highlight x-cell o-cell");
  });

  test("renders cell value", () => {
    // given
    const handleClick = jest.fn();

    render(<Cell x={1} y={1} value="X" clickable={true} onClick={handleClick} />);

    const cell = screen.getByTestId("cell-1-1");

    // then
    expect(cell).toHaveTextContent("X");
  });

  test("calls onClick when empty clickable cell is clicked", () => {
    // given
    const handleClick = jest.fn();

    render(<Cell x={1} y={1} clickable={true} onClick={handleClick} />);

    const cell = screen.getByTestId("cell-1-1");

    // when
    fireEvent.click(cell);

    // then
    expect(handleClick).toHaveBeenCalled();
  });

  test("renders X cell and does not call onClick when clicked because cell is filled", () => {
    // given
    const handleClick = jest.fn();

    render(<Cell x={1} y={1} value="X" clickable={true} onClick={handleClick} />);

    const cell = screen.getByTestId("cell-1-1");

    // when
    fireEvent.click(cell);

    // then
    expect(cell).toHaveClass("cell filled x-cell");
    expect(handleClick).not.toHaveBeenCalled();
  });

  test("renders O cell with highlight", () => {
    // given
    const handleClick = jest.fn();

    render(<Cell x={1} y={1} value="O" highlight={true} clickable={true} onClick={handleClick} />);

    const cell = screen.getByTestId("cell-1-1");

    // then
    expect(cell).toHaveClass("cell filled o-cell highlight");
  });
});
