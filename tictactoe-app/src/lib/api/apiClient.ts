import axios, { type AxiosInstance } from "axios";
import { API_BASE_URL } from "./endpoints";

const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

/**
 * API client for making requests to the backend avoiding repetition of axios calls
 */
export const api = {
  get: <T>(url: string) => apiClient.get<T>(url).then(res => res.data),
  post: <T>(url: string, body?: unknown) =>
    apiClient.post<T>(url, body).then(res => res.data),
};

export default apiClient;
