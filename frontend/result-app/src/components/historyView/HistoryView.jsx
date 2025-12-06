import React from "react";
import {HistoryTable} from "../historyTable/HistoryTable";
import {ResultTable} from "../resultTable/ResultTable";
import {Canvas} from "../canvas/Canvas";
import {useSelector} from "react-redux";
import {selectRValue} from "shared/rSlice";
import {useGetPointsForActiveGroupQuery, useAddPointToActiveGroupMutation} from "shared/geometryApi";

export function HistoryView() {
    const currentR = useSelector(selectRValue)
    const {data: points = []} = useGetPointsForActiveGroupQuery();
    const [addPoint] = useAddPointToActiveGroupMutation();
    const canvasHandlers = {addPoint, currentR};

    return (
        <div id="history">
            <div className="left_column_history">
                <ResultTable points={points}/>
                <HistoryTable/>
            </div>
            <div className="right_column_history">
                <Canvas points={points} r={currentR} formHandlers={canvasHandlers}/>
            </div>
        </div>
    );
}
