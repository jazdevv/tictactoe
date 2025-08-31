import React from "react";
import "./Cell.scss";

interface CellProps {
  x: number;
  y: number;
  value?: "X" | "O";
  highlight?: boolean;
  clickable: boolean;
  onClick: (x: number, y: number) => void;
}

const Cell: React.FC<CellProps> = ({ x, y, value, highlight = false, clickable, onClick }) => {
  const handleClick = () => {
    if (!value && clickable) {
      onClick(x, y);
    }
  };

  const classes = `cell ${highlight ? "highlight" : ""} ${value ? "filled" : ""} ${clickable && !value ? "clickable" : ""} ${value === "X" ? "x-cell" : "o-cell"}`;

  return (
    <div onClick={handleClick} className={classes}>
      {value}
    </div>
  );
};

export default Cell;
