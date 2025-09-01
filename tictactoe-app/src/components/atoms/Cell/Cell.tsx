import React from "react";
import "./Cell.scss";
import { type MovementValuesType } from "@/lib/types/MovementValuesType";
import { BoardCoordinateValuesType } from "@/lib/types/BoardCoordinateValuesType";

interface CellProps {
  x: BoardCoordinateValuesType;
  y: BoardCoordinateValuesType;
  value?: MovementValuesType;
  highlight?: boolean;
  clickable: boolean;
  onClick: (x: BoardCoordinateValuesType, y: BoardCoordinateValuesType) => void;
}

const Cell: React.FC<CellProps> = ({
  x,
  y,
  value,
  highlight = false,
  clickable,
  onClick,
}) => {
  const handleClick = () => {
    if (!value && clickable) {
      onClick(x, y);
    }
  };

  const classes = `cell ${highlight ? "highlight" : ""} ${
    value ? "filled" : ""
  } ${clickable && !value ? "clickable" : ""} ${
    value === "X" ? "x-cell" : "o-cell"
  }`;

  return (
    <div
      data-testid={`cell-${x}-${y}`}
      onClick={handleClick}
      className={classes}
    >
      {value}
    </div>
  );
};

export default Cell;
