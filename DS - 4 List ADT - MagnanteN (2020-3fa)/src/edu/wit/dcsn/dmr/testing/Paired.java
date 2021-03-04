/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab: List ADT
 * Fall, 2020
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post nor otherwise share this code with anyone other than
 * current students in my sections of this course. Violation of these usage
 * restrictions will be considered a violation of the Wentworth Institute of
 * Technology Academic Honesty Policy.
 *
 * Do not remove this notice.
 *
 * @formatter:on
 */


package edu.wit.dcsn.dmr.testing ;

/**
 * Utility class to facilitate testing if {@code sort()} is stable
 * <p>
 * Note: This class is immutable
 *
 * @author Dave Rosenberg
 * @version 1.0.0 2020-07-18 Initial implementation
 */
public final class Paired implements Comparable<Paired>
    {

    // static/class variable
    private static int nextId = 1 ;     // counter for assignment to this.id

    // instance variables
    private final int id ;
    private final int value ;

    /**
     * Fully initialize an instance - id is automatically assigned
     *
     * @param initialValue
     *     'corresponds' to the id
     */
    public Paired( final int initialValue )
        {
        this.id = Paired.nextId++ ;

        this.value = initialValue ;

        }   // end 1-arg constructor


    /**
     * retrieve the instance's id
     *
     * @return the id
     */
    public int getId()
        {
        return this.id ;

        }   // end getId()


    /**
     * retrieve the instance's value
     *
     * @return the value
     */
    public int getValue()
        {
        return this.value ;

        }   // end getValue()


    /**
     * ordering is based upon this.value
     *
     * @param otherPaired
     *     the other instance of Paired to which this instance is ordered
     * @return 0 if the two instances have the same value; a negative value if this
     *     should come before otherPaired; a positive value (greater than 0) if this
     *     should come after otherPaired
     */
    @Override
    public int compareTo( final Paired otherPaired )
        {

        return this.value - otherPaired.value ;

        }   // end compareTo()


    @Override
    public boolean equals( final Object otherObject )
        {
        if ( this == otherObject )
            {
            return true ;
            }
        
        if ( ! ( otherObject instanceof Paired ) )
            {
            return false ;
            }
        
        final Paired otherPaired = (Paired) otherObject ;
        if ( this.id != otherPaired.id )
            {
            return false ;
            }
        
        if ( this.value != otherPaired.value )
            {
            return false ;
            }
        
        return true ;
        
        }   // end equals()


    @Override
    public int hashCode()
        {
        final int prime = 31 ;
        int result = 1 ;
        result = ( prime * result ) + this.id ;
        result = ( prime * result ) + this.value ;
        return result ;
        
        }   // end hashCode()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return String.format( "%,2d(%,d)", this.value, this.id ) ;

        }   // end toString()

    }   // end class Paired