import "./App.scss";
import Text from "@/components/atoms/Text/Text";
import Button from "@/components/atoms/Button/Button";
import { useTranslation } from "react-i18next";
import { BoardManager } from "@/components/molecules/Board/BoardManager";
import { useMatchmaking } from "@/lib/hooks/useMatchmaking";
import searching from '@/assets/searching.svg';


function App() {
  const { data, loading, error, createMatch } = useMatchmaking();
  const {t} = useTranslation();

  const handleSearchGame = () => {
    createMatch();
  };

  const gameFound = !loading && !error && data && data.matchId !== null;

  return (
    <div className="app-container">
      {loading ? (
        <div className="searching-container">
          <img src={searching} alt="Searching..." />
          <Text value={t("searchingGame")} type="title" />
        </div>
      ) : (
        <>
          {gameFound ? (
            <BoardManager
              matchId={data.matchId}
              playerId={data.playerId == "X" ? "X" : "O"}
            />
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
