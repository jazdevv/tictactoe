import React from "react";
import "./Cell.scss";

interface CellProps {
  x: number;
  y: number;
  value?: "X" | "O";
  clickable: boolean;
  onClick: (x: number, y: number) => void;
}

const Cell: React.FC<CellProps> = ({ x, y, value, clickable, onClick }) => {
  const handleClick = () => {
    if (!value && clickable) {
      onClick(x, y);
    }
  };

  const classes = `cell ${value ? "filled" : ""} ${clickable && !value ? "clickable" : ""}`;

  return (
    <div onClick={handleClick} className={classes}>
      {value}
    </div>
  );
};

export default Cell;
