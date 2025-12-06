import {createSlice} from "@reduxjs/toolkit";

const initialState = {
    currentR: null
}

export const rSlice = createSlice({
    name: "rValue",
    initialState,
    reducers: {
        setRValue: (state, action) => {
            state.currentR = action.payload;
        }
    }
});

export const {setRValue} = rSlice.actions;
export const selectRValue = (state) => state.rValue.currentR;
export default rSlice.reducer;