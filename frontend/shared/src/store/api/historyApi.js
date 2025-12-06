import {apiSlice} from "../slice/apiSlice";

export const historyApi = apiSlice.injectEndpoints({
    endpoints: (build) => ({
        getUserHistory: build.query({
            query: () => ({
                url: `/history`,
                method: 'GET'
            }),
            providesTags: ['UserHistory']
        })
    })
});

export const {
    useGetUserHistoryQuery
} = historyApi;
