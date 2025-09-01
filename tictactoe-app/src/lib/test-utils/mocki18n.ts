import { jest } from '@jest/globals';

export const mockUseTranslation = () => {
  jest.mock('react-i18next', () => ({
    useTranslation: () => ({
      t: (key: string) => key, // return the key as the "translation"
      i18n: { changeLanguage: jest.fn() },
    }),
  }));
};
