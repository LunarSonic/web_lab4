import React from "react";
import {useGetUserHistoryQuery} from "shared/historyApi";

export function HistoryTable() {
    const {data: history = []} = useGetUserHistoryQuery();

    return (
        <>
            <h3 className="history_header">История действий пользователя</h3>
            <table>
                <thead>
                <tr>
                    <th>Действие</th>
                    <th>Сообщение</th>
                    <th>Время</th>
                </tr>
                </thead>
                <tbody>
                {history.map((entry, index) => (
                    <tr key={index}>
                        <td>{entry.action}</td>
                        <td>{entry.message}</td>
                        <td>{entry.time}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
}
