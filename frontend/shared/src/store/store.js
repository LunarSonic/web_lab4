import {configureStore} from "@reduxjs/toolkit";
import {apiSlice} from "./slice/apiSlice";
import {rSlice} from "./slice/rSlice";
import authReducer from "./slice/authSlice";
import "./api/authApi";
import "./api/geometryApi";
import "./api/historyApi";

export const store = configureStore({
    reducer: {
        [apiSlice.reducerPath]: apiSlice.reducer,
        rValue: rSlice.reducer,
        auth: authReducer,
    },
    middleware: (getDefaultMiddleware =>
        getDefaultMiddleware().concat(apiSlice.middleware))
});

export default store;