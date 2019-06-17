// Student name: lyu xiaoxi
// Student ID  : 55544909

/*
    Submission deadline: Friday, 21 June 2019, 4 pm. Upload your Java file to canvas.

    Run-length encoding (RLE) is a popular encoding method to compress a stream 
    of symbols. We shall consider the simple case for a stream of 0 and 1. 
    To avoid confusion, we use the letter O to represent 0 and letter I to  
    represent 1 in the encoded data stream. 

    In RLE, a run (consecutive sequence) of the same symbol is represented by a 
    count-symbol pair.

    Example:
    Original data stream: 000000101111101000000000010
    Simple RLE stream:    6O1I1O5I1O1I10O1I1O

    A single character of 1 or 0 is quite common in a data stream. 
    One can see that the simple RLE is not very efficient in encoding single 
    instances of 0 or 1. A revised RLE encoding is to encode runs of 2 or more 
    characters only. The start of a run is represented by 2 consecutive character 
    of the given symbol.

    Original data stream: 000000101111101000000000010
    Revised RLE stream:   OO6IOII5OIOO10IO

    Your task is to implement the compress() and expand() methods in 
    the class RunLengthCode using the revised RLE. 

    In this exercise, you may make use of the class StringBuffer.
    StringBuffer object is mutable. It is more convenient and efficient to
    use a StringBuffer object to store the intermediate result of a computation.

    You can use the append() method to append a char, a string, or an integer 
    to a StringBuffer object.

    You can use the toString() method to produce a String object from a 
    StringBuffer object.

    You may need to use the following methods of the String class
       int length()
       String substring(int, int) 
       char charAt(int)

    To check if a char is a digit, you can use the static method
    Character.isDigit(char)

    To convert a string of digits to an integer value, you can use the static 
    method Integer.parseInt(String)
*/

public class RunLengthCode 
{
    public static void main(String[] args) 
    {
        // test data
        String msg1 = "000000101111101000000000010";
        String msg2 = "1111111111110111111111111000111111111111111111111111011111111111111";
        String rleMsg1 = "OO6IOII5OIOO10I";
        String rleMsg2 = "II12OII12OO3II24OII14";

        test(1, msg1, rleMsg1);
        test(2, msg2, rleMsg2);
    }
    
    public static void test(int n, String msg, String codedMsg)
    {
        System.out.println("Test " + n + ": ");
        String temp = compress(msg);
        System.out.println("compressed message");
        System.out.println(temp);
        if (!temp.equals(codedMsg))
            System.out.println("  *** Error in the compress method");
        
        temp = expand(codedMsg);
        System.out.println("expanded message");
        System.out.println(temp);
        if (!temp.equals(msg))
            System.out.println("  *** Error in the expand method");   
        System.out.println();
    }
    
    // Compress the input message to RLE encoded message.
    // The input message is a sequence of '0' and '1'.
    // We use the letter 'O' to represent '0' and 'I' to represent '1' in the encoded message.
    public static String compress(String msg) 
    { 
        StringBuffer buf = new StringBuffer();
        StringBuffer result = new StringBuffer();
        int i, length, l;
        int substring_front = 0;
        int substring_rear = 0;
        // Your codes

        buf.append(msg);
        for(i = 0; i < msg.length() - 1; i++)
        {
            char current = buf.charAt(i);
            char next = buf.charAt(i + 1);

            if (Character.isDigit(current) && Character.isDigit(next))
            {
                if (current != next)
                {
                    substring_rear = i;
                    length =  substring_rear - substring_front;
                   if (length > 1)
                   {
                       if (current == '0'){
                           result.append("OO" + (length+1));
                       }else{
                           result.append("II" + (length+1));
                       }
                   }else{
                        if (current == '0'){
                            result.append("O");
                        }else{
                            result.append("I");
                        }
                   }
                   substring_front = substring_rear + 1;
                }

                // process the last substring separately.
                if ( i == msg.length() - 2)
                {
                    l = (msg.length() - 1) - substring_front;
                    if (l > 1){
                        if (next == '0'){
                           result.append("OO" + (l + 1));
                       }else{
                           result.append("II" + (l + 1));
                       }
                    }else{
                        if (next == '0'){
                            result.append("O");
                        }else{
                            result.append("I");
                        }
                    }
                }
                //
            }else{
                System.out.println("Input must be a digit!");
            }
        }

       return result.toString();
    }
    
    // Expand the RLE encoded message to original message.
    // The original message is a sequence of '0' and '1'.
    public static String expand(String codedMsg) 
    {        
        StringBuffer buf = new StringBuffer();
        StringBuffer result = new StringBuffer();
        int i;
        int start = 0;

        // Your codes
        buf.append(codedMsg);
        for (i = 0; i < codedMsg.length() - 1; i++)
        {
            char current = buf.charAt(i);
            char next = buf.charAt(i + 1);

            //split the string into e.g. "II8", "O", "OO12" format then process it.
            if (Character.isDigit(current) && !Character.isDigit(next))
            {
                result.append(transform(buf.substring(start, i + 1)));
                start = i + 1;
            }

            if (!Character.isDigit(current) && !Character.isDigit(next) && (current != next))
            {
                result.append(transform(buf.substring(start, i + 1)));
                start = i + 1;
            }

            //process the last substring separately.
            if (i == codedMsg.length() - 2)
            {
                result.append(transform(buf.substring(start, i + 2)));
            }
        }

        return result.toString();
    }

    public static String transform(String input)
    {
        StringBuffer tmp = new StringBuffer();
        StringBuffer output = new StringBuffer();
        int l = input.length();
        int i, counter, j;

        if (l == 1)
        {
            if (input.equals("O"))
            {
                output.append("0");
            }else if (input.equals("I"))
            {
                output.append("1");
            }

        }else{
            for (i = 0; i < l; i++)
            {
                if (Character.isDigit(input.charAt(i)))
                {
                    tmp.append(input.charAt(i));
                }
            }
            counter = Integer.parseInt(tmp.toString());

            if (input.charAt(0) == 'O')
            {
                for (j = 0; j < counter; j++)
                {
                    output.append("0");
                }
            }else{
                for (j = 0; j < counter; j++)
                {
                    output.append("1");
                }
            }
        }
        return output.toString();
    }
}

