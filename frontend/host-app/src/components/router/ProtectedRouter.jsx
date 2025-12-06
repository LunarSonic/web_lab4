import React from "react";
import {useSelector} from "react-redux";
import {Navigate, useLocation} from "react-router-dom";
import {selectIsAuthenticated} from "shared/authSlice";

export function ProtectedRouter({children}) {
    const isAuthenticated = useSelector(selectIsAuthenticated);
    const location = useLocation();
    if (!isAuthenticated) {
        return <Navigate to="/login" replace state={{from: location}} />;
    }
    return children;
}