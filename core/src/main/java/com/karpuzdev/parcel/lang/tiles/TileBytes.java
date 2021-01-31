package com.karpuzdev.parcel.lang.tiles;

public final class TileBytes {

    /**
     * Every tile starts with 4 bytes. First 3 bytes are KMN characters.
     * The last byte is a version identifier.
     */
    public static final int TILE_HEADER = ('K' << 16) | ('M' << 8) | ('N');

    /**
     * Tile version identifier
     * Versions may have different identifier-sets so this is
     * here for backwards-compatiblity
     *
     * Current version: 0 (Building)
     */
    public static final byte TILE_VERSION = 0x00;

    /**
     * Variable-length parameters have to end with a null terminator. When
     * a variable-length block starts, nothing will be counted towards execution until
     * the null terminator is encountered.
     *
     * Variable-length parameter types are String and Number
     */
    public static final byte NULL_TERMINATOR = 0x00;

    /**
     * If a parameter can have multiple types as a parameter, these identifiers
     * will be used to determine which one is given.
     */
    public static final byte STRING_IDENTIFIER = 0x01;
    public static final byte NUMBER_IDENTIFIER = 0x02;
    public static final byte DECIMAL_IDENTIFIER = 0x03;
    public static final byte PROPERTY_IDENTIFIER = 0x04;

    /**
     * This property will give the current channel name.
     * "Current" might refer to different things depending on the event.
     * E.g. The command event will take the channel that the message is sent to as current.
     */
    public static final short CHANNELNAME_PROPERTY = 0x0001;

    /**
     * Current channel id property.
     */
    public static final short CHANNELID_PROPERTY = 0x0002;

    /**
     * Argument property for the command event.
     * This property has to be followed by a variable-length number to
     * specify which argument is needed.
     */
    public static final short ARGUMENT_PROPERTY = 0x0003;



    /**
     * "Command" denotes the fact that a message is sent by the intention
     * of being a command. E.g. bot messages do not count as a command.
     *
     * @type Event
     * @parameter String - Command identifier text itself. (e.g. "!ping")
     */
    public static final short COMMAND_EVENT = 0x0001;

    /**
     * Takes 2 parameters and executes the given block if the parameters are equal.
     * If a parameter is a property, then it will be converted to its value before checking.
     *
     * @type Condition
     * @parameter Any
     * @parameter Any
     */
    public static final short EQUALS_CONDITION = 0x0010;

    /**
     * Checks the "ELSE FLAG" and executes the given block if it is set.
     * Every condition expression will set the "ELSE FLAG" if their condition did not pass and
     * will jump to the end of the given block.
     * Every expression will clear the "ELSE FLAG".
     *
     * @type Condition
     */
    public static final short ELSE_CONDITION = 0x0020;

    /**
     * Checks the counter and jumps to the given byte if not zero. Also
     * decrements the counter.
     *
     * @type Condition
     */
    public static final short JUMPCOUNTER_CONDITION = 0x0030;

    /**
     * Sends a text message to a channel.
     *
     * @type Action
     * @parameter String - Message text
     * @parameter Number(id) - Channel identifier
     */
    public static final short SENDTEXT_ACTION = 0x0100;

    /**
     * Responds to a text message. This action is actually a short-circuit for
     * the SendText action. The channel parameter will be given as channel.id automatically.
     *
     * Any SendText that takes channel.id property might be optimized to RespondText
     *
     * @type Action
     * @parameter String - Message text
     */
    public static final short RESPONDTEXT_ACTION = 0x0200;

    /**
     * This action stops the current code execution.
     * Return is always added at the end of events and functions to end the block.
     */
    public static final short RETURN_ACTION = 0x0300;

    /**
     * Sets the counter to the given number
     *
     * @type Action
     * @parameter Number - Counter
     */
    public static final short SETCOUNTER_ACTION = 0x0400;

}