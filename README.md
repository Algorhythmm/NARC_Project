# NARC_Project

This is a Computer Architecture assignment I completed while enrolled at SHSU.

The original prompt "TermProject-s18.docx" has been provided as the entire scope of the project would be difficult to explain
in a single README file.

This program emulates a simple computer called NARC (for "Not A Real Computer") that uses a 16-bit instruction set that is somewhat similar in structure to the 32-bit
instruction set used by MIPS.

Instriction Format:

    Bits  15-12 represent the opcode - (The opcode is a unique bit pattern that encodes a primitive operation the NARC computer can perform)
    Bit   11    is the opcode extension bit - (NARC consists of only 16 instructions for simplicity so this bit is unused. We could utilize this bit to expand to 32 instructions.)
    Bit   10    is the Indirect flag - (set to 1 when if indirect addressing is used)
    Bit   9-8   are used for the Index Flag - (These bits select one of three registers when indexed addressing is called for, or the instruction manipulates an index register.)
    Bits  7-0   represent the Address (These 8 bits represent the memory address in instructions that refer to memory. If the instruction does not refer to memory, the indirect
                                        flag, index flag, and memory address fields are not used, and the opcode field represents the complete instruction.)
Because only 8-bits are available for memory addressing, NARC can directly acces 256 memory locations. This means our program (instructions) and data must always be in the first
256 locations of memory. With indexed and indirect addressing modes used, we can extend the addressing range to 64K.

Therefore, NARC has direct, indirect, and indexed addressing modes. When both indirect and indexed addressing mode fields are used, the addressing mode is as indexed-indirect (pre-indexed indirect).

Index Registers:

    Bit-9     Bit-8       Index-Register-Selected
      0         0                   None
      0         1                     1
      1         0                     2
      1         1                     3

Refer to the original prompt file "TermProject-s18.docx" for in-depth documentation of the NARC instruction set.



## About the Files

In addition to the "TermProject-s18.docx" file, the professor provided 3 files for us to begin our project.

> "Divide.asm" represents the assembly instructions used to generate the binary file the NARC machine uses for instructions
> "Divide.bin" binary representation of the "Divide.asm" file
> "StackMachineStub.java" java source code provided by professor as a starting point.

#### The "NARC_Machine.java" file is my final submission of the project, i.e this file shows MY work.






###Don't Cheat###

This is an assignment I completed while attending SHSU. If you're viewing this code with the intent to cheat, you'll likely be caught.
