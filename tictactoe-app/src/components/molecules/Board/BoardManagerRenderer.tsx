// GameStatusRenderer.tsx
import Board from "./Board";
import Text from "@/components/atoms/Text/Text";
import searching from "@/assets/searching.svg";
import { type GameResponseDTO } from "@/lib/types/dto/GameResponseDTO";
import { useTranslation } from "react-i18next";
import { GameStatus } from "@/lib/types/GameStatus";
import type { PlayerType } from "@/lib/types/PlayerType";
import './BoardManagerRenderer.scss';

type Props = {
  data?: GameResponseDTO;
  onMove: (x: number, y: number) => void;
  playerId: PlayerType;
};

export const BoardManagerRenderer = ({ data, onMove, playerId }: Props) => {
  const { t } = useTranslation();

  switch (data?.status) {
    case GameStatus.MATCHMAKING:
      return (
        <div className="searching-container">
          <img src={searching} alt="Searching..." />
          <Text value={t("searchingGame")} type="title" />
        </div>
      );

    case GameStatus.IN_PROGRESS: {
      console.log("debug: current turn", data?.currentTurn, playerId);
      const currentTurn = data?.currentTurn === playerId;

      return (
        <div className="game-in-progress">
          <Text value={t(currentTurn ? "yourTurn" : "opponentTurn")} type="title" />
          <Board
            moves={data?.movements || []}
            clickable={currentTurn}
            onMove={onMove}
            blocked={!currentTurn}
          />
        </div>
      );
    }
    case GameStatus.FINISHED:
      return (
        <div className="game-finished">
          <Text value={t("gameFinished")} type="title" />
          <Board
            moves={data?.movements || []}
            clickable={false}
            onMove={onMove}
            blocked={true}
          />
        </div>
      );
  }
};
