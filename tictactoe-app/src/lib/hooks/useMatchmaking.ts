import { useState, useCallback } from "react";
import { ENDPOINTS } from "@/lib/api/endpoints";
import { api } from "@/lib/api/apiClient";
import { type MatchmakingResponseDTO } from "@/lib/types/dto/MatchmakingResponseDTO";
import { useTranslation } from "react-i18next";

interface UseMatchmaking {
  data: MatchmakingResponseDTO | null;
  loading: boolean;
  error: string | null;
  createMatch: () => Promise<void>;
}

/**
 * Custom hook for matchmaking in the game.
 * @returns {Object} - The matchmaking state and actions.
 */
export function useMatchmaking(): UseMatchmaking {
  const [data, setData] = useState<MatchmakingResponseDTO | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { t } = useTranslation();

  const createMatch = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await api.post<MatchmakingResponseDTO>(
        ENDPOINTS.CREATE_MATCH
      );
      setData(response);
    } catch (err: unknown) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError(t("unknownError"));
      }
    } finally {
      setLoading(false);
    }
  }, [t]);

  return { data, loading, error, createMatch };
}
