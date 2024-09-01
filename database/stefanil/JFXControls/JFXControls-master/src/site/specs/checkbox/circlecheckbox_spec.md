#CircleCheckBox User Experience Documentation

##Interaction Design
Currently there are no changes concerning the interaction design. See <a href="https://wiki.openjdk.java.net/display/OpenJFX/CheckBox+User+Experience+Documentation">OpenJDK CheckBox User Experience Documentation</a> to read more about the default interaction design of a checkbox.

##Navigation
Currently there are no changes concerning the navigation. See <a href="https://wiki.openjdk.java.net/display/OpenJFX/CheckBox+User+Experience+Documentation">OpenJDK CheckBox User Experience Documentation</a> to read more about the default interaction design of a checkbox.

##Customizations
Goal: Replace the background rectangle by a circle

###Solution 0: Styling the built in Controls by using CSS
A simple adpation of the Modena CSS classes given 4 checkbox and its inner controls will do the trick.

<pre>
.check-box > .box {
	-fx-background-radius: 10px;
}
</pre>

+ simple
- unflexible (for this issue it should be enough, but there will be use cases, where this is insufficient)

###Solution 1: Exchange Skin
* add a circle Shape to box
* adapt CSS to hide background of box

<pre>
public class CircleCheckBoxSkin extends CheckBoxSkin {

	private final Circle circle = new Circle();

	public CircleCheckBoxSkin(CheckBox checkbox) {
		super(checkbox);
		// configure circle
		circle.getStyleClass().setAll("circle");
		circle.setCenterX(5f);
		circle.setCenterY(5f);
		circle.setRadius(10.0f);
		updateChildren();
	}

	@Override
	protected void updateChildren() {
		super.updateChildren();
		addCircle();
	}

	private void addCircle() {
		if (getChildren() != null && getChildren().size() > 1
				&& getChildren().get(1) != null
				&& getChildren().get(1) instanceof StackPane) {
			StackPane box = (StackPane) getChildren().get(1);
			// add before inner box			
			if (circle != null)
				box.getChildren().add(0, circle);
		}
	}
}
</pre>

+ flexible (f.e. properties 4 centerX, centerY and radius could be made CSS stylable)
- more complex

###FAQ

#####How gets the rectangle drawn for a CheckBox?
.. a StackPane with style class <code>box</code> via CSS ..

<pre>
.check-box > .box 
{
    -fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;
    -fx-background-insets: 0 0 -1 0, 0, 1, 2;
    -fx-background-radius: 3px, 3px, 2px, 1px;
    -fx-padding: 0.333333em 0.666667em 0.333333em 0.666667em; /* 4 8 4 8 */
    -fx-text-fill: -fx-text-base-color;
    -fx-alignment: CENTER;
    -fx-content-display: LEFT;
}
</pre>

####What does the CheckBoxSkin cover?
* drawn via CSS
* CSS style classes of CheckBox
 * check-box .. the checkbox control itself
 * box .. outer StackPane (4 background ???)
 * mark .. inner StackPane (4 what???)
* CSS Pseudo Classes
  * determinate .. super checkbox with all children are selected
  * indeterminate .. super checkbox with just a few children selected
  * selected .. selection state of the checkbox
  * further pseudo classes get inherited from Button
  
<img href="checkbox_in_determinate.png" />
  
* see also <a href="modena_extract.css">this css extract</a>

Just watch the following code:

<pre>
<code>
public class CheckBoxSkin extends LabeledSkinBase<CheckBox, ButtonBehavior<CheckBox>> {

    private final StackPane box = new StackPane();
    private StackPane innerbox;

    public CheckBoxSkin(CheckBox checkbox) {
        super(checkbox, new ButtonBehavior<CheckBox>(checkbox));

        box.getStyleClass().setAll("box");
        innerbox = new StackPane();
        innerbox.getStyleClass().setAll("mark");
        innerbox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        box.getChildren().add(innerbox);
        updateChildren();
    }
    ...
}
</code>
</pre>

####How gets the selection hook drawn?
* via CSS -fx-shape using style class mark:
<pre>
<code>
.check-box > .box > .mark {
    -fx-background-color: null;
    -fx-padding: 0.416667em 0.416667em 0.5em 0.5em; /* 5 5 6 6 */
    /* this SVG path draws the hook "/"
    -fx-shape: "M-0.25,6.083c0.843-0.758,4.583,4.833,5.75,4.833S14.5-1.5,15.917-0.917c1.292,0.532-8.75,17.083-10.5,17.083C3,16.167-1.083,6.833-0.25,6.083z";
}
</code>
</pre>

* on selection the pseudo class selected becomes active and mark gets visible:

<pre>
.check-box:selected > .box > .mark {
    -fx-background-color: -fx-mark-highlight-color, -fx-mark-color;
    -fx-background-insets: 1 0 -1 0, 0;
}
</pre>

<pre>
 /* The small thin light "shadow" for mark-like objects. Typically used in
  * conjunction with -fx-mark-color with an insets of 1 0 -1 0. */
 -fx-mark-highlight-color: ladder(
     -fx-color,
     derive(-fx-color,80%) 60%,
     white 70%
 );
</pre>


