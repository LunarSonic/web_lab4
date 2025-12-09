import {configureStore} from "@reduxjs/toolkit";
import {apiSlice} from "./slice/apiSlice";
import authReducer from "./slice/authSlice";
import rReducer from "./slice/rSlice";
import "./api/authApi";
import "./api/geometryApi";
import "./api/historyApi";

export const store = configureStore({
    reducer: {
        [apiSlice.reducerPath]: apiSlice.reducer,
        rValue: rReducer,
        auth: authReducer,
    },
    middleware: (getDefaultMiddleware =>
        getDefaultMiddleware().concat(apiSlice.middleware))
});