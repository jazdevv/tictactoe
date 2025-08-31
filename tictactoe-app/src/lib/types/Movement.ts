export type Movement = {
  playerId: "X" | "O";
} & CellPosition;

export type CellPosition = {
  x: number;
  y: number;
};