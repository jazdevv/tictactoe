import React, { useState } from "react";
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
  const [hover, setHover] = useState(false);

  const classes = `button ${type} ${size} ${hover ? "hover" : ""} ${className}`;

  return (
    <button
      onClick={onClick}
      className={classes}
      onMouseEnter={() => setHover(true)}
      onMouseLeave={() => setHover(false)}
    >
      {value}
    </button>
  );
};

export default Button;
