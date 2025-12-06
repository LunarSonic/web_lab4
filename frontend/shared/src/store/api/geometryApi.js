import {apiSlice} from "../slice/apiSlice";

export const geometryApi = apiSlice.injectEndpoints({
    endpoints: (build) => ({
        addPointToActiveGroup: build.mutation({
            query: (coordinates) => ({
                url: '/geometry/groups/active/points',
                method: 'POST',
                body: {...coordinates}
            }),
            invalidatesTags: ['ActivePoints', 'UserHistory']
        }),

        createGroup: build.mutation({
            query: (groupName) => ({
                url: '/geometry/groups',
                method: 'POST',
                body: {groupName}
            }),
            invalidatesTags: ['Groups', 'ActivePoints', 'ActiveGroup', 'UserHistory']
        }),

        deleteGroup: build.mutation({
            query: (groupId) => ({
                url: `/geometry/groups/${groupId}`,
                method: 'DELETE'
            }),
            invalidatesTags: ['Groups', 'ActiveGroup', 'ActivePoints', 'UserHistory']
        }),

        activateGroup: build.mutation({
            query: (groupId) => ({
                url: `/geometry/groups/${groupId}/switch`,
                method: 'POST'
            }),
            invalidatesTags: ['Groups', 'ActiveGroup', 'ActivePoints', 'UserHistory']
        }),

        getGroups: build.query({
            query: () => ({
                url: '/geometry/groups',
                method: 'GET'
            }),
            providesTags: ['Groups']
        }),

        getPointsForActiveGroup: build.query({
            query: () => ({
                url: '/geometry/groups/active/points',
                method: 'GET'
            }),
            providesTags: ['ActivePoints']
        }),

        getActiveGroup: build.query({
            query: () => ({
                url: '/geometry/groups/active',
                method: 'GET'
            }),
            providesTags: ['ActiveGroup']
        })
    })
});

export const {
    useAddPointToActiveGroupMutation,
    useCreateGroupMutation,
    useActivateGroupMutation,
    useDeleteGroupMutation,
    useGetGroupsQuery,
    useGetPointsForActiveGroupQuery,
    useGetActiveGroupQuery
} = geometryApi;
