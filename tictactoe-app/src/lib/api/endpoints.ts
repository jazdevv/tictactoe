export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const ENDPOINTS = {
  CREATE_MATCH: `${API_BASE_URL}/create`,
  STATUS: (gameId: string) => `${API_BASE_URL}/status?gameId=${gameId}`,
};
