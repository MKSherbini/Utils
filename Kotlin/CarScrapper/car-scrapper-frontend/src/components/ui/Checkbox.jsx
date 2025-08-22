import React from "react";

export function Checkbox({ checked, onChange, children }) {
    return (
        <label className="flex items-center space-x-2">
            <input type="checkbox" checked={checked} onChange={onChange} />
            <span>{children}</span>
        </label>
    );
}
