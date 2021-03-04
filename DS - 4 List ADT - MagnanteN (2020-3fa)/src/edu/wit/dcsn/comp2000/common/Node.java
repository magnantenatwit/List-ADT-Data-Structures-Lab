/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab: General applicability for chains/linked constructs
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

package edu.wit.dcsn.comp2000.common ;

import static edu.wit.dcsn.dmr.testing.junit.TestData.itemToString ;

/**
 * Class Node provides the basis for singly linked-list functionality
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.1
 * 
 * @author David M Rosenberg
 * @version 4.6.0 2020-01-28 extracted Node from LinkedBag
 * @version 4.7.0 2020-08-08 switched class from package-private to public
 * @version 4.8.0 2020-11-04 add toString() to aid debugging
 * 
 * @param <T>
 *     The class of items the Node will reference.
 */
public class Node<T>
    {

    /*
     * instance variables
     */
    private T data ;        // reference to the entry
    private Node<T> next ;  // link to the next node in the chain
    
    private static int nextId = 1 ;
    
    /** pseudo address - for educational purposes only as an aid to debugging and visualization */
    public final int id ;

    /**
     * Sets up a node given supplied data; next pointer is set to null.
     *
     * @param dataPortion
     *     the information this node holds
     */
    public Node( final T dataPortion )
        {
        this( dataPortion, null ) ;

        }   // end 1-arg constructor


    /**
     * Sets up a node given supplied data and next pointer.
     *
     * @param dataPortion
     *     the information this node holds
     * @param nextNode
     *     reference to the next node in the linked list
     */
    public Node( final T dataPortion, final Node<T> nextNode )
        {
        this.data = dataPortion ;
        this.next = nextNode ;

        this.id = nextId++ ;
        
        }   // end 2-arg constructor


    /**
     * Retrieve the data referenced by this node
     *
     * @return a reference to the data stored in this node
     */
    public T getData()
        {
        return this.data ;

        }   // end getData()


    /**
     * Retrieve the next field
     *
     * @return reference to the next Node in the chain
     */
    public Node<T> getNextNode()
        {
        return this.next ;

        }   // end getNextNode()


    /**
     * Points the data field at the supplied data
     *
     * @param newData
     *     reference to the data to store
     */
    public void setData( final T newData )
        {
        this.data = newData ;

        }   // end setData()


    /**
     * Set the next field
     *
     * @param nextNode
     *     another Node in the chain or null to indicate none
     */
    public void setNextNode( final Node<T> nextNode )
        {
        this.next = nextNode ;

        } // end setNextNode()
    
    
    // toString() is generally not implemented - I provided it as a debugging aid
    @Override
    public String toString()
        {
        return String.format( "@%,d: {%s, %s%,d}",
                              this.id,
                              itemToString( this.data ),
                              this.next == null
                                  ? ""
                                  : "@",
                              this.next == null
                                  ? null
                                  : this.next.id ) ;
        
        }   // end toString()


    /**
     * (optional) test driver for Node
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // construct a chain of Nodes referencing arbitrary data
        Node<Object> firstNode = new Node<>( "A", null ) ;
        firstNode = new Node<>( 12_345, firstNode ) ;
        firstNode = new Node<>( 6_789L, firstNode ) ;
        firstNode = new Node<>( 'q', firstNode ) ;
        firstNode = new Node<>( true, firstNode ) ;
        
        // traverse the chain displaying its contents/construction
        Node<?> currentNode = firstNode ;
        System.out.printf( "firstNode: @%,d%n", firstNode.id ) ;
        while ( currentNode != null )
            {
            System.out.printf( "%s%n", currentNode ) ;
            
            currentNode = currentNode.next ;
            }

        }   // end main()

    }   // end class Node
