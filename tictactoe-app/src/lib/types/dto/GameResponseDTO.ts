import type { GameStatus } from "../GameStatus";
import type { Movement } from "../Movement";
import type { PlayerType } from "../PlayerType";

export type GameResponseDTO = {
  status: GameStatus;
  currentTurn: PlayerType;
  winner: PlayerType | null | "DRAW";
  movements: Array<Movement>;
}