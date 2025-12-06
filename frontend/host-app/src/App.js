import React, {lazy, Suspense} from "react";
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import {LoginPage} from "./components/pages/LoginPage";
import {RegistrationPage} from "./components/pages/RegistrationPage";
import {MainPage} from "./components/pages/MainPage";
import {ProtectedRouter} from "./components/router/ProtectedRouter";

const RemoteInputApp = lazy(() => import('inputapp/InputApp'));
const RemoteResultApp = lazy(() => import('resultapp/ResultApp'));

export default function App() {
    return (
        <BrowserRouter>
            <Suspense fallback={<div>Загрузка</div>}>
                <Routes>
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegistrationPage />} />
                    <Route
                        path="/main"
                        element={
                            <ProtectedRouter>
                                <MainPage
                                    InputApp={RemoteInputApp}
                                    ResultApp={RemoteResultApp}
                                />
                            </ProtectedRouter>
                        }
                    />
                    <Route path="*" element={<Navigate to="/login" replace />} />
                </Routes>
            </Suspense>
        </BrowserRouter>
    );
}