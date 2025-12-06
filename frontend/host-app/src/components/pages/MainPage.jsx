import React from "react";
import {MainContainer} from "../mainContainer/MainContainer";
import {Header} from "../header/Header";
import {LogoutButton} from "../header/LogoutButton";

export function MainPage({InputApp, ResultApp}) {
    return (
        <MainContainer>
            <Header/>
            <LogoutButton/>
            <InputApp/>
            <ResultApp/>
        </MainContainer>
    );
}