import React from "react";
import {InputText} from "primereact/inputtext";

export function GroupCreator({newGroupName, setNewGroupName, onCreate}) {
    return (
        <div id="group_creator">
            <InputText
                value={newGroupName}
                onChange={(e) => setNewGroupName(e.target.value)}
                placeholder="Название"
            />
            <div id="button_container">
                <button onClick={onCreate}>Создать группу</button>
            </div>
        </div>
    );
}