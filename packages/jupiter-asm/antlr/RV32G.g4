/*
 * RISC-V 32-bit RV32IMAFDZicsr_Zifencei (RV32G) Grammar
 */
grammar RV32G;

prog
  : line*? EOF
  ;

line
  : stmt EOL
  | stmt
  | EOL
  ;

stmt
  : label_def static_data
  | label_def instruction
  | label_def
  | directive
  | static_data
  | instruction
  ;

label_def
  : LABEL                                                                 # LABEL
  ;

instruction
  : rv32i_ext                                                             # RV32I
  | rv32zifencei_ext                                                      # RV32Zifencei
  | rv32zicsr_ext                                                         # RV32Zicsr
  | rv32m_ext                                                             # RV32M
  | rv32a_ext                                                             # RV32A
  | rv32f_ext                                                             # RV32F
  | rv32d_ext                                                             # RV32D
  | rv32i_ext_pseudos                                                     # RV32IPSEUDOS
  | rv32zicsr_ext_pseudos                                                 # RV32ZicsrPSEUDOS
  | rv32f_ext_pseudos                                                     # RV32FPSEUDOS
  | rv32d_ext_pseudos                                                     # RV32DPSEUDOS
  ;

rv32i_ext
  : I_LUI XREG ','? hi_expr                                               # LUI
  | I_AUIPC XREG ','? pcrel_hi_expr                                       # AUIPC
  | I_JAL XREG ','? expr                                                  # JAL
  | I_JALR XREG ','? (XREG ','? expr|expr '(' XREG ')')                   # JALR
  | I_BEQ XREG ','? XREG ','? expr                                        # BEQ
  | I_BNE XREG ','? XREG ','? expr                                        # BNE
  | I_BLT XREG ','? XREG ','? expr                                        # BLT
  | I_BGE XREG ','? XREG ','? expr                                        # BGE
  | I_BLTU XREG ','? XREG ','? expr                                       # BLTU
  | I_BGEU XREG ','? XREG ','? expr                                       # BGEU
  | I_LB XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')             # LB
  | I_LH XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')             # LH
  | I_LW XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')             # LW
  | I_LBU XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')            # LBU
  | I_LHU XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')            # LHU
  | I_SB XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')             # SB
  | I_SH XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')             # SH
  | I_SW XREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')             # SW
  | I_ADDI XREG ','? XREG ','? lo_expr                                    # ADDI
  | I_SLTI XREG ','? XREG ','? expr                                       # SLTI
  | I_SLTIU XREG ','? XREG ','? expr                                      # SLTIU
  | I_XORI XREG ','? XREG ','? expr                                       # XORI
  | I_ORI XREG ','? XREG ','? expr                                        # ORI
  | I_ANDI XREG ','? XREG ','? expr                                       # ANDI
  | I_SLLI XREG ','? XREG ','? expr                                       # SLLI
  | I_SRLI XREG ','? XREG ','? expr                                       # SRLI
  | I_SRAI XREG ','? XREG ','? expr                                       # SRAI
  | I_ADD XREG ','? XREG ','? XREG                                        # ADD
  | I_SUB XREG ','? XREG ','? XREG                                        # SUB
  | I_SLL XREG ','? XREG ','? XREG                                        # SLL
  | I_SLT XREG ','? XREG ','? XREG                                        # SLT
  | I_SLTU XREG ','? XREG ','? XREG                                       # SLTU
  | I_XOR XREG ','? XREG ','? XREG                                        # XOR
  | I_SRL XREG ','? XREG ','? XREG                                        # SRL
  | I_SRA XREG ','? XREG ','? XREG                                        # SRA
  | I_OR XREG ','? XREG ','? XREG                                         # OR
  | I_AND XREG ','? XREG ','? XREG                                        # AND
  | I_FENCE                                                               # FENCE
  | I_ECALL                                                               # ECALL
  | I_EBREAK                                                              # EBREAK
  ;

rv32zifencei_ext
  : I_FENCEI                                                              # FENCEI
  ;

