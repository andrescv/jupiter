// eslint-disable-next-line
const { compilerOptions } = require('./tsconfig.json');

// eslint-disable-next-line
const { pathsToModuleNameMapper } = require('ts-jest');

// eslint-disable-next-line
const Config = require('@jupitersim/jest');

module.exports = Config({
  moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths, {
    prefix: '<rootDir>',
  }),
});
