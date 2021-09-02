import java.util.Stack;

// here's a start of a program in case you don't know where to begin;

public class StackMachineStub
{
	final int MAX_CODE = 65536;   	// size of code memory
	final int MAX_DATA = 65536;		// size of data memory

	final int HALT 	= 0;			// a few opcodes defined
	final int PUSH 	= 1;
	final int RVALUE = 2;
	final int LVALUE = 3;

	final int ASGN 	= 4;

	final int ADD 	= 5;


	int [] code;					// reference for the memory modules
	int [] data;
	int PC;							// we need a couple of registers
	int IR;
	boolean run;
	Stack<Integer> stack;			// data stack
	Stack<Integer> callStack;		// call stack

	public StackMachineStub()
	{

		code = new int[MAX_CODE];		// constructor initializes the stack machine;
		data = new int[MAX_DATA];
		PC = 0;
		run = true;
		stack = new Stack<Integer>();
		callStack = new Stack<Integer>();

	}


	public void execute()
	{
		while(run)							// the old fetch-execute cycle
		{
			getNextInstruction();
			decodeAndRunInstruction();
		}
	}

	public void getNextInstruction()
	{
		IR = code[PC++];					// fetching is straighforward
	}

	public void decodeAndRunInstruction()
	{
		int opcode = IR >> 16;				// pull of the opcode and the operands
		int	operand   = IR & 0xFFFF;

		switch(opcode)						// what we do depends on what opcode we have
		{									// most of these are pretty straight forward
			case HALT:
				run = false;
				break;
			case PUSH:						// push a literal value
				stack.push(operand);
				break;
			case RVALUE:					// push the contents of a memory address
				stack.push(data[operand]);
				break;
			case LVALUE:					// push the address (which, come to think of it, is exactly the same as pushing a literal)
				stack.push(operand);
				break;

			case ASGN:						// store the value on top of the stack in the address directly below it on the stack
				int rvalue = stack.pop();
				int lvalue = stack.pop();
				data[lvalue] = rvalue;
				break;

			default:						// oops!
				System.err.println("Unimplemented opcode");
				System.exit(opcode);
		}
		if (IR == 0) run = false; 			// Isn't this redundant?  Oh, well leave it in for now
	}


	public static void main(String [] args)
	{
		StackMachine vm = new StackMachine();			// create our virtual stack machine


		System.out.println("Beginning Execution...");
		vm.execute();									// and, let it go, let it go .....
		System.out.println("Done");
	}

}