rv32zicsr_ext
  : I_CSRRW XREG ','? expr ','? XREG                                      # CSRRW
  | I_CSRRS XREG ','? expr ','? XREG                                      # CSRRS
  | I_CSRRC XREG ','? expr ','? XREG                                      # CSRRC
  | I_CSRRWI XREG ','? expr ','? expr                                     # CSRRWI
  | I_CSRRSI XREG ','? expr ','? expr                                     # CSRRSI
  | I_CSRRCI XREG ','? expr ','? expr                                     # CSRRCI
  ;

rv32m_ext
  : I_MUL XREG ','? XREG ','? XREG                                        # MUL
  | I_MULH XREG ','? XREG ','? XREG                                       # MULH
  | I_MULHSU XREG ','? XREG ','? XREG                                     # MULHSU
  | I_MULHU XREG ','? XREG ','? XREG                                      # MULHU
  | I_DIV XREG ','? XREG ','? XREG                                        # DIV
  | I_DIVU XREG ','? XREG ','? XREG                                       # DIVU
  | I_REM XREG ','? XREG ','? XREG                                        # REM
  | I_REMU XREG ','? XREG ','? XREG                                       # REMU
  ;

rv32a_ext
  : I_LRW XREG ','? '('? XREG ')'?                                        # LRW
  | I_SCW XREG ','? XREG ','? '('? XREG ')'?                              # SCW
  | I_AMOSWAPW XREG ','? XREG ','? '('? XREG ')'?                         # AMOSWAPW
  | I_AMOADDW XREG ','? XREG ','? '('? XREG ')'?                          # AMOADDW
  | I_AMOXORW XREG ','? XREG ','? '('? XREG ')'?                          # AMOXORW
  | I_AMOANDW XREG ','? XREG ','? '('? XREG ')'?                          # AMOANDW
  | I_AMOORW XREG ','? XREG ','? '('? XREG ')'?                           # AMOORW
  | I_AMOMINW XREG ','? XREG ','? '('? XREG ')'?                          # AMOMINW
  | I_AMOMAXW XREG ','? XREG ','? '('? XREG ')'?                          # AMOMAXW
  | I_AMOMINUW XREG ','? XREG ','? '('? XREG ')'?                         # AMOMINUW
  | I_AMOMAXUW XREG ','? XREG ','? '('? XREG ')'?                         # AMOMAXUW
  ;

rv32f_ext
  : I_FLW FREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')            # FLW
  | I_FSW (FREG ','? XREG ','? lo_expr | FREG ','? lo_expr '(' XREG ')')  # FSW
  | I_FMADDS FREG ','? FREG ','? FREG ','? FREG                           # FMADDS
  | I_FMSUBS FREG ','? FREG ','? FREG ','? FREG                           # FMSUBS
  | I_FNMSUBS FREG ','? FREG ','? FREG ','? FREG                          # FNMSUBS
  | I_FNMADDS FREG ','? FREG ','? FREG ','? FREG                          # FNMADDS
  | I_FADDS FREG ','? FREG ','? FREG                                      # FADDS
  | I_FSUBS FREG ','? FREG ','? FREG                                      # FSUBS
  | I_FMULS FREG ','? FREG ','? FREG                                      # FMULS
  | I_FDIVS FREG ','? FREG ','? FREG                                      # FDIVS
  | I_FSQRTS FREG ','? FREG                                               # FSQRTS
  | I_FSGNJS FREG ','? FREG ','? FREG                                     # FSGNJS
  | I_FSGNJNS FREG ','? FREG ','? FREG                                    # FSGNJNS
  | I_FSGNJXS FREG ','? FREG ','? FREG                                    # FSGNJXS
  | I_FMINS FREG ','? FREG ','? FREG                                      # FMINS
  | I_FMAXS FREG ','? FREG ','? FREG                                      # FMAXS
  | I_FCVTWS XREG ','? FREG                                               # FCVTWS
  | I_FCVTWUS XREG ','? FREG                                              # FCVTWUS
  | I_FMVXW XREG ','? FREG                                                # FMVXW
  | I_FMVXS XREG ','? FREG                                                # FMVXS
  | I_FEQS XREG ','? FREG ','? FREG                                       # FEQS
  | I_FLTS XREG ','? FREG ','? FREG                                       # FLTS
  | I_FLES XREG ','? FREG ','? FREG                                       # FLES
  | I_FCLASSS XREG ','? FREG                                              # FCLASSS
  | I_FCVTSW FREG ','? XREG                                               # FCVTSW
  | I_FCVTSWU FREG ','? XREG                                              # FCVTSWU
  | I_FMVWX FREG ','? XREG                                                # FMVWX
  | I_FMVSX FREG ','? XREG                                                # FMVSX
  ;

