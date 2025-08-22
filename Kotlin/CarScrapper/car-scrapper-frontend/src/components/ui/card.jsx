import React from "react";

export function Card({ children }) {
    return <div className="border p-4 rounded-lg shadow-md bg-white">{children}</div>;
}

export function CardContent({ children }) {
    return <div className="p-2">{children}</div>;
}
