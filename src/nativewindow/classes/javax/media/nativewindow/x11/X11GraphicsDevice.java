/*
 * Copyright (c) 2008 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 */

package javax.media.nativewindow.x11;

import com.jogamp.nativewindow.impl.x11.X11Util;
import javax.media.nativewindow.*;

/** Encapsulates a graphics device on X11 platforms.
 */

public class X11GraphicsDevice extends DefaultGraphicsDevice implements Cloneable {
    boolean closeDisplay = false;

    /** Constructs a new X11GraphicsDevice corresponding to the given connection and default
     *  {@link javax.media.nativewindow.ToolkitLock} via {@link NativeWindowFactory#createDefaultToolkitLock(java.lang.String, long)}.<br>
     *  Note that this is not an open connection, ie no native display handle exist.
     *  This constructor exist to setup a default device connection.
     */
    public X11GraphicsDevice(String connection, int unitID) {
        super(NativeWindowFactory.TYPE_X11, connection, unitID);
    }

    /** Constructs a new X11GraphicsDevice corresponding to the given native display handle and default
     *  {@link javax.media.nativewindow.ToolkitLock} via {@link NativeWindowFactory#createDefaultToolkitLock(java.lang.String, long)}.
     */
    public X11GraphicsDevice(long display, int unitID) {
        // FIXME: derive unitID from connection could be buggy, one DISPLAY for all screens for example..
        super(NativeWindowFactory.TYPE_X11, X11Util.XDisplayString(display), unitID, display);
        if(0==display) {
            throw new NativeWindowException("null display");
        }
    }

    /**
     * @param display the Display connection
     * @param locker custom {@link javax.media.nativewindow.ToolkitLock}, eg to force null locking in NEWT
     */
    public X11GraphicsDevice(long display, int unitID, ToolkitLock locker) {
        super(NativeWindowFactory.TYPE_X11, X11Util.XDisplayString(display), unitID, display, locker);
        if(0==display) {
            throw new NativeWindowException("null display");
        }
    }

    public Object clone() {
      return super.clone();
    }

    public void setCloseDisplay(boolean close) {
        closeDisplay = close;
    }
    public boolean close() {
        // FIXME: shall we respect the unitID ?
        if(closeDisplay && 0 != handle) {
            X11Util.closeDisplay(handle);
            handle = 0;
            return true;
        }
        return true;
    }
}

