import React, { useState } from "react";

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

  const background = type === "primary" ? "var(--primary-color)" : "transparent";
  const color = type === "primary" ? "#fff" : "var(--neutral-90)";
  const hoverBackground =
    type === "primary"
      ? "var(--primary-color-hover, #005fcc)"
      : "var(--neutral-95, #f0f0f0)";
  const padding = size === "xl" ? "0.75rem 1.5rem" : "0.5rem 1rem";
  const fontSize = size === "xl" ? "1.25rem" : "1rem";
  const border = type === "primary" ? "none" : "1px solid var(--neutral-90)";
  const borderRadius = "0.375rem";
  const cursor = "pointer";

  return (
    <button
      onClick={onClick}
      className={className}
      onMouseEnter={() => setHover(true)}
      onMouseLeave={() => setHover(false)}
      style={{
        background: hover ? hoverBackground : background,
        color,
        padding,
        fontSize,
        border,
        borderRadius,
        cursor,
        transition: "background 0.2s",
      }}
    >
      {value}
    </button>
  );
};

export default Button;
