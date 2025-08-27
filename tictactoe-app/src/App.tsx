import { useState } from "react";
import "./App.scss";
import Text from "@/components/atoms/Text/Text";
import Board from "@/components/molecules/Board/Board";
import Button from "@/components/atoms/Button/Button";
import { useTranslation } from "react-i18next";

type Move = {
  playerId: "X" | "O";
  x: number;
  y: number;
};

const initialMoves: Move[] = [
  { playerId: "X", x: 1, y: 1 },
  { playerId: "O", x: 1, y: 2 },
  { playerId: "X", x: 2, y: 2 },
  { playerId: "O", x: 2, y: 3 },
  { playerId: "X", x: 3, y: 3 },
];

function App() {
  const [searchingGame, setSearchingGame] = useState<boolean>(false);
  const [gameId, setGameId] = useState<string | null>(null);
  const [moves, setMoves] = useState<Move[]>(initialMoves);
  const [nextPlayer, setNextPlayer] = useState<"X" | "O">("O");
  const {t} = useTranslation();

  const handleMove = (x: number, y: number) => {
    // Add the new move
    setMoves([...moves, { playerId: nextPlayer, x, y }]);
    // Switch player
    setNextPlayer(nextPlayer === "X" ? "O" : "X");
  };

  const handleSearchGame = () => {
    setSearchingGame(true);
    
    
  };

  const gameFound = gameId !== null;

  return (
    <div className="app-container">
      {searchingGame ? (
        <div>searching ...</div>
      ) : (
        <>
          {gameFound ? (
            <>
              <Board
                moves={moves}
                clickable={true}
                onMove={handleMove}
                blocked={true}
              />
            </>
          ) : (
            <>
              <Text value={t("title")} type="title" />
              <Button value={t("searchGame")} type="primary" onClick={handleSearchGame} />
            </>
          )}
        </>
      )}
    </div>
  );
}

export default App;
