/**
 * Jest config.
 *
 * @param {import('@jest/types').Config.InitialOptions} opts
 */
function Config(opts) {
  return {
    verbose: true,
    roots: ['src', 'test'],
    extensionsToTreatAsEsm: ['.ts'],
    preset: 'ts-jest/presets/js-with-ts',
    moduleFileExtensions: ['js', 'ts', 'json'],
    testRegex: '.*\\.spec\\.ts$',
    collectCoverageFrom: ['src/**/*.ts'],
    coverageDirectory: './coverage',
    testEnvironment: 'node',
    transform: {
      '^.+\\.{js,ts}$': ['ts-jest', { isolatedModules: true }],
    },
    ...opts,
  };
}

module.exports = Config;
