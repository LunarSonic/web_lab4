import React, {useState} from "react";
import {useGetGroupsQuery, useGetActiveGroupQuery} from "shared/geometryApi";
import {useCreateGroupMutation, useDeleteGroupMutation, useActivateGroupMutation} from "shared/geometryApi";
import {GroupSelector} from "./GroupSelector";
import {GroupCreator} from "./GroupCreator";
import {GroupDelete} from "./GroupDelete";

export function GroupsPanel() {
    const {data: active} = useGetActiveGroupQuery();
    const {data: groups = []} = useGetGroupsQuery();
    const [createGroup] = useCreateGroupMutation();
    const [deleteGroup] = useDeleteGroupMutation();
    const [switchGroup] = useActivateGroupMutation();

    const [newGroupName, setNewGroupName] = useState("");

    const handleSelect = async (id) => {
        if (id) {
            try {
                await switchGroup(id).unwrap();
            } catch (error) {
                console.error("Ошибка при выборе группы: ", error)
            }
        }
    };

    const handleCreate = async () => {
        if (!newGroupName.trim()) return;
        try {
            await createGroup(newGroupName).unwrap();
            setNewGroupName("");
        } catch (error) {
            console.error("Ошибка при создании группы: ", error);
        }
    };

    const handleDelete = async () => {
        const groupId = active?.id;
        if (groupId) {
            try {
                await deleteGroup(active.id).unwrap();
            } catch (error) {
                console.error("Ошибка при удалениии группы: ", error);
            }
        }
    };

    return (
        <div id="groups_panel">
            <GroupSelector
                groups={groups}
                activeId={active?.id}
                onSelect={handleSelect}
            />
            <GroupCreator
                newGroupName={newGroupName}
                setNewGroupName={setNewGroupName}
                onCreate={handleCreate}
            />
            <GroupDelete
                onDelete={handleDelete}
            />
        </div>
    );
}