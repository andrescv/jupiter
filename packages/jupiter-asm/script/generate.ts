import { exec } from 'child_process';

const ANTLR_TOOL = 'java -jar lib/antlr-4.11.1-complete.jar';
const ANTLR_TARGET = '-Dlanguage=JavaScript';
const ANTLR_FLAGS = '-Xexact-output-dir -o src/syntax';
const ANTLR_GRAMMAR = 'antlr/RV32G.g4';

exec(`${ANTLR_TOOL} ${ANTLR_TARGET} ${ANTLR_FLAGS} ${ANTLR_GRAMMAR}`);
