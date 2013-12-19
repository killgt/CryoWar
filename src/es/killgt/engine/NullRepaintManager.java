/*******************************************************************************
 * Copyright (c) 2009 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Agustin Rodríguez killgt@gmail.com
 ******************************************************************************/
package es.killgt.engine;

import javax.swing.RepaintManager;
import javax.swing.JComponent;

/**
 * Esta clase evita que los elementos de swing se redibujen por su cuenta.
 * Extraida del libro Developing Games in Java.chm
 */

public class NullRepaintManager extends RepaintManager {

	public static void install() {
		RepaintManager repaintManager = new NullRepaintManager();
		repaintManager.setDoubleBufferingEnabled(false);
		RepaintManager.setCurrentManager(repaintManager);
	}

	public void addInvalidComponent(JComponent c) {
	}

	public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
	}

	public void markCompletelyDirty(JComponent c) {
	}

	public void paintDirtyRegions() {
	}

}
