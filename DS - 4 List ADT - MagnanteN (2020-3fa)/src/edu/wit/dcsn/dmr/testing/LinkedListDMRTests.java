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

import static org.junit.jupiter.api.Assertions.* ;

import org.junit.jupiter.api.Disabled ;
import org.junit.jupiter.api.DisplayName ;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation ;
import org.junit.jupiter.api.Order ;
import org.junit.jupiter.api.TestInfo ;
import org.junit.jupiter.api.TestInstance ;
import org.junit.jupiter.api.TestInstance.Lifecycle ;
import org.junit.jupiter.api.TestMethodOrder ;
import org.junit.jupiter.params.ParameterizedTest ;
import org.junit.jupiter.params.provider.CsvFileSource ;

import java.lang.reflect.Array ;
import java.util.Arrays ;
import java.util.Collections ;

import edu.wit.dcsn.comp2000.common.ListInterface ;
import edu.wit.dcsn.comp2000.common.Node ;
import edu.wit.dcsn.comp2000.list.adt.LinkedList ;
import edu.wit.dcsn.dmr.testing.junit.JUnitTestingBase ;
import static edu.wit.dcsn.dmr.testing.junit.Reflection.* ;
import static edu.wit.dcsn.dmr.testing.junit.TestData.* ;

/**
 * JUnit tests for the LinkedList class. All public and package visible 
 * methods are tested. These tests require the API for the LinkedList class 
 * implement {@code ListInterface<T>}.
 *
 * @author David M Rosenberg
 * @version 1.0.0 2020-07-17 initial set of tests - based on LinkedBagDMRTests
 * @version 1.1.0 2020-11-09 miscellaneous updates, enhancements, and cleanup
 */
