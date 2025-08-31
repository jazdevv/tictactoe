import React, { useMemo } from "react";
import Cell from "@/components/atoms/Cell/Cell";
import "./Board.scss";
import { useTranslation } from "react-i18next";
import { getWinningCells } from "./utils";
import type { Movement } from "@/lib/types/Movement";


interface BoardProps {
  moves: Movement[];
  finished?: boolean;
  clickable: boolean;
  blocked?: boolean; // nuevo prop
  onMove: (x: number, y: number) => void;
}

const Board: React.FC<BoardProps> = ({ moves, clickable, blocked = false, finished = false, onMove }) => {
  const { t } = useTranslation();
  const getCellValue = (x: number, y: number) => {
    const move = moves.find((m) => m.x === x && m.y === y);
    return move?.playerId;
  };

  const winningCells = useMemo(() => (finished ? getWinningCells(moves) : []), [moves, finished]);

  const isWinningCell = (x: number, y: number) =>
    winningCells.some((cell) => cell.x === x && cell.y === y);

  return (
    <div className={`board ${blocked ? "blocked" : ""}`}>
      {[1, 2, 3].map((y) => (
        <div className="board-row" key={y}>
          {[1, 2, 3].map((x) => (
            <Cell
              key={`${x}-${y}`}
              x={x}
              y={y}
              highlight={isWinningCell(x, y)}
              value={getCellValue(x, y)}
              clickable={clickable && !blocked}
              onClick={onMove}
            />
          ))}
        </div>
      ))}
      {!finished && blocked && <div className="overlay">{t("waitForYourTurn")}</div>}
    </div>
  );
};

export default Board;
