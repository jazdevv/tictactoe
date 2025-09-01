import React, { useMemo } from "react";
import Cell from "@/components/atoms/Cell/Cell";
import "./Board.scss";
import { useTranslation } from "react-i18next";
import { getWinningCells } from "./utils";
import type { Movement } from "@/lib/types/Movement";
import { BoardCoordinateValues } from "@/lib/const/BoardCoordinateValues";
import { BoardCoordinateValuesType } from "@/lib/types/BoardCoordinateValuesType";


interface BoardProps {
  moves: Movement[];
  finished?: boolean;
  clickable: boolean;
  blocked?: boolean; // nuevo prop
  onMove: (x: BoardCoordinateValuesType, y: BoardCoordinateValuesType) => void;
}

const Board: React.FC<BoardProps> = ({ moves, clickable, blocked = false, finished = false, onMove }) => {
  const { t } = useTranslation();
  const getCellValue = (x: BoardCoordinateValuesType, y: BoardCoordinateValuesType) => {
    const move = moves.find((m) => m.x === x && m.y === y);
    return move?.playerId;
  };

  const winningCells = useMemo(() => (finished ? getWinningCells(moves) : []), [moves, finished]);

  const isWinningCell = (x: BoardCoordinateValuesType, y: BoardCoordinateValuesType) =>
    winningCells.some((cell) => cell.x === x && cell.y === y);

  return (
    <div className={`board ${blocked ? "blocked" : ""}`}>
      {BoardCoordinateValues.map((y) => (
        <div className="board-row" key={y}>
          {BoardCoordinateValues.map((x) => (
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
