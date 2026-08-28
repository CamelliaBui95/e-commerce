import React, { type JSX } from "react";
import "./service-highlight.scss";

interface ServiceHighlightProps {
  icon: JSX.Element;
  title: string;
  description: string;
}

const ServiceHighlight: React.FC<ServiceHighlightProps> = ({
  icon,
  title,
  description,
}) => {
  return (
    <div className="service-highlight flex flex-row items-center justify-center gap-2">
      <div>{icon}</div>
      <div className="text-[0.8rem]">
        <p className="font-bold">{title}</p>
        <p>{description}</p>
      </div>
    </div>
  );
};

export default ServiceHighlight;
