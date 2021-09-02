import java.util.Scanner;
import java.io.*;

public class NARC_Machine
{
	Scanner sc = new Scanner(System.in);
	final int MAX_MEM = 65536;   	// size memory

//opcodes defined
	final int HALT 	= 0; //Halt/Stop
	final int LDA = 1;	 //Load Address
	final int STA = 2;   //Store Address
	final int ADD = 3;	 //Add
	final int TCA = 4;   //Two's Compliment
	final int BRU = 5;   //Branch Unconditional
	final int BIP = 6;   //Branch if Positive
	final int BIN = 7;   //Branch if Negative
	final int RWD = 8;   //Read Word
	final int WWD = 9;   //Write Word
	final int SHL = 10;  //Shift left ACC once
	final int SHR = 11;  //Shift right ACC once
	final int LDX = 12;  //Load index
	final int STX = 13;  //Store index
	final int TIX = 14;  //Test index increment
	final int TDX = 15;  //Test index decrement

						// reference for the memory modules
	int [] data;
	int PC;							// we need a couple of registers
	int IR;
	int ACC = 0;
	boolean run;
	

	public NARC_Machine() throws IOException
	{
		//it seems you need to get rid of either code or data array and keep the other.
		//NARC Machine will only work using 1 array for memory space.
		//just make sure you load the array with all of your instructions first,
		//then run the program.
		data = new int[MAX_MEM];		// constructor initializes the stack machine;
		//code = new int[MAX_CODE];    //for instance I got rid of this code array and used data as my memory

		PC = 0;
		run = true;
	}

	// the old fetch-execute cycle
	public void execute()
	{
		while(run)
		{
			getNextInstruction();
			decodeAndRunInstruction();
		}
	}

	public void getNextInstruction()
	{
		IR = data[PC++];	// fetching is straighforward
	}

	public void decodeAndRunInstruction()
	{
		// pull off the opcode and the operands
		int opcode = IR >> 12;
		int	operand   = IR & 0XFF;

		// what we do depends on what opcode we have
		// most of these are pretty straight forward
		switch(opcode)
		{
			//Halt/Stop
			case HALT:
				run = false;
				break;
			//Load Address
			case LDA:
				 ACC = data[operand];
				break;
			//Store Address
			case STA:
				data[operand] = ACC;
				break;
			//Add
			case ADD:
				ACC = ACC + data[operand];
				break;
			//Two's Compliment
			case TCA:
				ACC = (~ACC) + 1;
				break;
			//Branch Unconditional
			case BRU:
				PC = operand;
				break;
			//Branch if Positive
			case BIP:
				if (ACC > 0) {
					PC = operand;
				}
				break;
			//Branch if Negative
			case BIN:
				if (ACC < 0) {
					PC = operand;
				}
				break;
			//Read Word
			case RWD:
				System.out.print("Awaiting Input:  ");
				int rwd = sc.nextInt();
				ACC = rwd;
				break;
			//Write Word
			case WWD:
				System.out.println(ACC);
				break;
			//Shift ACC left once
			case SHL:
				ACC = ACC << 1;
				break;
			//Shift ACC right once
			case SHR:
				ACC = ACC >> 1;
				break;
			//Load index
			case LDX:
				IR = data[operand];
				break;
			//Store index
			case STX:
				data[operand] = IR;
				break;
			//Test Index increment
			case TIX:
				IR += IR;
				break;
			//Test Index decrement
			case TDX:
				IR -= IR;
				break;
			//Oops!
			default:
				System.err.println("Unimplemented opcode: " + opcode);
				System.exit(opcode);
		}
		if (IR == 0) run = false; 			// Isn't this redundant?  Oh, well leave it in for now
	}


	public void ReadFile(String fileName) throws IOException
	{
		//Create the binary file input streams
				FileInputStream fstream =  new FileInputStream(fileName);
				DataInputStream inputFile = new DataInputStream(fstream);
				String binaryString;
				char twoByte;
				Boolean endOfFile = false;
				//Read the contents of the file
				for (int i = 0; !endOfFile; i++) {
					int instr;
					try {
						//grab two bytes
						twoByte = inputFile.readChar();
						//convert to string
						binaryString = Integer.toBinaryString(twoByte);
						binaryString = getBinary(binaryString);
						//binaryString now holds a 16-bit binary string
						//store this value into the code[] array starting at index 0
						//and working your way up.
						instr = Integer.parseInt(binaryString, 2);
						data[i] = instr;


					} catch (EOFException e) {
						endOfFile = true;
						//always remember to close the file
						inputFile.close();
					}
		}
	} //end ReadFile

	public static String getBinary(String bin) // prints 16-bit binary string with preceding zeros
	{
			  String binary;
			  int printCount;
			   if (bin.length() < 16)
			   	{
					binary = "0";
			  		printCount = 16 - bin.length();
			  		for(int  i = 0; i < (printCount -1); i++)
			  		{
			  		binary = binary + "0";
			  		}
			  		binary = binary + bin;
			  	}
			  	else {
					binary = bin;
				}
	            return binary;
	  } // end printBinary


	public static void main(String [] args) throws IOException
	{

		NARC_Machine NARC = new NARC_Machine();			// create our virtual NARC machine
		NARC.ReadFile("./Divide.bin");

		System.out.println("Beginning Execution...");
		NARC.execute();									// and, let it go, let it go .....
		System.out.println("Done");
	}

}