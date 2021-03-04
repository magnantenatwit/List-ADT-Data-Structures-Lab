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

package edu.wit.dcsn.comp2000.list.adt ;

import edu.wit.dcsn.comp2000.common.ListInterface ;
import edu.wit.dcsn.comp2000.common.Node ;

import java.util.Iterator ;
import java.util.NoSuchElementException ;

/**
 * A class that implements the ADT list by using a chain of nodes that has both a
 * head reference and a tail reference. This implementation is {@code Iterable} using
 * an inner class iterator. Two additional methods are required: {@code sort()} and
 * {@code shuffle()}.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 * @version 5.0
 * @author David M Rosenberg
 * @version 4.1.0 2018-07-11
 *     <ul>
 *     <li>initial version based upon Carrano and Henry implementation in the 4th
 *     edition of the textbook
 *     <li>modified per assignment
 *     </ul>
 * @version 5.1.0 2019-07-14
 *     <ul>
 *     <li>revise to match the 5th edition of the textbook
 *     <li>revise to match this semester's assignment
 *     <li>switch from 1-based to 0-based positions
 *     </ul>
 * @version 5.2.0 2019-11-12 track revised assignment for this semester
 * @version 5.3.0 2020-07-17
 *     <ul>
 *     <li>move Node inner class to separate class
 *     <li>update to coding standard
 *     <li>streamline some code
 *     <li>add array constructor
 *     <li>prevent {@code add(null)}
 *     <li>switch to EnhancedListInterface from ListInterface and Iterable
 *     </ul>
 * @version 5.4.0 2020-11-09
 *     <ul>
 *     <li>track changes/consolidation to ListInterface
 *     <ul>
 *     <li>remove explicit {@code implements Iterable}
 *     <li>rename class from EnhancedLinkedList to LinkedList
 *     </ul>
 *     <li>replace {@code toString()} and implement {@code main()} as a
 *     testing/debugging aid
 *     </ul>
 * @author Nicholas Magnante // DONE
 * @version 5.5.0 2020-11-09 implement sort() and shuffle()
 * @param <T>
 *     the class of objects to be stored in the list such that T implements the
 *     Comparable interface.
 */
