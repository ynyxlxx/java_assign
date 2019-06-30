import java.math.MathContext;

/**
 * Representation of a Fraction object must fulfill the 3 requirements:
 * 1. Value of the denominator > 0
 * 2. The numerator and denominator are relatively prime, e.g. 2/6 is normalized to 1/3
 * 3. There is only one representation of the value Zero, i.e. 0/1
 * 
 * In this exercise, implements the constructor, methods add, subtract, multiply,
 * divide, compareTo, equals and increment.
 */

public class Fraction implements Comparable<Fraction>
{
    private final long numerator;   // value cannot be changed after initialization
    private final long denominator;

    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ZERO = new Fraction();
    
    //utility function to compute the greatest common divisor of 2 positive integers
    //precondition: n >= 0 and m > 0
    private static long gcd(long n, long m) 
    {       
        long r = n % m;

        while (r > 0)
        {
            n = m;
            m = r;
            r = n % m;
        }
        return m;
    }

    /**
     * default constructor
     */
    public Fraction()
    {
        numerator = 0;
        denominator = 1;
    }

    /**
     * @param n is the numerator
     * @param d is the denominator
     * Precondition: d != 0
     * Create a new instance of Fraction such that the numerator and denominator
     * are relatively prime, and denominator > 0.
     */
    public Fraction(long n, long d)
    {
        int sign = 1;
        long temp_n, temp_d;

        temp_n = Math.abs(n);
        temp_d = Math.abs(d);

        long temp_gcd = gcd(temp_n, temp_d);

        if ((n < 0 && d < 0) || (n > 0 && d > 0)){
            sign = 1;
        }
        if ((n > 0 && d < 0) || (n < 0 && d > 0)){
            sign = -1;
        }
        // Your codes
        if (temp_d != 0){
            if (n != 0){
            numerator = sign * (temp_n / temp_gcd);
            denominator = temp_d / temp_gcd;
            }else{
                numerator = 0;
                denominator = 1;
            }
        }else{
            throw new NumberFormatException("Denominator cannot be 0.");
        }
    }
    
    /**
     *
     * @param other
     * @return a new instance of Fraction that represents this + other
     */
    public Fraction add(Fraction other)
    {
        long n = this.numerator * other.denominator + this.denominator * other.numerator;
        long d = this.denominator * other.denominator;
        return new Fraction(n, d);
    }

    /**
     *
     * @param other
     * @return a new instance of Fraction that represents this - other
     */
    public Fraction subtract(Fraction other)
    {
        Fraction temp = new Fraction(-other.numerator, other.denominator);
        return this.add(temp);
    }

     /**
     *
     * @param other
     * @return a new instance of Fraction that represents this * other
     */
    public Fraction multiply(Fraction other)
    {
        // Your codes
        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);

    }

     /**
     *
     * @param other
     * @return a new instance of Fraction that represents this / other
     */
    public Fraction divide(Fraction other)
    {
        // Your codes

        return new Fraction(this.numerator * other.denominator, this.denominator * other.numerator);  // dummy return statement
    }
    
    /**
     * 
     * @return a new instance of Fraction that represents this incremented by 1
     */
    public Fraction increment()
    {
        return this.add(ONE);
    }
    
    /**
     * Compare the value of this with other
     * @param other
     * @return 0 if this is equal to other, 
     *  return a +ve value if this is greater than other
     *  return a -ve value if this is less than other
     */    
    @Override
    public int compareTo(Fraction other) {
        // Your codes
        if ((this.subtract(other)).numerator < 0) {
            return -1;
        }

        if ((this.subtract(other)).numerator > 0) {
            return 1;
        }

        return 0;
    }


    /**
     * @param other
     * @return true if the implicit object this is equal (equivalent) to other
     */
    @Override
    public boolean equals(Object other)
    {
        // Your codes
        if (other instanceof Fraction){
        Fraction f = (Fraction) other;
        return numerator == f.numerator && denominator == f.denominator;
        }else{
            return false;
        }
    }


     /**
     *
     * @return a String object for text-based I/O
     */
    @Override
    public String toString()
    {
        return numerator + "/" + denominator;
    }
}
