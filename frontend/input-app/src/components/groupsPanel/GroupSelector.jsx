import React, {useEffect} from "react";
import {useState} from "react";
import {Dropdown} from "primereact/dropdown";

export function GroupSelector({groups, activeId, onSelect}) {
    const [selectedValue, setSelectedValue] = useState(activeId || null)

    useEffect(() => {
        setSelectedValue(activeId || null);
    }, [activeId]);

    const handleValueChange = (e) => {
        setSelectedValue(e.value);
        onSelect(e.value);
    }

    const options = groups.map(g => ({
        label: g.groupName,
        value: g.id
    }));

    return (
        <Dropdown
            value={selectedValue}
            options={options}
            optionLabel="label"
            optionValue="value"
            onChange={handleValueChange}
            placeholder="Выберите группу"
        />
    );
}