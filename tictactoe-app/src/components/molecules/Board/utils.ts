import type { CellPosition, Movement } from "@/lib/types/Movement";

/**
 * Returns an array of cell positions that form the winning combination.
 * Returns an empty array if no winner.
 */
export function getWinningCells(moves: Movement[]): CellPosition[] {
  const board: ("X" | "O" | null)[][] = [
    [null, null, null],
    [null, null, null],
    [null, null, null],
  ];

  moves.forEach(({ x, y, playerId }) => {
    board[y - 1][x - 1] = playerId;
  });

  const lines: CellPosition[][] = [
    // Rows
    [{ x: 1, y: 1 }, { x: 2, y: 1 }, { x: 3, y: 1 }],
    [{ x: 1, y: 2 }, { x: 2, y: 2 }, { x: 3, y: 2 }],
    [{ x: 1, y: 3 }, { x: 2, y: 3 }, { x: 3, y: 3 }],
    // Columns
    [{ x: 1, y: 1 }, { x: 1, y: 2 }, { x: 1, y: 3 }],
    [{ x: 2, y: 1 }, { x: 2, y: 2 }, { x: 2, y: 3 }],
    [{ x: 3, y: 1 }, { x: 3, y: 2 }, { x: 3, y: 3 }],
    // Diagonals
    [{ x: 1, y: 1 }, { x: 2, y: 2 }, { x: 3, y: 3 }],
    [{ x: 1, y: 3 }, { x: 2, y: 2 }, { x: 3, y: 1 }],
  ];

  for (const line of lines) {
    const [a, b, c] = line;
    const v1 = board[a.y - 1][a.x - 1];
    const v2 = board[b.y - 1][b.x - 1];
    const v3 = board[c.y - 1][c.x - 1];
    if (v1 && v1 === v2 && v1 === v3) {
      return line; // winning cells
    }
  }

  return [];
}
