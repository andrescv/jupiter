// @ts-check
// eslint-disable-next-line
const { pathsToModuleNameMapper } = require('ts-jest');

/**
 * Jest config.
 *
 * @param {import('@jest/types').Config.InitialOptions} opts
 */
function Config(opts) {
  return {
    verbose: true,
    roots: ['src'],
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

Config.moduleNameMapperFrom = (
  /** @type {{ paths: import("typescript").MapLike<string[]> }} */
  compilerOptions,
  { prefix = '<rootDir>' } = {}
) => pathsToModuleNameMapper(compilerOptions.paths, {
  prefix,
})

module.exports = Config;
