import TerminalNode from 'antlr4/tree/TerminalNode';

/**
 * Gets float value from node.
 *
 * @param node - Float node.
 * @returns Float value.
 */
export function getFloat(node: TerminalNode) {
  const float = node.getText().toLowerCase();

  return parseFloat(float);
}
