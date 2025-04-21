package net.windyweather.synctestgui;

/*
    Encodes a file operation between a source and destination file path.
    The only file operations we do here in Sync are to COPY a file.
    The older programs did Delete, Find Duplicates etc. We leave those for
    the normal file explorer. Experience indicates that the other features are
    used so infrequently that it's not worth coding them.
    Woops. I forgot verify copy, and verify not copied.
 */
public class SyncFileOperation {
    public SyncFileOperation() {

    }

    /*
        Operation constants
     */
    static final String SFO_COPY = "COPY";
    static final String SFO_COPY_VERIFY = "COPY/VERIFY";
    static final String SFO_VERIFY_NOCOPY = "VERIFY ONLY";
    /*
        Status Constants
     */
    static final String SFO_PEND = "PENDING";
    static final String SFO_COPIED  = "COPIED";
    static final String SFO_COPYVERIFY = "COPIED/VERIFIED";
    static final String SFO_VERFIED = "VERIFIED";
    static final String SFO_RECOVERED = "RECOVERED";



    String sSourcePath;
    String sDestinationPath;
    int     intSize;
    String sOperation; //
    String Status;
}
