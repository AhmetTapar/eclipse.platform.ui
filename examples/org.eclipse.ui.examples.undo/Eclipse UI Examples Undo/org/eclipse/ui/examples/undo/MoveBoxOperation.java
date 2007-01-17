/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.undo;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

/**
 * An operation that adds a box.
 */
public class MoveBoxOperation extends BoxOperation {

	/*
	 * The point the box should move to/from.
	 */
	private Point origin;
	private Point target;
	
	public MoveBoxOperation(String label, IUndoContext context, Box box, Canvas canvas, Point newOrigin) {
		super(label, context, null, box, canvas);
		origin = new Point(box.x1, box.y1);
		target = new Point(newOrigin.x, newOrigin.y);
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.IUndoableOperation#execute(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		if (box==null) {
			throw new ExecutionException("box ix null");
		}
		box.move(target);
		canvas.redraw();
		return Status.OK_STATUS;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.IUndoableOperation#redo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	public IStatus redo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return execute(monitor, info);
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.IUndoableOperation#undo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	public IStatus undo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		if (box==null) {
			throw new ExecutionException("box ix null");
		}
		box.move(origin);
		canvas.redraw();
		return Status.OK_STATUS;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.IUndoableOperation#getLabel()
	 */
	public String getLabel() {
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(super.getLabel());
		stringBuffer.append("["); //$NON-NLS-1$
		stringBuffer.append("("); //$NON-NLS-1$
		stringBuffer.append(new Integer(origin.x).toString());
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(new Integer(origin.y).toString());
		stringBuffer.append(')');
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append("("); //$NON-NLS-1$
		stringBuffer.append(new Integer(target.x).toString());
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(new Integer(target.y).toString());
		stringBuffer.append(')');
		stringBuffer.append(']');
		return stringBuffer.toString();
	}

}
