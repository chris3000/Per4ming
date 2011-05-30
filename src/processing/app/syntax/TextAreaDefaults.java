/*
 * TextAreaDefaults.java - Encapsulates default values for various settings
 * Copyright (C) 1999 Slava Pestov
 *
 * You may use and modify this package for any purpose. Redistribution is
 * permitted, in both source and binary form, provided that this notice
 * remains intact in all source distributions of this package.
 */

package processing.app.syntax;

import java.awt.*;

/**
 * Encapsulates default settings for a text area. This can be passed
 * to the constructor once the necessary fields have been filled out.
 * The advantage of doing this over calling lots of set() methods after
 * creating the text area is that this method is faster.
 */
public class TextAreaDefaults {
  public InputHandler inputHandler = new DefaultInputHandler();
  public SyntaxDocument document = new SyntaxDocument();;
  public boolean editable = true;

  public boolean caretVisible = true;
  public boolean caretBlinks = true;
  public boolean blockCaret = false;
  public int electricScroll = 3;

  public int cols = 80;
  public int rows=25;
  public SyntaxStyle[] styles = SyntaxUtilities.getDefaultSyntaxStyles();
  public Color caretColor = Color.red;
  public Color selectionColor= new Color(0xccccff);
  public Color lineHighlightColor = new Color(0xe0e0e0);;
  public boolean lineHighlight = true;
  public Color bracketHighlightColor= Color.black;
  public boolean bracketHighlight = true;
  public Color eolMarkerColor= new Color(0x009999);;
  public boolean eolMarkers = true;
  public boolean paintInvalid = true;

  // moved from TextAreaPainter [fry]
  public Font font=new Font("Monaco", Font.PLAIN, 12);
  public Color fgcolor=Color.BLACK;
  public Color bgcolor = Color.WHITE;
  public boolean antialias = true;


  /*
  private static final TextAreaDefaults DEFAULTS = new TextAreaDefaults();
  static {
    DEFAULTS.inputHandler = new DefaultInputHandler();
    DEFAULTS.inputHandler.addDefaultKeyBindings();
    DEFAULTS.document = new SyntaxDocument();
    DEFAULTS.editable = true;

    DEFAULTS.caretVisible = true;
    DEFAULTS.caretBlinks = true;
    DEFAULTS.electricScroll = 3;

    DEFAULTS.cols = 80;
    DEFAULTS.rows = 25;
    DEFAULTS.styles = SyntaxUtilities.getDefaultSyntaxStyles();
    DEFAULTS.caretColor = Color.red;
    DEFAULTS.selectionColor = new Color(0xccccff);
    DEFAULTS.lineHighlightColor = new Color(0xe0e0e0);
    DEFAULTS.lineHighlight = true;
    DEFAULTS.bracketHighlightColor = Color.black;
    DEFAULTS.bracketHighlight = true;
    DEFAULTS.eolMarkerColor = new Color(0x009999);
    DEFAULTS.eolMarkers = true;
    DEFAULTS.paintInvalid = true;
  }
  
  /// Returns a new TextAreaDefaults object with the default values filled in.
  public static TextAreaDefaults getDefaults()
  {
    return DEFAULTS;
  }
  */
}
