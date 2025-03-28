import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

// Custom hook to fetch API data with TanStack Query
export const useApiData = (endpoints, params = {}) => {
    const queryParams = new URLSearchParams({ ...params }).toString();
    const queryString = queryParams ? `?${queryParams}` : '';
    const url = `${import.meta.env.VITE_API_BASE_URL}/${endpoints}${queryString}`;

    return useQuery({
        queryKey: ['apiData', endpoints, params], // Unique key for caching
        queryFn: () => axios.get(url),
        retry: 2,
        staleTime: 60000,
        onError: (error) => {
            console.error(`API request failed for ${endpoints}:`, error);
        }
    });
};