public class LinkedList<T extends Comparable<? super T>> implements ListInterface<T>
    {

    /*
     * instance variables
     */
    private Node<T> firstNode ;     // Head reference to first node
    private Node<T> lastNode ;      // Tail reference to last node

    private int numberOfEntries ;   // Number of entries in list

    /*
     * constructor(s)
     */

    /**
     *
     */
    public LinkedList()
        {
        initializeDataFields() ;

        }	// end no-arg constructor


    /**
     * Initialize a new LinkedList and populate it with the contents of
     * {@code initialContents}
     *
     * @param initialContents
     *     an array of zero or more entries to copy to the newly instantiated
     *     LinkedList - it could be null
     */
    public LinkedList( final T[] initialContents )
        {
        // initialize state: empty
        this() ;

        // if an array is available, add its contents to 'this' in the same order
        if ( initialContents != null )
            {
            for ( final T initialItem : initialContents )
                {
                this.add( initialItem ) ;
                }
            }

        }   // end array constructor

    /*
     * public API methods
     */


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#add(java.lang.Comparable)
     */
    @Override
    public void add( final T newEntry )
        {
        // rather than duplicating code, invoke the positional method specifying
        // the position immediately after the end of the list
        add( this.numberOfEntries, newEntry ) ;

        }   // end add()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#add(int,
     * java.lang.Comparable)
     */
    @Override
    public void add( final int givenPosition,
                     final T newEntry )
        {
        if ( ( givenPosition < 0 ) || ( givenPosition > this.numberOfEntries ) )
            {
            // givenPosition was out of range
            throw new IndexOutOfBoundsException( "Illegal position given to add operation." ) ;
            }

        if ( newEntry == null )
            {
            throw new IllegalArgumentException( "new entry cannot be null" ) ;
            }

        final Node<T> newNode = new Node<>( newEntry ) ;

        if ( isEmpty() )
            {   // only (first and last) entry in empty list
            this.firstNode = newNode ;
            this.lastNode = newNode ;
            }
        else if ( givenPosition == 0 )
            {   // first entry in non-empty list
            newNode.setNextNode( this.firstNode ) ;
            this.firstNode = newNode ;
            }
        else if ( givenPosition == this.numberOfEntries )
            {   // last entry in non-empty list
            this.lastNode.setNextNode( newNode ) ;
            this.lastNode = newNode ;
            }
        else
            {   // intermediate entry in non-empty list
            final Node<T> nodeBefore = getNodeAt( givenPosition - 1 ) ;
            final Node<T> nodeAfter = nodeBefore.getNextNode() ;
            newNode.setNextNode( nodeAfter ) ;
            nodeBefore.setNextNode( newNode ) ;
            }   // end if

        this.numberOfEntries++ ;

        }   // end add()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#clear()
     */
    @Override
    public void clear()
        {
        initializeDataFields() ;

        }   // end clear()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#contains(java.lang.
     * Comparable)
     */
    @Override
    public boolean contains( final T anEntry )
        {
        Node<T> currentNode = this.firstNode ;

        // loop until we've found a matching entry or run out of entries to check
        while ( currentNode != null )
            {
            if ( currentNode.getData().equals( anEntry ) )
                {
                return true ;   // found a match
                }

            currentNode = currentNode.getNextNode() ;

            }   // end while

        // no entries matched
        return false ;

        }   // end contains()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#getEntry(int)
     */
    @Override
    public T getEntry( final int givenPosition )
        {
        if ( ( givenPosition >= 0 ) && ( givenPosition < this.numberOfEntries ) )
            {
            // return the data stored at the specified position
            return getNodeAt( givenPosition ).getData() ;

            }

        // givenPosition was out of range
        throw new IndexOutOfBoundsException( "Illegal position given to getEntry operation." ) ;

        }   // end getEntry()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#getLength()
     */
    @Override
    public int getLength()
        {
        return this.numberOfEntries ;

        }   // end getLength()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#isEmpty()
     */
    @Override
    public boolean isEmpty()
        {
        // since we have the count of entries, simple to test it
        return this.numberOfEntries == 0 ;

        }   // end isEmpty()


    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator()
        {
        return new LinkedListIterator() ;

        }   // end iterator()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#remove(int)
     */
    @Override
    public T remove( final int givenPosition )
        {
        // the entry we'll return; initially none
        T result = null ;

        if ( ( givenPosition >= 0 ) && ( givenPosition < this.numberOfEntries ) )
            {
            if ( givenPosition == 0 )
                {   // removing first entry
                // capture the data to return to the caller
                result = this.firstNode.getData() ;

                // move past the first entry to remove it from the chain
                this.firstNode = this.firstNode.getNextNode() ;

                // if this is the only entry, clear the last pointer too
                if ( this.numberOfEntries == 1 )
                    {
                    this.lastNode = null ;
                    }
                }
            else
                {   // removing an entry that isn't first
                // get a reference to the node before the one we're removing
                final Node<T> nodeBefore = getNodeAt( givenPosition - 1 ) ;

                // capture the data to return to the caller
                final Node<T> nodeToRemove = nodeBefore.getNextNode() ;
                result = nodeToRemove.getData() ;

                // update the node before's next pointer to skip the one we're
                // removing
                nodeBefore.setNextNode( nodeToRemove.getNextNode() ) ;

                // are we removing the last entry?
                if ( givenPosition == ( this.numberOfEntries - 1 ) )
                    {   // yes - need to update the reference to the now last node
                    this.lastNode = nodeBefore ;
                    }
                } // end if

            // we removed an entry - adjust the counter
            this.numberOfEntries-- ;
            }
        else
            {
            // givenPosition was out of range
            throw new IndexOutOfBoundsException( "Illegal position given to remove operation." ) ;
            }

        // return removed entry's data
        return result ;

        }   // end remove()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#replace(int,
     * java.lang.Comparable)
     */
    @Override
    public T replace( final int givenPosition,
                      final T newEntry )
        {
        if ( ( givenPosition >= 0 ) && ( givenPosition < this.numberOfEntries ) )
            {
            // locate the specified node
            final Node<T> desiredNode = getNodeAt( givenPosition ) ;

            // capture the original/prior data to return to the caller
            final T originalEntry = desiredNode.getData() ;

            // save the new data
            desiredNode.setData( newEntry ) ;

            // return the prior data
            return originalEntry ;

            }

        // givenPosition was out of range
        throw new IndexOutOfBoundsException( "Illegal position given to replace operation." ) ;
        }   // end replace()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#shuffle()
     */
    @Override
    public void shuffle()
        {
        /* @formatter:off
         *
         * To shuffle the contents of the list:
         * - split the list into two pieces; interleave the two halves, one entry at a
         *      time starting with the entry at the beginning of the first half of the list
         * - split the list into two pieces; interleave the two halves, one entry at a
         *      time starting with the entry in the middle of the list (at the beginning
         *      of the second half)
         * - split the list into two pieces; interleave the two halves, one entry at a
         *      time starting with the entry at the beginning of the first half of the list
         *
         * You are not permitted to:
         * - copy the contents of the chain into an array
         * - use any Java Class Library classes/methods
         * - use any LinkedList methods other than getReferenceTo()
         *
         * Your implementation of appendNode() can significantly affect the efficiency of
         * shuffle().  You must implement it with O(1) efficiency so the overall efficiency
         * is O(n), not O(n²) (or worse).
         *
         * @formatter:on
         */

        // DONE implement this
        Node<T> firstHalf = this.firstNode ;
        Node<T> endFirstHalf = this.getNodeAt( ( this.getLength() / 2 ) - 1 ) ;

        Node<T> secondHalf = new Node<>( null ) ;

        if ( endFirstHalf == null )
            {
            secondHalf = firstHalf ;
            }
        else
            {
            secondHalf = endFirstHalf.getNextNode() ;
            endFirstHalf.setNextNode( null ) ;
            }

        interleave( firstHalf, secondHalf ) ;

        // 2nd Pass
        firstHalf = this.firstNode ;
        endFirstHalf = this.getNodeAt( ( this.getLength() / 2 ) - 1 ) ;

        secondHalf = new Node<>( null ) ;

        if ( endFirstHalf == null )
            {
            secondHalf = firstHalf ;
            }
        else
            {
            secondHalf = endFirstHalf.getNextNode() ;
            endFirstHalf.setNextNode( null ) ;
            }

        interleave( secondHalf, firstHalf ) ;

        // 3rd Pass
        firstHalf = this.firstNode ;
        endFirstHalf = this.getNodeAt( ( this.getLength() / 2 ) - 1 ) ;

        secondHalf = new Node<>( null ) ;

        if ( endFirstHalf == null )
            {
            secondHalf = firstHalf ;
            }
        else
            {
            secondHalf = endFirstHalf.getNextNode() ;
            endFirstHalf.setNextNode( null ) ;
            }

        interleave( firstHalf, secondHalf ) ;

        }   // end shuffle()


    /**
     * Utility method to re-populate the instance chain from the two sub-chains.
     * <p>
     * Preconditions:
     * <ul>
     * <li>the instance variables ({@code firstNode} and {@code lastNode}) are both
     * {@code null}
     * <li>both sub-chains are {@code null}-terminated
     * </ul>
     *
     * @param firstHalf
     *     a reference to one sub-chain containing ~1/2 of the elements in the
     *     instance - reconstruction of the instance chain will begin with the first
     *     entry in this sub-chain (if any)
     * @param secondHalf
     *     a reference to the other sub-chain
     */
    private void interleave( Node<T> firstHalf,
                             Node<T> secondHalf )
        {
        // interleave the two sub-chains

        // DONE implement this

        this.firstNode = null ;
        this.lastNode = null ;
        this.numberOfEntries = 0 ;

        Node<T> firstHNode = new Node<>( null ) ;
        Node<T> secondHNode = new Node<>( null ) ;

        while ( ( firstHalf != null ) || ( secondHalf != null ) )
            {

            if ( firstHalf != null )
                {
                firstHNode = firstHalf ;
                appendNode( firstHNode ) ;
                firstHalf = firstHalf.getNextNode() ;
                }

            if ( secondHalf != null )
                {
                secondHNode = secondHalf ;
                appendNode( secondHNode ) ;
                secondHalf = secondHalf.getNextNode() ;
                }

            }

        /*
         * assertion: this.lastNode's next is null because it was at the end of one
         * of the sub-chains
         */

        }   // end interleave(Node, Node)


    /**
     * Add a {@code Node} to the end of the instance's chain
     *
     * @param aNode
     *     the {@code Node} to add
     */
    private void appendNode( final Node<T> aNode )
        {

        // DONE implement this
        if ( this.firstNode == null )
            {
            this.firstNode = aNode ;
            this.lastNode = aNode ;
            this.numberOfEntries++ ;
            }
        else
            {
            this.lastNode.setNextNode( aNode ) ;
            this.lastNode = aNode ;
            this.numberOfEntries++ ;
            }

        }   // end appendNode()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#sort()
     */
    @Override
    public void sort()
        {
        // only need to sort if have multiple entries
        if ( this.numberOfEntries > 1 )
            {
            insertionSort() ;
            }

        }   // end sort()


    /**
     * A linked implementation of a stable, iterative, insertion sort.
     * <p>
     * Precondition: There is at least 1 element in this list.
     */
    private void insertionSort()
        {
        /* @formatter:off
         *
         * - use a rudimentary insertion sort
         * - your sort must be stable
         * - you are not permitted to copy the contents of the chain into an array
         * - you are not permitted to use any Java Class Library classes
         *
         * @formatter:on
         */

        // DONE implement this

        if ( this.getLength() > 1 )
            {
            // Assertion: firstNode != null
            // Break chain into 2 pieces: sorted and unsorted
            Node<T> unsortedPart = this.firstNode.getNextNode() ;
            // Assertion: unsortedPart != null
            this.firstNode.setNextNode( null ) ;
            while ( unsortedPart != null )
                {
                final Node<T> nodeToInsert = unsortedPart ;
                unsortedPart = unsortedPart.getNextNode() ;
                insertInOrder( nodeToInsert ) ;
                }
            }
        }   // end insertionSort()


    /**
     * Utility method to insert a {@code Node} in its correct position in the chain
     * based upon T's natural ordering. This sort is stable.
     *
     * @param nodeToInsert
     *     the {@code Node} to insert
     */
    private void insertInOrder( final Node<T> nodeToInsert )
        {

        // DONE implement this

        final T item = nodeToInsert.getData() ;
        Node<T> currentNode = this.firstNode ;
        Node<T> previousNode = null ;

        // Locate insertion point
        while ( ( currentNode != null ) &&
                ( item.compareTo( currentNode.getData() ) >= 0 ) )
            {
            previousNode = currentNode ;
            currentNode = currentNode.getNextNode() ;
            }
        // Make the insertion
        if ( previousNode != null )
            {
            // Insert between previousNode and currentNode
            previousNode.setNextNode( nodeToInsert ) ;
            nodeToInsert.setNextNode( currentNode ) ;
            if ( currentNode == null )
                {
                this.lastNode = nodeToInsert ;
                }
            }
        else // Insert at beginning
            {
            if ( this.firstNode.getNextNode() == null )
                {
                this.lastNode = currentNode ;
                }
            nodeToInsert.setNextNode( this.firstNode ) ;
            this.firstNode = nodeToInsert ;
            }
        }   // end insertInOrder()


    /*
     * (non-Javadoc)
     * @see edu.wit.dcsn.comp2000.list.adt.ListInterface#toArray()
     */
    @Override
    public T[] toArray()
        {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )
        final T[] result = (T[]) new Comparable<?>[ this.numberOfEntries ] ;

        // start at the first position in the chain and in the array
        Node<T> currentNode = this.firstNode ;
        int index = 0 ;

        while ( currentNode != null )
            {
            // copy the data from the node into the array
            result[ index ] = currentNode.getData() ;

            // move to the next node and next array index
            currentNode = currentNode.getNextNode() ;
            index++ ;
            }   // end while

        return result ;

        }   // end toArray()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        // toString() is generally not implemented - I provided it as a debugging aid
        return String.format( "numberOfEntries: %,d; firstNode: %s%s; lastNode: %s%s%n%s",
                              this.numberOfEntries,
                              this.firstNode == null
                                  ? ""
                                      : "@",
                              this.firstNode == null
                                  ? null
                                      : this.firstNode.id,
                              this.lastNode == null
                                  ? ""
                                      : "@",
                              this.lastNode == null
                                  ? null
                                      : this.lastNode.id,
                              chainToString() ) ;

        }   // end toString()


    /**
     * for debugging - display the contents of the LinkedList as a chain
     * <p>
     * requires enhanced Node to be useful
     *
     * @return a text representation of the chain of {@code Nodes}
     */
    private String chainToString()
        {
        final StringBuilder result = new StringBuilder() ;

        Node<T> currentNode = this.firstNode ;
        int currentPosition = 0 ;

        if ( currentNode != null )
            {
            result.append( String.format( "[%,d] ", currentPosition++ ) ) ;
            result.append( currentNode.toString() ) ;

            currentNode = currentNode.getNextNode() ;
            }

        while ( currentNode != null )
            {
            result.append( " -> " ) ;
            result.append( String.format( "[%,d] ", currentPosition++ ) ) ;
            result.append( currentNode.toString() ) ;

            currentNode = currentNode.getNextNode() ;
            }

        return result.toString() ;

        }   // end chainToString()

    /*
     * private utility methods
     */


    /**
     * Returns a reference to the node at a given position.
     * <p>
     * Precondition: The chain is not empty;
     * {@code 0 <= givenPosition < numberOfEntries}.
     *
     * @param givenPosition
     *     in the range 0..number of entries - 1
     * @return reference to the {@code Node} at the specified position in the
     *     {@code List}
     */
    private Node<T> getNodeAt( final int givenPosition )
        {
        // position to first node
        Node<T> currentNode = this.firstNode ;

        if ( givenPosition == ( this.numberOfEntries - 1 ) )
            {
            // last node is requested
            currentNode = this.lastNode ;
            }
        else if ( givenPosition > 0 )
            {
            // desired node isn't at either end
            // traverse the chain to locate the desired node
            for ( int counter = 0 ; counter < givenPosition ; counter++ )
                {
                currentNode = currentNode.getNextNode() ;
                }
            }   // end if

        return currentNode ;

        }   // end getNodeAt()


    /**
     * Initializes the class's data fields to indicate an empty list.
     */
    private void initializeDataFields()
        {
        this.firstNode = null ;
        this.lastNode = null ;

        this.numberOfEntries = 0 ;

        }   // end initializeDataFields()

    /**
     * Basic Iterator - doesn't implement {@code remove()}
     */
    private class LinkedListIterator implements Iterator<T>
        {

        /*
         * instance variable(s)
         */
        private Node<T> nextNode ;

        /**
         * Set initial position to the beginning of the (@code List}
         */
        private LinkedListIterator()
            {
            this.nextNode = LinkedList.this.firstNode ;

            }   // end constructor


        /*
         * (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext()
            {
            return this.nextNode != null ;

            }   // end hasNext()


        /*
         * (non-Javadoc)
         * @see java.util.Iterator#next()
         */
        @Override
        public T next()
            {
            if ( !hasNext() )
                {
                throw new NoSuchElementException( "Illegal call to next(); " +
                                                  "iterator is after end of list." ) ;
                }

            final T result = this.nextNode.getData() ;
            this.nextNode = this.nextNode.getNextNode() ;

            return result ;

            }   // end next()

        }   // end inner class LinkedListIterator

    /**
     * Test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // OPTIONAL for testing/debugging

        /*
         * ListInterface<String> demoList = new LinkedList<>() ; System.out.printf(
         * "empty list:%n%s%n%n", demoList ) ; demoList.add( "A" ) ;
         * System.out.printf( "1st item added:%n%s%n%n", demoList ) ; demoList.add(
         * "B" ) ; System.out.printf( "another item added at beginning:%n%s%n%n",
         * demoList ) ; demoList.add( "C" ) ; System.out.printf(
         * "another item added at end:%n%s%n%n", demoList ) ; demoList.add( 2, "D" )
         * ; System.out.printf(
         * "another item added somewhere in the middle:%n%s%n%n", demoList ) ;
         * demoList.add( "11" ) ; System.out.printf(
         * "another item added somewhere in the middle:%n%s%n%n", demoList ) ;
         * demoList.add( "-11" ) ; System.out.printf(
         * "another item added somewhere in the middle:%n%s%n%n", demoList ) ;
         * demoList.remove( 0 ) ; System.out.printf(
         * "remove the first item:%n%s%n%n", demoList ) ; demoList.remove(
         * demoList.getLength() - 1 ) ; System.out.printf(
         * "remove the last item:%n%s%n%n", demoList ) ; demoList.add( "F" ) ;
         * demoList.add( "E" ) ; System.out.printf(
         * "more items added at end:%n%s%n%n", demoList ) ; demoList.remove( 1 ) ;
         * System.out.printf( "remove a middle item:%n%s%n%n", demoList ) ;
         * demoList.sort() ; System.out.printf( "sorted:%n%s%n%n", demoList ) ;
         * demoList.shuffle() ; System.out.printf( "shuffled:%n%s%n%n", demoList ) ;
         * demoList.sort() ; System.out.printf( "sorted:%n%s%n%n", demoList ) ;
         */

        LinkedList<String> demoList = new LinkedList<>() ;
        System.out.printf( "empty list:%n%s%n%n", demoList ) ;

        demoList.appendNode( new Node<>( "A" ) ) ;
        System.out.printf( "1st item appended:%n%s%n%n", demoList ) ;

        demoList.appendNode( new Node<>( "B" ) ) ;
        System.out.printf( "2nd item appended:%n%s%n%n", demoList ) ;

        demoList.appendNode( new Node<>( "C" ) ) ;
        System.out.printf( "3rd item appended:%n%s%n%n", demoList ) ;

        demoList.appendNode( new Node<>( "D" ) ) ;
        System.out.printf( "4th item appended:%n%s%n%n", demoList ) ;

        demoList.appendNode( new Node<>( "E" ) ) ;
        System.out.printf( "5th item appended:%n%s%n%n", demoList ) ;

        demoList = new LinkedList<>() ;
        System.out.printf( "%nempty list:%n%s%n%n", demoList ) ;

        demoList.interleave( new Node<>( "A", new Node<>( "B" ) ),
                             new Node<>( "C", new Node<>( "D" ) ) ) ;
        System.out.printf( "after interleave( [A, B], [C, D] ):%n%s%n%n",
                           demoList ) ;

        demoList = new LinkedList<>() ;
        System.out.printf( "%nempty list:%n%s%n%n", demoList ) ;

        demoList.interleave( new Node<>( "A", new Node<>( "B" ) ),
                             new Node<>( "C",
                                         new Node<>( "D", new Node<>( "E" ) ) ) ) ;
        System.out.printf( "after interleave( [A, B], [C, D, E] ):%n%s%n%n",
                           demoList ) ;

        demoList = new LinkedList<>() ;
        System.out.printf( "%nempty list:%n%s%n%n", demoList ) ;

        demoList.add( "A" ) ;
        demoList.add( "B" ) ;
        demoList.add( "C" ) ;
        demoList.add( "D" ) ;
        System.out.printf( "populated: A -> B -> C -> D%n%s%n%n", demoList ) ;

        demoList.shuffle() ;
        System.out.printf( "shuffled:%n%s%n%n", demoList ) ;

        demoList.sort() ;
        System.out.printf( "sorted:%n%s%n%n", demoList ) ;

        }   // end main()

    }   // end class LinkedList
