import React from "react";
import "./Button.scss";

type ButtonProps = {
  type?: "base" | "primary";
  size?: "md" | "xl";
  value: string;
  onClick?: () => void;
  className?: string;
};

const Button: React.FC<ButtonProps> = ({
  type = "base",
  size = "md",
  value,
  onClick,
  className = "",
}) => {

  const classes = `button ${type} ${size} ${className}`;

  return (
    <button
      onClick={onClick}
      className={classes}
    >
      {value}
    </button>
  );
};

export default Button;