rv32d_ext
  : I_FLD FREG ','? (XREG ','? lo_expr | lo_expr '(' XREG ')')            # FLD
  | I_FSD (XREG ','? FREG ','? lo_expr | FREG ','? lo_expr '(' XREG ')')  # FSD
  | I_FMADDD FREG ','? FREG ','? FREG ','? FREG                           # FMADDD
  | I_FMSUBD FREG ','? FREG ','? FREG ','? FREG                           # FMSUBD
  | I_FNMSUBD FREG ','? FREG ','? FREG ','? FREG                          # FNMSUBD
  | I_FNMADDD FREG ','? FREG ','? FREG ','? FREG                          # FNMADDD
  | I_FADDD FREG ','? FREG ','? FREG                                      # FADDD
  | I_FSUBD FREG ','? FREG ','? FREG                                      # FSUBD
  | I_FMULD FREG ','? FREG ','? FREG                                      # FMULD
  | I_FDIVD FREG ','? FREG ','? FREG                                      # FDIVD
  | I_FSQRTD FREG ','? FREG                                               # FSQRTD
  | I_FSGNJD FREG ','? FREG ','? FREG                                     # FSGNJD
  | I_FSGNJND FREG ','? FREG ','? FREG                                    # FSGNJND
  | I_FSGNJXD FREG ','? FREG ','? FREG                                    # FSGNJXD
  | I_FMIND FREG ','? FREG ','? FREG                                      # FMIND
  | I_FMAXD FREG ','? FREG ','? FREG                                      # FMAXD
  | I_FCVTSD FREG ','? FREG                                               # FCVTSD
  | I_FCVTDS FREG ','? FREG                                               # FCVTDS
  | I_FEQD XREG ','? FREG ','? FREG                                       # FEQD
  | I_FLTD XREG ','? FREG ','? FREG                                       # FLTD
  | I_FLED XREG ','? FREG ','? FREG                                       # FLED
  | I_FCLASSD XREG ','? FREG                                              # FCLASSD
  | I_FCVTWD XREG ','? FREG                                               # FCVTWD
  | I_FCVTWUD XREG ','? FREG                                              # FCVTWUD
  | I_FCVTDW FREG ','? XREG                                               # FCVTDW
  | I_FCVTDWU FREG ','? XREG                                              # FCVTDWU
  ;

rv32i_ext_pseudos
  : I_LA XREG ','? id_expr                                                # LAPSEUDO
  | I_LLA XREG ','? id_expr                                               # LLAPSEUDO
  | I_LB XREG ','? id_expr                                                # LBPSEUDO
  | I_LH XREG ','? id_expr                                                # LHPSEUDO
  | I_LW XREG ','? id_expr                                                # LWPSEUDO
  | I_SB XREG ','? id_expr ','? XREG                                      # SBPSEUDO
  | I_SH XREG ','? id_expr ','? XREG                                      # SHPSEUDO
  | I_SW XREG ','? id_expr ','? XREG                                      # SWPSEUDO
  | I_NOP                                                                 # NOPPSEUDO
  | I_LI XREG ','? expr                                                   # LIPSEUDO
  | I_MV XREG ','? XREG                                                   # MVPSEUDO
  | I_NOT XREG ','? XREG                                                  # NOTPSEUDO
  | I_NEG XREG ','? XREG                                                  # NEGPSEUDO
  | I_SEQZ XREG ','? XREG                                                 # SEQZPSEUDO
  | I_SNEZ XREG ','? XREG                                                 # SNEZPSEUDO
  | I_SLTZ XREG ','? XREG                                                 # SLTZPSEUDO
  | I_SGTZ XREG ','? XREG                                                 # SGTZPSEUDO
  | I_BEQZ XREG ','? expr                                                 # BEQZPSEUDO
  | I_BNEZ XREG ','? expr                                                 # BNEZPSEUDO
  | I_BLEZ XREG ','? expr                                                 # BLEZPSEUDO
  | I_BGEZ XREG ','? expr                                                 # BGEZPSEUDO
  | I_BLTZ XREG ','? expr                                                 # BLTZPSEUDO
  | I_BGTZ XREG ','? expr                                                 # BGTZPSEUDO
  | I_BGT XREG ','? XREG ','? expr                                        # BGTPSEUDO
  | I_BLE XREG ','? XREG ','? expr                                        # BLEPSEUDO
  | I_BGTU XREG ','? XREG ','? expr                                       # BGTUPSEUDO
  | I_BLEU XREG ','? XREG ','? expr                                       # BLEUPSEUDO
  | I_J expr                                                              # JPSEUDO
  | I_JAL expr                                                            # JALPSEUDO
  | I_JR XREG                                                             # JRPSEUDO
  | I_JALR XREG                                                           # JALRPSEUDO
  | I_RET                                                                 # RETPSEUDO
  | I_CALL ','? expr                                                      # CALLPSEUDO
  | I_TAIL ','? expr                                                      # TAILPSEUDO
  ;