@DisplayName( "LinkedList" )
@TestInstance( Lifecycle.PER_CLASS )
@TestMethodOrder( OrderAnnotation.class )
class LinkedListDMRTests extends JUnitTestingBase
    {

    /**
     * Test method for
     * {@link edu.wit.dcsn.comp2000.list.adt.LinkedList#LinkedList(java.lang.Comparable[])}.
     *
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param arrayContentsArgument
     *     contents for initialContents array
     * @param testInfo
     *     info about the test
     */
    @ParameterizedTest( name = "{displayName}:: [{index}] {arguments}" )
    @CsvFileSource( resources = "./test-data-dmr/test-array-constructor.data",
                    numLinesToSkip = 1 )
    @DisplayName( "array constructor" )
    @Order( 100200 )
    @Disabled
    void testArrayConstructor( final boolean isLastTest,
                               final boolean isStubBehavior,
                               final String arrayContentsArgument,
                               final TestInfo testInfo )
        {
        final Object[][] arrayContents = startTest( testInfo,
                                                    isLastTest,
                                                    isStubBehavior,
                                                    new String[] { "initialContents" },
                                                    arrayContentsArgument ) ;

        final Comparable<?>[] comparableContents = 
                arrayContentsArgument == null
                    ? null  // initialContents array is null
                    : // instantiate and populate comparableContents array
                        (Comparable[]) copyObjectArrayToSpecificArray( arrayContents[ 0 ] ) ;

        final boolean arrayContentsContainsNull =
                                        arrayContains( comparableContents, null ) ;

        // display message describing the expected result of this test
        writeLog( "\texpect: %s%n",
                  arrayContents[ 0 ] == null
                      ? "[]"
                      : arrayContentsContainsNull
                          ? IllegalArgumentException.class.getSimpleName() +
                              ": \"entry cannot be null\""
                          : arrayToString( arrayContents[ 0 ] ) ) ;
        

        // execute the test
        assertTimeoutPreemptively( testTimeLimit, () ->
            {
            // instantiate testList
            if ( arrayContentsContainsNull )
                {
                try
                    {
                    // instantiating the LinkedList is expected to throw an IllegalArgumentException
                    @SuppressWarnings( { "unchecked", "rawtypes", "unused" } )
                    final ListInterface<?> testList =
                                        new LinkedList( comparableContents ) ;

                    // failed to throw any exception
                    
                    // display message describing the actual result of this test
                    writeLog( "\tactual: no Exception thrown%n" ) ;
                    
                    fail( "IllegalArgumentException not thrown" ) ;
                    }
                catch ( Throwable e )
                    {
                    // display message describing the actual result of this test
                    writeLog( "\tactual: %s%s%n",
                              e.getClass().getSimpleName(),
                              e.getMessage() == null
                                  ? ""
                                  : ": \"" + e.getMessage() + "\"" ) ;
                    
                    // instantiating the LinkedList is expected to throw an IllegalArgumentException
                    assertEquals( IllegalArgumentException.class,
                                  e.getClass() ) ;
                    }
                
                }
            else
                {
                // instantiate testList
                try
                    {
                    @SuppressWarnings( { "rawtypes", "unchecked" } )
                    final ListInterface<?> testList =
                                        new LinkedList( comparableContents ) ;


                    verifyListState( testList, 
                                     arrayContents[ 0 ] == null
                                         ? 0
                                         : arrayContents[ 0 ].length ) ;
        
        
                    // correct entries?
                    final Object[] testListContents = getContentsOfChainBackedCollection( testList ) ;
        
                    // display message describing the actual result of this test
                    writeLog( "\tactual: %s%n", arrayToString( testListContents ) ) ;
        
                    // verify the test list's contents
                    compareArrays( arrayContents[ 0 ] == null
                                        ? new Object[ 0 ]
                                        : arrayContents[ 0 ], 
                                   testListContents, IS_UNORDERED ) ;
        
                    // verify initialContents array's contents
                    // - must be a non-destructive operation
        
                    // correct entries?
                    compareArrays( arrayContents[ 0 ], comparableContents, IS_ORDERED ) ;
                    }
                catch ( Throwable e )
                    {
                    writeLog( "\tactual: %s%s%n",
                              e.getClass().getSimpleName(),
                              e.getMessage() == null
                                  ? ""
                                  : ": " + e.getMessage() ) ;
                    
                    throw e ;   // re-throw it
                    }
                }

            this.currentTestPassed = true ;
            } ) ;

        }   // end testArrayConstructor()


    /**
     * Test method for {@link edu.wit.dcsn.comp2000.list.adt.LinkedList#clear()}.
     * 
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param listContentsArgument
     *     contents to add to the list
     * @param testInfo
     *     info about the test
     */
    @ParameterizedTest( name = "{displayName}:: [{index}] {arguments}" )
    @CsvFileSource( resources = "./test-data-dmr/test-clear.data", numLinesToSkip = 1 )
    @DisplayName( "clear()" )
    @Order( 200100 )
    @Disabled
    void testClear( final boolean isLastTest,
                    final boolean isStubBehavior,
                    final String listContentsArgument,
                    final TestInfo testInfo )
        {
        final Object[][] listContents = startTest( testInfo,
                                                  isLastTest,
                                                  isStubBehavior,
                                                  new String[] { "initial contents" },
                                                  listContentsArgument ) ;

        // execute the test
        assertTimeoutPreemptively( testTimeLimit, () ->
            {
            // display message describing the expected result of this test
            writeLog( "\texpect: %s%n", "[]" ) ;

            // instantiate and populate testList
            final Comparable<?>[] comparableContents =
                (Comparable[]) copyObjectArrayToSpecificArray( listContents[ 0 ] ) ;

            final ListInterface<?> testList = populateList( comparableContents ) ;

            // correct number of entries?
            assertEquals( comparableContents == null
                            ? 0
                            : comparableContents.length, 
                          testList.getLength() ) ;

            // clear it
            try
                {
                testList.clear() ;
                }
            catch ( Exception e )
                {
                writeLog( "\tactual: %s%s%n",
                          e.getClass().getSimpleName(),
                          e.getMessage() == null
                              ? ""
                              : ": \"" + e.getMessage() + "\"" ) ;
                
                throw e ;   // re-throw it
                }

            // empty?
            assertTrue( testList.isEmpty() ) ;

            // make sure it is
            assertEquals( 0, testList.getLength() ) ;

            // display message describing the actual result of this test
            writeLog( "\tactual: %s%n", "[]" ) ;

            this.currentTestPassed = true ;
            } ) ;

        }   // end testClear()


    /**
     * Test method for {@link edu.wit.dcsn.comp2000.list.adt.LinkedList#shuffle()}.
     * 
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param listContentsArgument
     *     contents to add to the list
     * @param firstPassContentsArgument 
     *     contents of the list after the first invocation of {@code shuffle()}
     * @param secondPassContentsArgument 
     *     contents of the list after the second invocation of {@code shuffle()}
     * @param thirdPassContentsArgument 
     *     contents of the list after the third invocation of {@code shuffle()}
     * @param fourthPassContentsArgument 
     *     contents of the list after the fourth invocation of {@code shuffle()}
     * @param testInfo
     *     info about the test
     */
    @ParameterizedTest( name = "{displayName}:: [{index}] {arguments}" )
    @CsvFileSource( resources = "./test-data-dmr/test-shuffle.data",
                    numLinesToSkip = 1 )
    @DisplayName( "shuffle()" )
    @Order( 500300 )
    void testShuffle( final boolean isLastTest,
                      final boolean isStubBehavior,
                      final String listContentsArgument,
                      final String firstPassContentsArgument,
                      final String secondPassContentsArgument,
                      final String thirdPassContentsArgument,
                      final String fourthPassContentsArgument,
                      final TestInfo testInfo )
        {
        final Object[][] listContents = startTest( testInfo,
                                                  isLastTest,
                                                  isStubBehavior,
                                                  new String[] { "initial contents",
                                                                 "after first pass",
                                                                 "after second pass",
                                                                 "after third pass",
                                                                 "after fourth pass" },
                                                  listContentsArgument,
                                                  firstPassContentsArgument,
                                                  secondPassContentsArgument,
                                                  thirdPassContentsArgument,
                                                  fourthPassContentsArgument ) ;

        // instantiate and populate array with initial contents
        final Object[] initialContents =
                (Object[]) copyObjectArrayToSpecificArray( listContents[ 0 ] ) ;
        
        // execute the test
        assertTimeoutPreemptively( testTimeLimit, () ->
            {
            // instantiate and populate testList
            final ListInterface<?> testList = populateList( initialContents ) ;
            
            verifyListState( testList, initialContents.length ) ;

            for ( int pass = 1; pass < listContents.length; pass++ )
                {
                // i-th pass
                Object[] expectedContents =
                    (Object[]) copyObjectArrayToSpecificArray( listContents[ pass ] ) ;

                // display message describing the expected result of this test
                writeLog( "\texpect: %s%n", arrayToString( expectedContents ) ) ;

                // shuffle the contents of the test list
                Object[] actualResult ;
                try
                    {
                    testList.shuffle() ;
                    }
                catch ( Throwable e )
                    {
                    writeLog( "\tactual: %s%s%n",
                              e.getClass().getSimpleName(),
                              e.getMessage() == null
                                  ? ""
                                  : ": \"" + e.getMessage() + "\"" ) ;
                    
                    throw e ;   // re-throw it
                    }

                verifyListState( testList, expectedContents.length ) ;

                actualResult = testList.toArray() ;

                // display message describing the actual result of this test
                writeLog( "\tactual: %s%n", arrayToString( actualResult ) ) ;

                // verify the list's contents
                compareArrays( expectedContents, actualResult, IS_ORDERED ) ;
                }

            this.currentTestPassed = true ;
            
            } ) ;

        }   // end testShuffle()


    /**
     * Test method for {@link edu.wit.dcsn.comp2000.list.adt.LinkedList#sort()}.
     * 
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param listContentsArgument
     *     contents to add to the list
     * @param testInfo
     *     info about the test
     */
    @ParameterizedTest( name = "{displayName}:: [{index}] {arguments}" )
    @CsvFileSource( resources = "./test-data-dmr/test-sort.data",
                    numLinesToSkip = 1 )
    @DisplayName( "sort()" )
    @Order( 500100 )
    void testSort( final boolean isLastTest,
                      final boolean isStubBehavior,
                      final String listContentsArgument,
                      final TestInfo testInfo )
        {
        final Object[][] listContents = startTest( testInfo,
                                                  isLastTest,
                                                  isStubBehavior,
                                                  new String[] { "initial contents" },
                                                  listContentsArgument ) ;

        // instantiate and populate comparableContents array with initial contents
        final Comparable<?>[] comparableContents =
                (Comparable[]) copyObjectArrayToSpecificArray( listContents[ 0 ] ) ;
        
        // determine expected results ==> initial contents (stable) sorted
        final Comparable<?>[] expectedContents = 
                    Arrays.copyOf( comparableContents, comparableContents.length ) ;
        Arrays.sort( expectedContents ) ;

        // display message describing the expected result of this test
        writeLog( "\texpect: %s%n", arrayToString( expectedContents ) ) ;

        // execute the test
        assertTimeoutPreemptively( testTimeLimit, () ->
            {
            // instantiate and populate testList
            final ListInterface<?> testList = populateList( comparableContents ) ;

            // sort the contents of the test list
            final Comparable<?>[] actualResult ;
            try
                {
                testList.sort() ;
                }
            catch ( Throwable e )
                {
                writeLog( "\tactual: %s%s%n",
                          e.getClass().getSimpleName(),
                          e.getMessage() == null
                              ? ""
                              : ": \"" + e.getMessage() + "\"" ) ;
                
                throw e ;   // re-throw it
                }
            
            verifyListState( testList, expectedContents.length ) ;
            
            actualResult = testList.toArray() ;

            // display message describing the actual result of this test
            writeLog( "\tactual: %s%n", arrayToString( actualResult ) ) ;

            // verify the list's contents
            compareArrays( expectedContents, actualResult, IS_ORDERED ) ;

            // this operation must be repeatable
            // - do it again to make sure...

            // retrieve the contents of the test list in an array
            final Comparable<?>[] verifyResult ;
            try
                {
                testList.sort() ;
                }
            catch ( Exception e )
                {
                writeLog( "\tactual: %s%s%n",
                          e.getClass().getSimpleName(),
                          e.getMessage() == null
                              ? ""
                              : ": \"" + e.getMessage() + "\"" ) ;
                
                throw e ;   // re-throw it
                }
            
            verifyListState( testList, expectedContents.length ) ;
            
            verifyResult = testList.toArray() ;

            // display message describing the actual result of this test
            writeLog( "\tverify: %s%n", arrayToString( verifyResult ) ) ;

            // verify the list's contents
            compareArrays( expectedContents, verifyResult, IS_ORDERED ) ;

            this.currentTestPassed = true ;
            
            } ) ;

        }   // end testSort()


    /**
     * Test method for stability of
     * {@link edu.wit.dcsn.comp2000.list.adt.LinkedList#sort()}.
     * 
     * @param isLastTest
     *     flag to indicate that this is the last dataset for this test
     * @param isStubBehavior
     *     flag to indicate that the result of testing this dataset matches the
     *     stubbed behavior
     * @param orderFrontToBackArgument
     *     flag to control order to add elements to list:<br>
     *     true: add at end<br>
     *     false: add at beginning
     * @param listContentsArgument
     *     contents to add to the list
     * @param testInfo
     *     info about the test
     */
    @ParameterizedTest( name = "{displayName}:: [{index}] {arguments}" )
    @CsvFileSource( resources = "./test-data-dmr/test-sort-stability.data",
                    numLinesToSkip = 1 )
    @DisplayName( "sort() stability" )
    @Order( 500200 )
    void testSortStability( final boolean isLastTest,
                      final boolean isStubBehavior,
                      final String orderFrontToBackArgument,
                      final String listContentsArgument,
                      final TestInfo testInfo )
        {
        final Object[][] listContents = startTest( testInfo,
                                                  isLastTest,
                                                  isStubBehavior,
                                                  new String[] { "add front-to-back",
                                                                 "initial values" },
                                                  orderFrontToBackArgument,
                                                  listContentsArgument ) ;

        // instantiate and populate initialContents
        final Paired[] initialContents = new Paired[ listContents[ 1 ].length ] ;
        for ( int i = 0; i < listContents[ 1 ].length; i++ )
            {
            initialContents[ i ] = new Paired( (int) (long) listContents[ 1 ][ i ] ) ;
            }
        
        // reverse order of initialContents if necessary
        final boolean orderFrontToBack = (boolean) listContents[ 0 ][ 0 ] ;
        if ( !orderFrontToBack )
            {
            Collections.reverse( Arrays.asList( initialContents ) );
            }
        
        // determine expected results ==> initial contents (stable) sorted
        final Paired[] expectedContents = 
                    Arrays.copyOf( initialContents, initialContents.length ) ;
        Arrays.sort( expectedContents ) ;

        // display message describing the expected result of this test
        writeLog( "\texpect: %s%n", arrayToString( expectedContents ) ) ;

        // execute the test
        assertTimeoutPreemptively( testTimeLimit, () ->
            {
            // instantiate and populate testList
            final ListInterface<Paired> testList =
                                        new LinkedList<>( initialContents ) ;

            // sort the contents of the test list
            final Comparable<?>[] actualContents ;
            try
                {
                testList.sort() ;
                }
            catch ( Throwable e )
                {
                writeLog( "\tactual: %s%s%n",
                          e.getClass().getSimpleName(),
                          e.getMessage() == null
                              ? ""
                              : ": \"" + e.getMessage() + "\"" ) ;
                
                throw e ;   // re-throw it
                }
            
            verifyListState( testList, expectedContents.length ) ;
            
            actualContents = testList.toArray() ;

            // display message describing the actual result of this test
            writeLog( "\tactual: %s%n", arrayToString( actualContents ) ) ;

            // verify the list's contents
            compareArrays( expectedContents, actualContents, IS_ORDERED ) ;

            // this operation must be repeatable
            // - do it again to make sure...

            // retrieve the contents of the test list in an array
            final Comparable<?>[] verifyContents ;
            try
                {
                testList.sort() ;
                }
            catch ( Exception e )
                {
                writeLog( "\tactual: %s%s%n",
                          e.getClass().getSimpleName(),
                          e.getMessage() == null
                              ? ""
                              : ": \"" + e.getMessage() + "\"" ) ;
                
                throw e ;   // re-throw it
                }
            
            verifyListState( testList, expectedContents.length ) ;
            
            verifyContents = testList.toArray() ;

            // display message describing the actual result of this test
            writeLog( "\tverify: %s%n", arrayToString( verifyContents ) ) ;

            // verify the list's contents
            compareArrays( expectedContents, verifyContents, IS_ORDERED ) ;

            this.currentTestPassed = true ;
            
            } ) ;

        }   // end testSortStability()


    // --------------------------------------------------
    //
    // The following utilities are used by the test methods
    //
    // --------------------------------------------------

    /**
     * Utility to populate a list from the contents of an array
     * 
     * @param listToFill
     *     the list to populate
     * @param entries
     *     the entries to add to the listToFill
     */
    @SuppressWarnings( "unused" )
    private static <T extends Comparable<? super T>> void populateList( 
                                       final ListInterface<T> listToFill,
                                       final T[] entries )
        {
        if ( entries != null )
            {
            for ( final T anEntry : entries )
                {
                listToFill.add( anEntry ) ;
                }
            }

        }   // end populateList()


    /**
     * Utility to populate a list from the contents of an array
     * 
     * @param entries
     *     the entries to add to the listToFill
     * @return an instantiated and populated list containing the provided entries
     */
    private static ListInterface<?> populateList( final Object[] entries )
        {
        final ListInterface<?> list ;

        if( ( entries != null ) &&
            ( entries.length > 0 ) )
            {
            final Object firstEntry = entries[ 0 ] ;
            final String firstEntryClassName ;
            if ( firstEntry == null )
                {
                firstEntryClassName = "null" ;
                }
            else
                {
                firstEntryClassName = firstEntry.getClass().getSimpleName() ;
                }

            if ( firstEntry instanceof Comparable )
                {
                switch( firstEntryClassName )
                    {
                    case "String" :
                        list = new LinkedList<>( (String[]) entries ) ;
                        break ;
                        
                    case "Long" :
                        list = new LinkedList<>( (Long[]) entries ) ;
                        break ;
                        
                    case "Character" :
                        list = new LinkedList<>( (Character[]) entries ) ;
                        break ;
                        
                    default:
                        list = new LinkedList<>() ;
                        
                        fail( String.format( "unsupported/unexpected item/type: (%s) %s",
                                             firstEntryClassName,
                                             firstEntry ) ) ;
                    }
                }   // firstEntry isn't a Comparable
            else
                {
                list = new LinkedList<>() ;
                
                fail( String.format( "unsupported/unexpected item/type: (%s) %s",
                                     firstEntryClassName,
                                     firstEntry ) ) ;
                }
            }
        else
            {
            list = new LinkedList<>() ;
            }
        
        return list ;

        }   // end populateList()


    /**
     * Create and populate an array of a specific type/class from an {@code Object[]}
     *
     * @param objectArray
     *     the {@code Object[]} containing the elements to copy into the specific
     *     type array
     * @return an array of the type of element of the first element in
     *     {@code objectArray}; if the parameter array is empty or the first element
     *     is {@code null}, the returned array is of type {@code String[]}; if the
     *     parameter is {@code null} returns {@code null}
     */
    private static Object copyObjectArrayToSpecificArray( Object[] objectArray )
        {
        if ( objectArray == null )
            {
            return null ;
            }
        
        // instantiate an array of Object references (if no entries) or an array
        // of references of the class of the first element of objectArray
        final Object specificArray =
                                   Array.newInstance( ( objectArray.length == 0 ) || 
                                                      ( objectArray[ 0 ] == null )
                                                        ? String.class
                                                        : objectArray[ 0 ].getClass(),
                                                      objectArray.length ) ;
        
        // copy each element from objectArray to comparableArray
        System.arraycopy( objectArray, 0, specificArray, 0, objectArray.length );
        
        return specificArray ;
        
        }   // end copyObjectArrayToSpecificArray()
    
    
    /**
     * Test the provided LinkedList instance to ensure it's in a valid state:
     * <ul>
     * <li>numberOfEntries is non-negative and matches expected value
     * <li>the chain is the correct length
     * <li>none of the data references is null
     * <li>lastNode points to the correct Node
     * </ul>
     * 
     * @param testList
     *     the {@code LinkedList} instance to test
     * @param expectedNumberOfEntries
     *     the number of entries that should be in the list
     */
    private static void verifyListState( final ListInterface<?> testList,
                                         final int expectedNumberOfEntries )
        {
        assertTrue( testList instanceof LinkedList ) ;
        
        final int numberOfEntries = getIntField( testList, "numberOfEntries" ) ;
        assertEquals( expectedNumberOfEntries, numberOfEntries, "incorrect numberOfEntries" ) ;
        
        final Node<?> firstNode = (Node<?>) getReferenceField( testList, "firstNode" ) ;
        final Node<?> lastNode = (Node<?>) getReferenceField( testList, "lastNode" ) ;
        
        if ( expectedNumberOfEntries == 0 )
            {   // list is empty
            assertNull( firstNode, "firstNode should be null" ) ;
            assertNull( lastNode, "lastNode should be null" ) ;
            }
        else
            {   // list is non-empty
            assertNotNull( firstNode, "firstNode should not be null" ) ;
            assertNotNull( lastNode, "lastNode should not be null" ) ;
            
            int nodeCount = 0 ;
            Node<?> currentNode = firstNode ;
            
            // count the number of Nodes
            while ( currentNode != null )
                {
                nodeCount++ ;
                
                assertNotNull( currentNode.getData(), "data can't be null" ) ;
                
                if ( nodeCount == expectedNumberOfEntries )
                    {
                    assertTrue( currentNode == lastNode, "incorrect lastNode" ) ;
                    }
                
                assertTrue( nodeCount <= expectedNumberOfEntries, "too many Nodes" ) ;
                
                currentNode = currentNode.getNextNode() ;
                }   // end traversing the chain
            
            assertEquals( expectedNumberOfEntries, nodeCount, "incorrect number of Nodes" ) ;
            }
        
        }   // end verifyListState()
    
    }   // end class LinkedListDMRTests