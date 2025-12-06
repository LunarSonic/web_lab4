import React from "react";

export function ResultTable({points}) {
    return (
        <>
            <h3 className="history_header">История проверок</h3>
            <table>
                <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Попали?</th>
                    <th>Текущая дата</th>
                    <th>Время работы скрипта</th>
                </tr>
                </thead>
                <tbody id="body_for_table">
                {points.map((point, index) => (
                    <tr key={index}>
                        <td>{point.x}</td>
                        <td>{point.y}</td>
                        <td>{point.r}</td>
                        <td style={{color: point.hit ? "#79d34a" : "#bc2929"}}>{point.hit ? 'Да' : 'Нет'}</td>
                        <td>{point.serverTime}</td>
                        <td>{point.scriptTime}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
}