package TuitionManager;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit Test Class for the add() and remove() method in the roster.
 * Tests done on an international student studying abroad
 * and an international student not studying abroad.
 */
public class RosterTest {

    /**
     * Test to check if adding a Tri-State student that is already in the roster returns false.
     */
    @Test
    public void add_TriState_isInRoster() {
        Roster roster = new Roster();
        Profile profile = new Profile("Diaz","Mia","06/30/2002");
        TriState student = new TriState(profile, "MATH", 90, "NY");
        roster.add(student);
        assertFalse(roster.add(student));
    }

    /**
     * Test to check if removing a Tri-State student that is in the roster returns true.
     */
    @Test
    public void remove_TriState_isInRoster() {
        Roster roster = new Roster();
        Profile profile = new Profile("Diaz","Mia","06/30/2002");
        TriState student = new TriState(profile, "MATH", 90, "NY");
        roster.add(student);
        assertTrue(roster.remove(student));
    }

    /**
     * Test to check if adding an International student that is not in the roster returns true.
     */
    @Test
    public void add_International_isNotInRoster() {
        Roster roster = new Roster();
        Profile profile = new Profile("Diaz","Mia","06/30/2002");
        International student = new International(profile, "MATH", 90, true);
        assertTrue(roster.add(student));
    }

    /**
     * Test to check if removing an international student that is not in the roster returns false.
     */
    @Test
    public void remove_International_isNotInRoster() {
        Roster roster = new Roster();
        Profile profile = new Profile("Diaz","Mia","06/30/2002");
        International student = new International(profile, "MATH", 90, false);
        assertFalse(roster.remove(student));
    }
}