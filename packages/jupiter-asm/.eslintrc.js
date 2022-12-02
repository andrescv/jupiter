module.exports = {
  extends: ['jupiter'],
  rules: {
    '@typescript-eslint/no-non-null-assertion': 'off',
  },
  overrides: [
    {
      files: ['**/*.ts'],
      rules: {
        'simple-import-sort/imports': [
          'error',
          {
            groups: [
              ['^\\u0000'],
              ['^'],
              ['^@?\\w'],
              ['^@/constant'],
              ['^@/data'],
              ['^@/enum'],
              ['^@/instruction'],
              ['^@/interface'],
              ['^@/syntax'],
              ['^@/util'],
              ['^\\.\\.'],
              ['^\\.'],
            ],
          },
        ],
      },
    },
  ],
};