rv32zicsr_ext_pseudos
  : I_RDINSTRET XREG                                                      # RDINSTRETPSEUDO
  | I_RDCYCLE XREG                                                        # RDCYCLEPSEUDO
  | I_RDTIME XREG                                                         # RDTIMEPSEUDO
  | I_CSRR XREG ','? expr                                                 # CSRRPSEUDO
  | I_CSRW expr ','? XREG                                                 # CSRWPSEUDO
  | I_CSRS expr ','? XREG                                                 # CSRSPSEUDO
  | I_CSRC expr ','? XREG                                                 # CSRCPSEUDO
  | I_CSRWI expr ','? INT                                                 # CSRWIPSEUDO
  | I_CSRSI expr ','? INT                                                 # CSRSIPSEUDO
  | I_CSRCI expr ','? INT                                                 # CSRCIPSEUDO
  | I_FRCSR XREG                                                          # FRCSRPSEUDO
  | I_FSCSR XREG                                                          # FSCSRPSEUDO
  | I_FSCSR XREG ','? XREG                                                # FSCSR2PSEUDO
  | I_FRRM XREG                                                           # FRRMPSEUDO
  | I_FSRM XREG                                                           # FSRMPSEUDO
  | I_FSRM XREG ','? XREG                                                 # FSRM2PSEUDO
  | I_FRFLAGS XREG                                                        # FRFLAGSPSEUDO
  | I_FSFLAGS XREG                                                        # FSFLAGSPSEUDO
  | I_FSFLAGS XREG ','? XREG                                              # FSFLAGS2PSEUDO
  ;

rv32f_ext_pseudos
  : I_FLW FREG ','? id_expr ','? XREG                                     # FLWPSEUDO
  | I_FSW FREG ','? id_expr ','? XREG                                     # FSWPSEUDO
  | I_FMVS FREG ','? FREG                                                 # FMVSPSEUDO
  | I_FABSS FREG ','? FREG                                                # FABSSPSEUDO
  | I_FNEGS FREG ','? FREG                                                # FNEGSPSEUDO
  ;

rv32d_ext_pseudos
  : I_FLD FREG ','? id_expr ','? XREG                                     # FLDPSEUDO
  | I_FSD FREG ','? id_expr ','? XREG                                     # FSDPSEUDO
  | I_FMVD FREG ','? FREG                                                 # FMVDPSEUDO
  | I_FABSD FREG ','? FREG                                                # FABSDPSEUDO
  | I_FNEGD FREG ','? FREG                                                # FNEGDPSEUDO
  ;

static_data
  : D_BYTE expr (','? expr)*                                              # BYTE
  | D_HALF expr (','? expr)*                                              # HALF
  | D_WORD expr (','? expr)*                                              # WORD
  | D_FLOAT expr (','? expr)*                                             # FLOAT
  | D_DOUBLE expr (','? expr)*                                            # DOUBLE
  | D_ASCII STRING                                                        # ASCII
  | D_STRING STRING                                                       # ASCIIZ
  | D_ZERO int_expr                                                       # ZERO
  ;

