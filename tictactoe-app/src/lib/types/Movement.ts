import { BoardCoordinateValuesType } from "@/lib/types/BoardCoordinateValuesType";

export type Movement = {
  playerId: "X" | "O";
} & CellPosition;

export type CellPosition = {
  x: BoardCoordinateValuesType;
  y: BoardCoordinateValuesType;
};