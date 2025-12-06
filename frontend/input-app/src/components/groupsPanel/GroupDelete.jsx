import React from "react";

export function GroupDelete({onDelete}) {
    return (
        <div id="button_container">
            <button onClick={onDelete}>Удалить группу</button>
        </div>
    );
}