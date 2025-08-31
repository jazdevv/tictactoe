// getWinningCells.test.ts
import { getWinningCells } from "./utils"; // adjust path
import type { Movement, CellPosition } from "@/lib/types/Movement";

describe("getWinningCells", () => {
  test("returns empty array if no winner", () => {
    // given
    const moves: Movement[] = [
      { x: 1, y: 1, playerId: "X" },
      { x: 2, y: 1, playerId: "O" },
    ];

    // when
    const result: CellPosition[] = getWinningCells(moves);

    // then
    expect(result).toEqual([]);
  });

  test("returns correct winning line for a row", () => {
    // given
    const moves: Movement[] = [
      { x: 1, y: 1, playerId: "X" },
      { x: 2, y: 1, playerId: "X" },
      { x: 3, y: 1, playerId: "X" },
    ];

    // when
    const result: CellPosition[] = getWinningCells(moves);

    // then
    expect(result).toEqual([
      { x: 1, y: 1 },
      { x: 2, y: 1 },
      { x: 3, y: 1 },
    ]);
  });

  test("returns correct winning line for a diagonal", () => {
    // given
    const moves: Movement[] = [
      { x: 1, y: 1, playerId: "O" },
      { x: 2, y: 2, playerId: "O" },
      { x: 3, y: 3, playerId: "O" },
    ];

    // when
    const result: CellPosition[] = getWinningCells(moves);

    // then
    expect(result).toEqual([
      { x: 1, y: 1 },
      { x: 2, y: 2 },
      { x: 3, y: 3 },
    ]);
  });
});
