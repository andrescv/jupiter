// @ts-check
// eslint-disable-next-line
const { compilerOptions } = require('./tsconfig.json');

// eslint-disable-next-line
const Config = require('@jupitersim/jest');

module.exports = Config({
  moduleNameMapper: Config.moduleNameMapperFrom(compilerOptions),
});
