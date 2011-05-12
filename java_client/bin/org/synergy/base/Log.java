/*
 * synergy -- mouse and keyboard sharing utility
 * Copyright (C) 2010 Shaun Patterson
 * Copyright (C) 2010 The Synergy Project
 * Copyright (C) 2009 The Synergy+ Project
 * Copyright (C) 2002 Chris Schoeneman
 * 
 * This package is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * found in the file COPYING that should have accompanied this file.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.synergy.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Logging class
 * 
 * Example usage:  
 * Log.fatal ("fatal error");
 * Log.debug1 ("debug1 printout")
 * 
 * Log.setLogLevel (Log.Level.FATAL)
 * 
 * @author Shaun Patterson
 *
 */
public class Log {
    public enum Level {
        PRINT,
        FATAL,
        ERROR,
        WARNING,
        NOTE,
        INFO,
        DEBUG,
        DEBUG1,
        DEBUG2,
        DEBUG3,
        DEBUG4,
        DEBUG5;
    }

    // Current level of logging
    private static Level logLevel = Level.DEBUG;

    private ArrayList <LogOutputterInterface> outputters;
    private ArrayList <LogOutputterInterface> alwaysOutputters;
    
    private int maxNewlineLength;
    private int maxPriority;

    private static Log log;
    private static Object sync = new Object ();
    private static Log getInstance () {
        synchronized (sync) {
            if (log == null) {
                log = new Log ();
            }
            return log;
        }
    }
    private Log () {
    	this.outputters = new ArrayList <LogOutputterInterface> ();
    	this.alwaysOutputters = new ArrayList <LogOutputterInterface> ();
    	
        this.insert (new ConsoleLogOutputter (), false);
    }

    private void insert (LogOutputterInterface outputter, boolean alwayAtHead) {
    	if (alwayAtHead) {
            alwaysOutputters.add (0, outputter);
        } else {
        	outputters.add (0, outputter);
        }
    }

    private void remove (LogOutputterInterface outputter) {
        outputters.remove (outputter);
    }
     

    public void popFront (boolean alwaysAtHead) {
        ArrayList <LogOutputterInterface> list = alwaysAtHead ? alwaysOutputters : outputters;
        if (list.isEmpty() == false) {
        	list.remove (0);
        }
    }

    public static void setLogLevel (final Level level) {
        logLevel = level;
    }
    
    public static Level getLogLevel () {
        return logLevel;
    }


    private void print (final Level level, final String message) {
        if (level.compareTo (getLogLevel ()) > 0) {
            // Done
            return;
        }

        // Do not prefix time and file for PRINT
        if (level.compareTo (Level.PRINT) != 0) {
            Date now = new Date ();
            SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

            StringBuffer formattedMessage = new StringBuffer (dateFormatter.format (now));
            formattedMessage.append (" ");
            formattedMessage.append (level);
            formattedMessage.append (": ");
            formattedMessage.append (message); 
            formattedMessage.append ("\n\t");
            
            // Get the calling method's class and the line number
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            
            try {
            	formattedMessage.append (stackTraceElements [3].getClassName());
            	formattedMessage.append (":");
            	formattedMessage.append (stackTraceElements [3].getLineNumber());
            } catch (Exception e) {
            	formattedMessage.append ("Class and line info not found");
            }
            
            output (level, formattedMessage.toString ());
        } else {
            output (level, message);
        }
    }

    private void output (final Level level, final String message) {
        for (LogOutputterInterface outputter : alwaysOutputters) {
            outputter.write (level, message);
        }

        for (LogOutputterInterface outputter : outputters) {
            outputter.write (level, message);
        }
    }
    public static void print (String message) {
        getInstance ().print (Level.PRINT, message);
    }

    public static void fatal (String message) {
        getInstance ().print (Level.FATAL, message);
    }

    public static void error (String message) {
        getInstance ().print (Level.ERROR, message);
    }

    public static void info (String message) {
    	getInstance().print(Level.INFO, message);
    }

    public static void note (String message) {
    	getInstance().print(Level.NOTE, message);
    }
    
    public static void debug (String message) {
    	getInstance().print(Level.DEBUG, message);
    }
    
    public static void debug1 (String message) {
    	getInstance ().print(Level.DEBUG1, message);
    }
    
    public static void debug2 (String message) {
    	getInstance ().print(Level.DEBUG2, message);
    }
        
    public static void debug3 (String message) {
    	getInstance ().print(Level.DEBUG3, message);
    }
    
    public static void debug4 (String message) {
    	getInstance ().print(Level.DEBUG4, message);
    }

    public static void debug5 (String message) {
    	getInstance ().print(Level.DEBUG5, message);
    }
}
