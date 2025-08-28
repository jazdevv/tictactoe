export const API_BASE_URL = "http://127.0.0.1:8080";

export const ENDPOINTS = {
  CREATE_MATCH: `${API_BASE_URL}/create`,
  STATUS: (gameId: string) => `${API_BASE_URL}/status?gameId=${gameId}`,
};
