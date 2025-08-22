import React from "react";

export function Select({ value, onChange, children }) {
    return (
        <select
            value={value}
            onChange={onChange}
            className="border p-2 rounded-md w-full"
        >
            {children}
        </select>
    );
}
