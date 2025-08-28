import { useTranslation } from "react-i18next";
import Text from "@/components/atoms/Text/Text";
import searching from '@/assets/searching.svg';

export const Searching = () => {
  const { t } = useTranslation();

  return (
    <div className="searching-container">
      <img src={searching} alt="Searching..." />
      <Text value={t("searchingGame")} type="title" />
    </div>
  );
};

