import TerminalNode from 'antlr4/tree/TerminalNode';

/**
 * Gets int value from node.
 *
 * @param node - Integer node.
 * @returns Integer value.
 */
export function getInt(node: TerminalNode) {
  const int = node.getText().toLowerCase();

  if (int.startsWith('0x')) {
    return parseInt(int.slice(2), 16);
  }

  if (int.startsWith('0b')) {
    return parseInt(int.slice(2), 2);
  }

  if (int.startsWith("'")) {
    return int.charCodeAt(1);
  }

  return parseInt(int, 10);
}
