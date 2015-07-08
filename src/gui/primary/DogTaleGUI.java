/*
 * Robert Ferguson TCSS 305 – Winter 2015 Assignment 6b - Tetris (NES Zelda themed)
 */

package gui.primary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;

import java.util.Observable;
import java.util.Observer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

// import sounds.SoundEffect;

/**
 * The primary window that holds the Tetris game.
 * 
 * @author Robert Ferguson
 * @version February 27th 2015
 */
public class DogTaleGUI implements Observer, FocusListener
{
    // constants to capture screen dimensions
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /** The width of the screen. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;

    /** The height of the screen. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;

    /** Adjusts the location of the GUI on the screen. */
    private static final int ADJUSTMENT = 2;

    /** The title on the JFrame. */
    private static final String TITLE = "Dog's Tale";

    /** Plays the background music. */
    private static Sequencer midiPlayer;

    /** The JFrame everything is contained in. */
    private final JFrame myPrimaryFrame;

    /** The object that times the game. */
    private final Timer myGameTimer;

    /** Listens for Key presses. */
    private final KeyController myKeyListener;

    /** How many lines have been cleared on the current game. */
    private int myLinesCleared;

    /** Whether or not a sound effect should play on the first piece. */
    private boolean myFirstPiece;

    /** Whether or not the game is running. */
    private boolean myGameState;

    /** The listener for the timer. Triggers step() on the board. */
    private final GameTimerListener myTimerListener;

    /**
     * Creates the GUI for Dog's Tale
     */
    public DogTaleGUI()
    {
        myPrimaryFrame = new JFrame(TITLE);
        myPrimaryFrame.setBackground(Color.BLACK);

        myGameTimer = new Timer(0, null);
        myTimerListener = new GameTimerListener();

        myKeyListener = new KeyController();
    }

    /**
     * Starts all the elements of the GUI.
     */
    public void start()
    {
        // When the gui window is closed, the program should exit.
        myPrimaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // myPrimaryFrame.setPreferredSize(PREF_SIZE);

        // final ImageIcon icon = new ImageIcon("./images/triforce.png");

        // Change the icon
        // myPrimaryFrame.setIconImage(icon.getImage());

        // resize
        myPrimaryFrame.pack();

        // position the frame in the center of the screen
        myPrimaryFrame.setLocation(SCREEN_WIDTH / ADJUSTMENT - myPrimaryFrame.getWidth()
                / ADJUSTMENT, SCREEN_HEIGHT / ADJUSTMENT - myPrimaryFrame.getHeight()
                / ADJUSTMENT);

        // Make the frame visible and stop it from being resizable.
        myPrimaryFrame.setVisible(true);
        myPrimaryFrame.setResizable(false);

        // Turn sound effects on
        // SoundEffect.allowSounds(true);

    }

    @Override
    public void update(final Observable theObservable, final Object theArg)
    {

    }

    @Override
    public void focusGained(final FocusEvent theArg)
    {

    }

    @Override
    public void focusLost(final FocusEvent theArg)
    {

    }

    private final class GameTimerListener implements ActionListener
    {
        protected GameTimerListener()
        {
            super();
        }

        /**
         * The action performed whenever the timer ticks.
         * 
         * @param theEvent The event triggering the action.
         */
        public void actionPerformed(final ActionEvent theEvent)
        {

        }

    }

    private final class KeyController extends KeyAdapter
    {
        protected KeyController()
        {
            super();
        }

        @Override
        public void keyPressed(final KeyEvent theEvent)
        {

        }
    }
}
