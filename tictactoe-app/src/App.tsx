import { useState } from "react";
import "./App.scss";
import Text from "@/components/atoms/Text/Text";
import Board from "@/components/molecules/Board/Board";
import Button from "@/components/atoms/Button/Button";

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

  const handleMove = (x: number, y: number) => {
    // Add the new move
    setMoves([...moves, { playerId: nextPlayer, x, y }]);
    // Switch player
    setNextPlayer(nextPlayer === "X" ? "O" : "X");
  };

  const handleSearchGame = () => {
    setSearchingGame(true);

    setTimeout(() => {
      setSearchingGame(false);
      setGameId("123");
    }, 200);
  };

  const gameFound = gameId !== null;

  return (
    <>
      {
        searchingGame ? 
        <div>searching ...</div>
        : 
        <>
{gameFound ? (
        <>
          <Text value="Hello, World!" type="primary" />
          <Board moves={moves} clickable={true} onMove={handleMove} blocked={true}/>
        </>
      ) : (
        <Button value="Search game" onClick={handleSearchGame} />
      )}
      </>
      }
      
    </>
  );
}

export default App;
