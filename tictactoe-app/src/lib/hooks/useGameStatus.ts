import useSWR from "swr";
import { ENDPOINTS } from "@/lib/api/endpoints";
import { api } from "@/lib/api/apiClient";
import type { GameResponseDTO } from "@/lib/types/dto/GameResponseDTO";

interface UseGameStatus {
  data: GameResponseDTO | undefined;
  error: string | null;
  loading: boolean;
}

const fetcher = (url: string) => api.get<GameResponseDTO>(url);

/**
 * Custom hook for fetching game status and refresh it every 1000ms
 * @param gameId - The ID of the game to fetch status for.
 * @returns {Object} - The game status data, error, and loading state.
 */
export function useGameStatus(gameId: string): UseGameStatus {
  const { data, error } = useSWR<GameResponseDTO, Error>(
    ENDPOINTS.STATUS(gameId),
    fetcher,
    {
      refreshInterval: 1000,
    }
  );

  return {
    data,
    error: error ? error.message : null,
    loading: !data && !error,
  };
}
