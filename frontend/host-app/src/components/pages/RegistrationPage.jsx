import React, {useState} from "react";
import {Link} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Header} from "../header/Header";
import {MainContainer} from '../mainContainer/MainContainer';
import {useRegisterMutation} from "shared/authApi"
import {AuthInput} from "../auth/AuthInput";
import {ServerErrorMessage} from "../errorMessage/ServerErrorMessage";
import {useNavigate} from "react-router-dom";

export function RegistrationPage() {
    const loginPattern = /^[a-zA-Z0-9._-]+$/;
    const passwordPattern = /^[a-zA-Z0-9#$%&@~?!_]+$/;

    const [registerUser] = useRegisterMutation();
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onSubmit", reValidateMode: "onSubmit"});

    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        setErrorMessage("");
        try {
            await registerUser(data).unwrap();
            navigate("/login")
        } catch (error) {
            if (error && error.data && error.data.message) {
                setErrorMessage(error.data.message);
            }
        }
    };

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
                    <button type="submit" id="login_button">Зарегистрироваться</button>
                    <Link to="/login" className="swicth_button">Войти</Link>
                </div>
                <ServerErrorMessage errorMessage={errorMessage} />
        </form>
        </MainContainer>
    );
}