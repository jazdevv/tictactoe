import useSWR, { mutate } from "swr";
import { ENDPOINTS } from "@/lib/api/endpoints";
import { api } from "@/lib/api/apiClient";
import type { GameResponseDTO } from "@/lib/types/dto/GameResponseDTO";

interface UseGameStatus {
  data: GameResponseDTO | undefined;
  error: string | null;
  loading: boolean;
  makeMove: (x: number, y: number, playerId: string, matchId: string) => Promise<void>;
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

  /**
   * Makes a move in the game.
   * @param x - The x-coordinate of the square to mark.
   * @param y - The y-coordinate of the square to mark.
   * @param playerId - The ID of the player making the move.
   * @param matchId - The ID of the match.
   */
  const makeMove = async (
    x: number,
    y: number,
    playerId: string,
    matchId: string
  ) => {
    try {
      await api.post("/move", {
        square: { x, y },
        playerId,
        matchId,
      });

      mutate(ENDPOINTS.STATUS(gameId));
    } catch (err: unknown) {
      console.error("Error making move:", err);
      throw err;
    }
  };

  return {
    data,
    error: error ? error.message : null,
    loading: !data && !error,
    makeMove
  };
}
