/*******************************************************************************
 *   MTImageButton.java
 * 
 * ® Sébastien Parodi (capturevision), 2015.
 *   http://capturevision.wordpress.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/


package org.mt4j.components.visibleComponents.widgets.buttons;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.mt4j.components.bounds.BoundsZPlaneRectangle;
import org.mt4j.components.bounds.IBoundingShape;
import org.mt4j.components.interfaces.IclickableButton;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.gestureAction.DefaultButtonClickAction;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.scaleProcessor.ScaleProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Class MTImageButton. Can be used as a button displaying an image.
 * A tapprocessor is registered automatically. We can check if the button was
 * clicked by adding an actionlistener to it.
 * @author Christopher Ruff
 */
public class MTImageButton extends MTRectangle implements IclickableButton {
	
	/** The selected. */
	private boolean selected;
	
	/** The registered action listeners. */
	private ArrayList<ActionListener> registeredActionListeners;
	
	/**
	 * Instantiates a new mT image button.
	 *
	 * @param texture the texture
	 * @param pApplet the applet
	 * @deprecated constructor will be deleted! Please , use the constructor with the PApplet instance as the first parameter.
	 */
	public MTImageButton(PImage texture, PApplet pApplet) {
		this(pApplet, texture);
	}
	
	/**
	 * Instantiates a new mT image button.
	 * @param pApplet the applet
	 * @param texture the texture
	 */
	public MTImageButton(PApplet pApplet, PImage texture) {
		super(pApplet, texture);
//		this.registeredActionListeners = new ArrayList<ActionListener>();
		
		this.setName("Unnamed image button");
		
		this.selected = false;
		
		this.setGestureAllowance(DragProcessor.class, false);
		this.setGestureAllowance(RotateProcessor.class, false);
		this.setGestureAllowance(ScaleProcessor.class, false);
		
		this.setEnabled(true);
		this.setBoundsBehaviour(AbstractShape.BOUNDS_ONLY_CHECK);
		
		//Make clickable
		this.setGestureAllowance(TapProcessor.class, true);
		this.registerInputProcessor(new TapProcessor(pApplet));
		this.addGestureListener(TapProcessor.class, new DefaultButtonClickAction(this));
		
		//Draw this component and its children above 
		//everything previously drawn and avoid z-fighting
		this.setDepthBufferDisabled(true);
	}
	
	
	@Override
	protected void setDefaultGestureActions() {
		//Dont register the usual drag,scale,rot processors
	}
	
	
	@Override
	protected IBoundingShape computeDefaultBounds(){
		return new BoundsZPlaneRectangle(this);
	}
	

	/**
	 * Adds the action listener.
	 * 
	 * @param listener the listener
	 */
	public synchronized void addActionListener(ActionListener listener){
		if (!registeredActionListeners.contains(listener)){
			registeredActionListeners.add(listener);
		}
	}
	
	/**
	 * Removes the action listener.
	 * 
	 * @param listener the listener
	 */
	public synchronized void removeActionListener(ActionListener listener){
		if (registeredActionListeners.contains(listener)){
			registeredActionListeners.remove(listener);
		}
	}
	
	/**
	 * Gets the action listeners.
	 * 
	 * @return the action listeners
	 */
	public synchronized ActionListener[] getActionListeners(){
		return registeredActionListeners.toArray(new ActionListener[this.registeredActionListeners.size()]);
	}
	
	/**
	 * Fire action performed.
	 */
	protected synchronized void fireActionPerformed() {
		ActionListener[] listeners = this.getActionListeners();
        for (ActionListener listener : listeners) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "action performed on tangible button"));
        }
	}
	
	/**
//	 * fires an action event with a ClickEvent Id as its ID.
//	 * 
//	 * @param ce the ce
//	 */
	public synchronized void fireActionPerformed(TapEvent ce) { //TODO REMOVE?
		ActionListener[] listeners = this.getActionListeners();
        for (ActionListener listener : listeners) {
            listener.actionPerformed(new ActionEvent(this, ce.getTapID(), "action performed on tangible button"));
        }
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
//		this.setStrokeWeight(selected ? this.getStrokeWeight() + 2 : 0);
	}


	
	
}