directive
  : '.section'? D_BSS                                                     # BSS
  | '.section'? D_DATA                                                    # DATA
  | '.section'? D_RODATA                                                  # RODATA
  | '.section'? D_TEXT                                                    # TEXT
  | D_GLOBL id_expr (','? id_expr)*                                       # GLOBL
  | D_ALIGN int_expr                                                      # ALIGN
  | D_BALIGN int_expr                                                     # BALIGN
  | D_FILE STRING                                                         # FILE
  | D_EQU ID ','? expr                                                    # EQU
  ;

id_expr
  : ID                                                                    # IDEXPR
  ;

int_expr
  : INT                                                                   # INTEXPR
  ;

float_expr
  : FLOAT                                                                 # FLOATEXPR
  ;

expr
  : '(' expr ')'                                                          # PARENEXPR
  | <assoc=right> SIGN expr                                               # UNARYEXPR
  | expr SIGN expr                                                        # OPEXPR
  | int_expr                                                              # INTEXPRN
  | id_expr                                                               # IDEXPRN
  | float_expr                                                            # FLOATEXPRN
  ;

hi_relocation
  : '%hi' '(' id_expr ')'                                                 # HIREL
  ;

lo_relocation
  : '%lo' '(' id_expr ')'                                                 # LOREL
  ;

pcrel_hi_relocation
  : '%pcrel_hi' '(' id_expr ')'                                           # PCRELHI
  ;

pcrel_lo_relocation
  : '%pcrel_lo' '(' id_expr ')'                                           # PCRELLO
  ;

hi_expr
  : hi_relocation
  | expr
  ;

lo_expr
  : lo_relocation
  | pcrel_lo_relocation
  | expr
  ;

pcrel_hi_expr
  : pcrel_hi_relocation
  | expr
  ;

/****************************************
|                 LEXER                 |
*****************************************/

fragment A: ('a'|'A');
fragment B: ('b'|'B');
fragment C: ('c'|'C');
fragment D: ('d'|'D');
fragment E: ('e'|'E');
fragment F: ('f'|'F');
fragment G: ('g'|'G');
fragment H: ('h'|'H');
fragment I: ('i'|'I');
fragment J: ('j'|'J');
fragment K: ('k'|'K');
fragment L: ('l'|'L');
fragment M: ('m'|'M');
fragment N: ('n'|'N');
fragment O: ('o'|'O');
fragment P: ('p'|'P');
fragment Q: ('q'|'Q');
fragment R: ('r'|'R');
fragment S: ('s'|'S');
fragment T: ('t'|'T');
fragment U: ('u'|'U');
fragment V: ('v'|'V');
fragment W: ('w'|'W');
fragment X: ('x'|'X');
fragment Y: ('y'|'Y');
fragment Z: ('z'|'Z');

fragment DIGIT: [0-9];
fragment HEX_DIGIT: [a-fA-F0-9];
fragment BIN_DIGIT: [01];

