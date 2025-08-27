import React from "react";

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

  return (
    <div
      onClick={handleClick}
      style={{
        width: "80px",
        height: "80px",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        border: "1px solid #333",
        fontSize: "2rem",
        cursor: value || !clickable ? "default" : "pointer",
        userSelect: "none",
      }}
    >
      {value}
    </div>
  );
};

export default Cell;
