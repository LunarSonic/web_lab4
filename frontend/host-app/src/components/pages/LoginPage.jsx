import React, {useEffect, useState} from "react";
import {useNavigate, Link} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Header} from "../header/Header";
import {MainContainer} from "../mainContainer/MainContainer";
import {ServerErrorMessage} from "../errorMessage/ServerErrorMessage";
import {useSelector} from "react-redux";
import {useLoginMutation} from "shared/authApi"
import {selectIsAuthenticated} from "shared/authSlice";
import {AuthInput} from "../auth/AuthInput";

export function LoginPage() {
    const [login] = useLoginMutation();
    const isAuthenticated = useSelector(selectIsAuthenticated);
    const navigate = useNavigate();
    const {register, handleSubmit, formState: {errors}} = useForm({defaultValues: {username: "", password: ""}, mode: "onSubmit", reValidateMode: "onSubmit"});

    const loginPattern = /^[a-zA-Z0-9._-]+$/;
    const passwordPattern = /^[a-zA-Z0-9#$%&@~?!_]+$/;

    const [errorMessage, setErrorMessage] = useState("");

    const onSubmit = async (data) => {
        setErrorMessage("");
        try {
            await login(data).unwrap();
        } catch (error) {
            if (error && error.data && error.data.message) {
                setErrorMessage(error.data.message);
            }
        }
    };
    useEffect(() => {
        if (isAuthenticated)
            navigate("/main");
    }, [isAuthenticated, navigate]);

    return (
        <MainContainer>
            <Header />
            <form className="login_form" onSubmit={handleSubmit(onSubmit)}>
                <AuthInput
                    name="username"
                    type="text"
                    label="Логин"
                    placeholder="Введите логин"
                    register={register}
                    error={errors.username}
                    rules={{
                        required: "Логин обязателен",
                        minLength: {
                            value: 5,
                            message: "Должно быть минимум 5 символов!"
                        },
                        pattern: {
                            value: loginPattern,
                            message: "Должны быть только английские буквы, цифры и . _ -"
                        }
                    }}
                />
                <AuthInput
                    name="password"
                    type="password"
                    label="Пароль"
                    placeholder="Введите пароль"
                    register={register}
                    error={errors.password}
                    rules={{
                        required: "Пароль обязателен",
                        minLength: {
                            value: 8,
                            message: "Должно быть минимум 8 символов!"
                        },
                        pattern: {
                            value: passwordPattern,
                            message: "Должны быть только английские буквы, цифры и # $ % & @ ~ ? ! _"
                        }
                    }}
                />
                <div id="button_container">
                    <button type="submit" id="login_button">Войти</button>
                    <Link to="/register" className="swicth_button">Зарегистрироваться</Link>
                </div>
                <ServerErrorMessage errorMessage={errorMessage} />
            </form>
        </MainContainer>
    );
}