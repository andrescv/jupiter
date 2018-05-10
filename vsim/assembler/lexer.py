import ply.lex as lex


class Tokenizer(object):

    # lexer Tokens
    tokens = (
        # number representations
        'BIN', 'OCT', 'HEX', 'NUM',
        # strings
        'STRING',
        # directive e.g .data
        'DIRECTIVE',
        # label e.g label:
        'LABEL',
        # identifier i.e, instructions
        'IDENTIFIER',
        # riscv registers e.g x0 or zero
        'REGISTER_NUMBER', 'REGISTER_NAME',
        # normal tokens
        'COMMA', 'DOT', 'LPAREN', 'RPAREN',
    )

    # ignored characters
    t_ignore = ' \t\n\r\f\v'

    t_LABEL = r'[a-zA-Z][a-zA-Z0-9_]+\:'
    t_DIRECTIVE = r'\.[a-zA-Z][a-zA-Z0-9_]+'
    t_REGISTER_NUMBER = r'[xX]([0-9]{1,2})'
    RAW = r'(([zZ][eE][rR][oO]|'
    RAW += r'[rR][aA]|[sS][pP]|[gG][pP]'
    RAW += r'[tT][pP]|[fF][pP])'
    SAVED = r'|[sS](1[01]|[0-9])'
    TEMPS = r'|[tT][0-6]'
    ARGS = r'|[aA][0-7])'
    t_REGISTER_NAME = RAW + SAVED + TEMPS + ARGS

    t_COMMA = r'\,'
    t_DOT = r'\.'
    t_LPAREN = r'\('
    t_RPAREN = r'\)'

    def t_BIN(self, t):
        r'0[bB][01]+'
        t.value = int(t.value, 2)
        return t

    def t_OCT(self, t):
        r'0[0-7]+'
        t.value = int(t.value, 8)
        return t

    def t_HEX(self, t):
        r'0[xX][0-9aAbBcCdDeEfF]+'
        t.value = int(t.value, 16)
        return t

    def t_NUM(self, t):
        r'[+-]?[1-9][0-9]+'
        t.value = int(t.value)
        return t

    def t_STRING(self, t):
        r'\"((.|\n)*)\"'
        t.value = t.value.strip('"')
        return t

    # Define a rule so we can track line numbers
    def t_newline(self, t):
        r'\n+'
        t.lexer.lineno += len(t.value)

    # error handling rule
    def t_error(self, t):
        t.lexer.skip(1)
        return t

    # build the lexer,
    def build(self, **kwargs):
        self.lexer = lex.lex(module=self, **kwargs)

    # set lexer input data
    def scan(self, data):
        self.lexer.input(data)

    def __iter__(self):
        return self

    def __next__(self):
        token = self.lexer.token()
        if not token:
            raise StopIteration
        return token
