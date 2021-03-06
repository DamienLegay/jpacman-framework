package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.HallOfFame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;

/**
 * Test class fo the Hall of Fame.
 */
public class HallOfFameTest
{
    /**
     * A Hall of Fame to test methods with.
     */
    private HallOfFame hallOfFame;

    /**
     * The pth of the file containing the running Hall of Fame.
     */
    private String path;

    /**
     * To read the HoF file.
     */
    private BufferedReader reader;

    /**
     * Operations to be executed prior to tests.
     * @throws IOException If the HoF file cannot be found or read.
     */
    @Before
    public void init() throws IOException
    {
        HallOfFame.setIsNotATest(false);
        hallOfFame = new HallOfFame();
        path = hallOfFame.getHoFPath();
        reader = new BufferedReader(new FileReader(path));
    }

    /**
     * Closes the HoF file after a test.
     * @throws IOException If the HoF file cannot be closed.
     */
    @After
    public void cleanup() throws IOException
    {
        reader.close();
    }

    /**
     * Tests whether the HoF is correctly updated whenever a new high score is obtained.
     */
    @Test
    public void hallOfFameUpdateTest()
    {
        File hallOfFameFile = new File(path);
        hallOfFame.handleHoF(Integer.MAX_VALUE, "TESTPLAYER");
        //Testing whether the Hall of Fame file was modified within the last few seconds, as it should' ve been, given the score.
        assertTrue("The Hall of Fame hasn't been modified.", hallOfFameFile.lastModified() / 10000 == System.currentTimeMillis() / 10000);
    }

    /**
     * Tests whether the HoF gets reset to default values when appropriate.
     * @throws IOException If the default HoF file cannot be read or found.
     */
    @Test
    public void hallOfFameResetTest() throws IOException
    { // Note: running tests resets the Hall of Fame.
        hallOfFame.resetHoF();
        BufferedReader defaultHOFReader = new BufferedReader(new FileReader(hallOfFame.getDefaultHoFPath()));
        String current = reader.readLine(), base = defaultHOFReader.readLine();
        for (int i = 0; i < hallOfFame.getNumberOfRecordsKept(); i++)
        {
            current += reader.readLine();
            base += defaultHOFReader.readLine();
        }
        assertEquals("Hall of Fame hasn't been reset.", current, base);
    }
}
