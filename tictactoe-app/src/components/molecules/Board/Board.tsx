import React from "react";
import Cell from "@/components/atoms/Cell/Cell";
import "./Board.scss";

interface Move {
  playerId: "X" | "O";
  x: number;
  y: number;
}

interface BoardProps {
  moves: Move[];
  clickable: boolean;
  blocked?: boolean; // nuevo prop
  onMove: (x: number, y: number) => void;
}

const Board: React.FC<BoardProps> = ({ moves, clickable, blocked = false, onMove }) => {
  const getCellValue = (x: number, y: number) => {
    const move = moves.find((m) => m.x === x && m.y === y);
    return move?.playerId;
  };

  return (
    <div className={`board ${blocked ? "blocked" : ""}`}>
      {[1, 2, 3].map((y) => (
        <div className="board-row" key={y}>
          {[1, 2, 3].map((x) => (
            <Cell
              key={`${x}-${y}`}
              x={x}
              y={y}
              value={getCellValue(x, y)}
              clickable={clickable && !blocked}
              onClick={onMove}
            />
          ))}
        </div>
      ))}
      {blocked && <div className="overlay">Espere a su turno</div>}
    </div>
  );
};

export default Board;
