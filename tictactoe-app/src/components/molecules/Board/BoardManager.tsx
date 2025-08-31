import type { PlayerType } from "@/lib/types/PlayerType";
import { useGameStatus } from "@/lib/hooks/useGameStatus";
import { BoardManagerRenderer } from "./BoardManagerRenderer";

type BoardManagerProps = {
  matchId: string;
  playerId: PlayerType
}
export const BoardManager = ({
  matchId, 
  playerId
}: BoardManagerProps) => {
  const { data, makeMove } = useGameStatus(matchId);

  const handleMove = (x: number, y: number) => {
    console.log(`Move made at (${x}, ${y}) by player ${data?.currentTurn}`);
    makeMove(x, y, playerId, matchId);
  };

  return <BoardManagerRenderer playerId={playerId} data={data} onMove={handleMove} />;
};
