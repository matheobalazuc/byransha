import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

// Custom hook to fetch API data with TanStack Query
export const useApiData = (endpoints, params = {}) => {
    const queryParams = new URLSearchParams({ endpoints, ...params }).toString();
    const url = `${import.meta.env.VITE_API_BASE_URL}?${queryParams}`;

    return useQuery({
        queryKey: ['apiData', endpoints, params], // Unique key for caching
        queryFn: () => axios.get(url),
    });
};

export const useMainApiData = (params = {}) => {
    const queryParams = new URLSearchParams({ ...params }).toString();
    const url = `${import.meta.env.VITE_API_BASE_URL}?${queryParams}`;

    return useQuery({
        queryKey: ['apiData', '/', params], // Unique key for caching
        queryFn: () => axios.get(url).then(res => res.data),
    });
};