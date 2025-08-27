import React from "react";
import "./Text.scss";

type TextProps = {
  as?: React.ElementType;
  type?: "base" | "primary" | "title";
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
  const classes = `text ${type} ${size} ${className}`;

  return <Component className={classes}>{value}</Component>;
};

export default Text;
