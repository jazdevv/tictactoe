import React from "react";
import Cell from "@/components/atoms/Cell";

interface Move {
  playerId: "X" | "O";
  x: number;
  y: number;
}

interface BoardProps {
  moves: Move[];
  clickable: boolean;
  onMove: (x: number, y: number) => void;
}

const Board: React.FC<BoardProps> = ({ moves, clickable, onMove }) => {
  // Build a 3x3 grid from moves
  const getCellValue = (x: number, y: number) => {
    const move = moves.find((m) => m.x === x && m.y === y);
    return move?.playerId;
  };

  const renderRow = (y: number) => (
    <div style={{ display: "flex" }} key={y}>
      {[1, 2, 3].map((x) => (
        <Cell
          key={`${x}-${y}`}
          x={x}
          y={y}
          value={getCellValue(x, y)}
          clickable={clickable}
          onClick={onMove}
        />
      ))}
    </div>
  );

  return <div>{[1, 2, 3].map(renderRow)}</div>;
};

export default Board;
