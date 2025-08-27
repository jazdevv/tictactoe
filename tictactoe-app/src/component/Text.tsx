import React from "react";

type TextProps = {
  as?: React.ElementType; 
  type?: "base" | "primary";       
  size?: "md" | "xl";              
  value: string;
  className?: string;         
};

const Text: React.FC<TextProps> = ({
  as: Component = "p",
  type = "base",
  size = "md",
  value,
  className = "",
}) => {
  const color = type === "primary" ? "var(--primary-color)" : "var(--neutral-90)";
  const fontSize = size === "xl" ? "1.25rem" : "1rem";

  return (
    <Component
      className={className}
      style={{
        color,
        fontSize,
      }}
    >
      {value}
    </Component>
  );
};

export default Text;
