# SyncTestGui

This is a re-imagining of a program I wrote long ago called Synchronize, and followed by SynchQt. Synchronize was written in about 2001 and reached version 3 in 2002. I did find a reference to a version of Synchronize from 1999. 

SynchQt was a re-write of Synchronize done in C++ and the Qt Framework. SynchQt was put onto Source Forge in 2011 and updated as recently as 2015.

https://sourceforge.net/projects/synchqt/

The re-imagining of SynchQt in Java will simplify the program quite a lot. SynchQt had several features which are no longer necessary or overly complicated:

SynchQt was multi-threaded, allowing multiple pairs to be open at the same time and multiple file searches and copy operations to be going on at the same time. 
The local network is much faster, as are the computers and hard drives of 20 years ago. 

Multi-threaded operation is a complexity for the user as well as for the code. The Java version is planned to allow only one pair open at a time and the file operations will be done in the context of the GUI thread.

The java version will only present one window with the Path Pairs on the left and an active pair on the right. 

The SynchQt has options for finding duplicates and deleting files from path 1. These features have not been used in years and will not be carried forward. The "protect files" will also be removed as it has not been used in years.

At this point the new GUI design is complete and this test program will explore how to manipulate the Table and Tree Table elements. Also the preferences file storage used in the ScreenShot program will not be used and an XML storage will be developed for pair storage.

Onward.
