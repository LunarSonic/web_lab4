import React from "react";
import {useErrorVisibility} from "shared/useErrorVisibility";

export function AuthInput({name, type, label, placeholder, register, rules, error}) {
    const showError = useErrorVisibility(error)
    return (
        <div className="form_group" id={name}>
            <label htmlFor={name}>{label}</label>
            <input
                type={type}
                id={name}
                placeholder={placeholder}
                {...register(name, rules)}
            />
            {error && (
                <div className={`error_base error ${showError ? "show" : ""}`} key={error.message}>
                    {error.message}
                </div>
            )}
        </div>
    );
}
