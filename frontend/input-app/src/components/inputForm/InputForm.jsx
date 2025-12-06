import React, {useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {setRValue} from "shared/rSlice";
import {useForm} from "react-hook-form";
import {XField} from "../formFields/XField";
import {YField} from "../formFields/YField";
import {RField} from "../formFields/RField";
import {useAddPointToActiveGroupMutation, useGetActiveGroupQuery} from "shared/geometryApi";
import {useErrorVisibility} from "shared/useErrorVisibility";
import {ServerErrorMessage} from "../errorMessage/ServerErrorMessage";
import {GroupsPanel} from "../groupsPanel/GroupsPanel";

export function InputForm() {
    const xItems = [
        { label: '-2', value: -2.0 },
        { label: '-1.5', value: -1.5 },
        { label: '-1', value: -1.0 },
        { label: '-0.5', value: -0.5 },
        { label: '0', value: 0.0 },
        { label: '0.5', value: 0.5 },
        { label: '1', value: 1.0 },
        { label: '1.5', value: 1.5 },
        { label: '2', value: 2.0 }
    ];

    const rItems = [
        { label: '-2', value: -2.0 },
        { label: '-1.5', value: -1.5 },
        { label: '-1', value: -1.0 },
        { label: '-0.5', value: -0.5 },
        { label: '0', value: 0.0 },
        { label: '0.5', value: 0.5 },
        { label: '1', value: 1.0 },
        { label: '1.5', value: 1.5 },
        { label: '2', value: 2.0 }
    ];

    const [addPoint] = useAddPointToActiveGroupMutation();
    const {data: activeGroup} = useGetActiveGroupQuery();
    const dispatch = useDispatch();
    const {handleSubmit, control, watch, reset, formState: {errors}} = useForm({defaultValues: {x: null, y: 0, r: null}});

    const xShowError = useErrorVisibility(errors.x);
    const yShowError = useErrorVisibility(errors.y);
    const rShowError = useErrorVisibility(errors.r);

    const [yDisplay, setYDisplay] = useState("0");
    const [errorMessage, setErrorMessage] = useState("");
    const r = watch("r");

    useEffect(() => {
        if (r != null) {
            dispatch(setRValue(r));
        }
    }, [r, dispatch]);

    const onSubmit = async (data) => {
        setErrorMessage("")
        if (!activeGroup) {
            setErrorMessage("Сначала создайте или выберите группу");
            return;
        }
        try {
            await addPoint(data).unwrap();
        } catch (error) {
            if (error && error.data && error.data.message) {
                setErrorMessage(error.data.message);
            }
        }
    };

    const handleClearForm = () => {
        reset();
        setYDisplay("0");
    };

    return (
        <div id="input_form_container">
        <div className="left_column">
                <form id="main_form" onSubmit={handleSubmit(onSubmit)}>
                    <XField
                        control={control}
                        xShowError={xShowError}
                        errors={errors}
                        xItems={xItems}
                    />
                    <YField
                        control={control}
                        errors={errors}
                        yDisplay={yDisplay}
                        setYDisplay={setYDisplay}
                        yShowError={yShowError}
                    />
                    <RField
                        control={control}
                        rShowError={rShowError}
                        errors={errors}
                        rItems={rItems}
                    />
                    <div id="button_container">
                        <button id="submit_button" type="submit">Подтвердить</button>
                        <button id="clear_form_button" type="button" onClick={handleClearForm}>Очистить форму</button>
                    </div>
                    <ServerErrorMessage errorMessage={errorMessage} />
                </form>
            </div>
            <div className="right_column">
                <GroupsPanel/>
            </div>
        </div>
    );
}