fragment ESCAPE: '\\' ['"?abfnrtv\\];
fragment SCHAR: ~["]|ESCAPE|'\\'?'\r'?'\n';
fragment CCHAR: ~['\\\r\n]|ESCAPE;

// I EXTENSION KEYWORDS
I_LUI: L U I;
I_AUIPC: A U I P C;
I_JAL: J A L;
I_JALR: J A L R;
I_BEQ: B E Q;
I_BNE: B N E;
I_BLT: B L T;
I_BGE: B G E;
I_BLTU: B L T U;
I_BGEU: B G E U;
I_LB: L B;
I_LH: L H;
I_LW: L W;
I_LBU: L B U;
I_LHU: L H U;
I_SB: S B;
I_SH: S H;
I_SW: S W;
I_ADDI: A D D I;
I_SLTI: S L T I;
I_SLTIU: S L T I U;
I_XORI: X O R I;
I_ORI: O R I;
I_ANDI: A N D I;
I_SLLI: S L L I;
I_SRLI: S R L I;
I_SRAI: S R A I;
I_ADD: A D D;
I_SUB: S U B;
I_SLL: S L L;
I_SLT: S L T;
I_SLTU: S L T U;
I_XOR: X O R;
I_SRL: S R L;
I_SRA: S R A;
I_OR: O R;
I_AND: A N D;
I_FENCE: F E N C E;
I_ECALL: E C A L L;
I_EBREAK: E B R E A K;

// Zifencei EXTENSION
I_FENCEI: F E N C E '.' I;

// Zicsr EXTENSION
I_CSRRW: C S R R W;
I_CSRRS: C S R R S;
I_CSRRC: C S R R C;
I_CSRRWI: C S R R W I;
I_CSRRSI: C S R R S I;
I_CSRRCI: C S R R C I;

// M EXTENSION KEYWORDS
I_MUL: M U L;
I_MULH: M U L H;
I_MULHSU: M U L H S U;
I_MULHU: M U L H U;
I_DIV: D I V;
I_DIVU: D I V U;
I_REM: R E M;
I_REMU: R E M U;

// A EXTENSION KEYWORDS
I_LRW: L R '.' W ('.' (A Q | R L | A Q R L))?;
I_SCW: S C '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOSWAPW: A M O S W A P '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOADDW: A M O A D D '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOXORW: A M O X O R '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOANDW: A M O A N D '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOORW: A M O O R '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOMINW: A M O M I N '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOMAXW: A M O M A X '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOMINUW: A M O M I N U '.' W ('.' (A Q | R L | A Q R L))?;
I_AMOMAXUW: A M O M A X U '.' W ('.' (A Q | R L | A Q R L))?;

// F EXTENSION KEYWORDS
I_FLW: F L W;
I_FSW: F S W;
I_FMADDS: F M A D D '.' S;
I_FMSUBS: F M S U B '.' S;
I_FNMSUBS: F N M S U B '.' S;
I_FNMADDS: F N M A D D '.' S;
I_FADDS: F A D D '.' S;
I_FSUBS: F S U B '.' S;
I_FMULS: F M U L '.' S;
I_FDIVS: F D I V '.' S;
I_FSQRTS: F S Q R T '.' S;
I_FSGNJS: F S G N J '.' S;
I_FSGNJNS: F S G N J N '.' S;
I_FSGNJXS: F S G N J X '.' S;
I_FMINS: F M I N '.' S;
I_FMAXS: F M A X '.' S;
I_FCVTWS: F C V T '.' W '.' S;
I_FCVTWUS: F C V T '.' W U '.' S;
I_FMVXW: F M V '.' X '.' W;
I_FMVXS: F M V '.' X '.' S;
I_FEQS: F E Q '.' S;
I_FLTS: F L T '.' S;
I_FLES: F L E '.' S;
I_FCLASSS: F C L A S S '.' S;
I_FCVTSW: F C V T '.' S '.' W;
I_FCVTSWU: F C V T '.' S '.' W U;
I_FMVWX: F M V '.' W '.' X;
I_FMVSX: F M V '.' S '.' X;

// D EXTENSION KEYWORDS
I_FLD: F L D;
I_FSD: F S D;
I_FMADDD: F M A D D '.' D;
I_FMSUBD: F M S U B '.' D;
I_FNMSUBD: F N M S U B '.' D;
I_FNMADDD: F N M A D D '.' D;
I_FADDD: F A D D '.' D;
I_FSUBD: F S U B '.' D;
I_FMULD: F M U L '.' D;
I_FDIVD: F D I V '.' D;
I_FSQRTD: F S Q R T '.' D;
I_FSGNJD: F S G N J '.' D;
I_FSGNJND: F S G N J N '.' D;
I_FSGNJXD: F S G N J X '.' D;
I_FMIND: F M I N '.' D;
I_FMAXD: F M A X '.' D;
I_FCVTSD: F C V T '.' S '.' D;
I_FCVTDS: F C V T '.' D '.' S;
I_FEQD: F E Q '.' D;
I_FLTD: F L T '.' D;
I_FLED: F L E '.' D;
I_FCLASSD: F C L A S S '.' D;
I_FCVTWD: F C V T '.' W '.' D;
I_FCVTWUD: F C V T '.' W U '.' D;
I_FCVTDW: F C V T '.' D '.' W;
I_FCVTDWU: F C V T '.' D '.' W U;

// I EXTENSION PSEUDO KEYWORDS
I_LA: L A;
I_LLA: L L A;
I_NOP: N O P;
I_LI: L I;
I_MV: M V;
I_NOT: N O T;
I_NEG: N E G;
I_SEQZ: S E Q Z;
I_SNEZ: S N E Z;
I_SLTZ: S L T Z;
I_SGTZ: S G T Z;
I_BEQZ: B E Q Z;
I_BNEZ: B N E Z;
I_BLEZ: B L E Z;
I_BGEZ: B G E Z;
I_BLTZ: B L T Z;
I_BGTZ: B G T Z;
I_BGT: B G T;
I_BLE: B L E;
I_BGTU: B G T U;
I_BLEU: B L E U;
I_J: J;
I_JR: J R;
I_RET: R E T;
I_CALL: C A L L;
I_TAIL: T A I L;

// Zicsr EXTENSION PSEUDO KEYWORDS
I_RDINSTRET: R D I N S T R E T H?;
I_RDCYCLE: R D C Y C L E H?;
I_RDTIME: R D T I M E H?;
I_CSRR: C S R R;
I_CSRW: C S R W;
I_CSRS: C S R S;
I_CSRC: C S R C;
I_CSRWI: C S R W I;
I_CSRSI: C S R S I;
I_CSRCI: C S R C I;
I_FRCSR: F R C S R;
I_FSCSR: F S C S R;
I_FRRM: F R R M;
I_FSRM: F S R M;
I_FRFLAGS: F R F L A G S;
I_FSFLAGS: F S F L A G S;

// F EXTENSION PSEUDO KEYWORDS
I_FMVS: F M V '.' S;
I_FABSS: F A B S '.' S;
I_FNEGS: F N E G '.' S;

// D EXTENSION PSEUDO KEYWORDS
I_FMVD: F M V '.' D;
I_FABSD: F A B S '.' D;
I_FNEGD: F N E G '.' D;

// DIRECTIVES
D_BSS: '.bss';
D_DATA: '.data';
D_RODATA: '.rodata';
D_TEXT: '.text';
D_GLOBL: ('.globl'|'.global');
D_ALIGN: ('.align'|'.p2align');
D_BALIGN: '.balign';
D_FILE: '.file';
D_EQU: ('.equ'|'.equiv');
D_BYTE: '.byte';
D_HALF: ('.half'|'.short'|'.2byte');
D_WORD: ('.word'|'.long'|'.4byte');
D_FLOAT: '.float';
D_DOUBLE: '.double';
D_ASCII: '.ascii';
D_STRING: ('.string'|'.asciiz'|'.asciz');
D_ZERO: ('.zero'|'.space');

// REGISTERS
XREG: ('x'('3'[01]|[12][0-9]|[0-9])|'zero'|'ra'|'sp'|'gp'|'tp'|'fp'|'t'[0-6]|'a'[0-7]|'s'('10'|'11'|[0-9]));
FREG: ('f'('3'[01]|[12][0-9]|[0-9])|'ft'('10'|'11'|[0-9])|'fa'[0-7]|'fs'('10'|'11'|[0-9]));

// NUMBERS
FLOAT: (DIGIT* '.' DIGIT+ (E SIGN? DIGIT+)?| 'NaN' | 'nan' | 'inf' | 'Inf');
INT: (DIGIT+|'0' X HEX_DIGIT+|'0' B BIN_DIGIT+|'\'' CCHAR+? '\'');

// SIGN
SIGN: ('+'|'-');

// STRINGS AND CHARS
STRING: '"' SCHAR*? '"';

// IDENTIFIERS
ID: [a-zA-Z_$@][@.a-zA-Z0-9_$]*;

// LABELS
LABEL: [a-zA-Z_$@][@.a-zA-Z0-9_$]*?':';

// NEWLINE
EOL: [\n\r]+;

// COMMENTS
COMMENT: (';'|'#') ~[\r\n]* -> skip;

// IGNORE WHITESPACE
WHITESPACE: [ \t]+ -> skip;

// Everything else (captures unrecognized characters)
EVERYTHING : .;
