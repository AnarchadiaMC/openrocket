package info.openrocket.swing.gui.rocketfigure;

import info.openrocket.core.rocketcomponent.RocketComponent;
import info.openrocket.core.rocketcomponent.ShockCord;
import info.openrocket.core.util.Coordinate;
import info.openrocket.core.util.Transformation;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class ShockCordShapes extends RocketComponentShapes {
	@Override
	public Class<? extends RocketComponent> getShapeClass() {
		return ShockCord.class;
	}

	@Override
	public RocketComponentShapes[] getShapesSide(final RocketComponent component, final Transformation transformation) {
		final ShockCord massObj = (ShockCord)component;
		
		double length = massObj.getLength();
		double radius = massObj.getRadius();
		double arc = Math.min(length, 2*radius) * 0.7;
		final double radialDistance = massObj.getRadialPosition();
		final double radialAngleRadians = massObj.getRadialDirection();

		final Coordinate localPosition = new Coordinate(0,
				radialDistance * Math.cos(radialAngleRadians),
				radialDistance * Math.sin(radialAngleRadians));
		final Coordinate renderPosition = transformation.transform(localPosition);
		Shape[] s = new Shape[1];
		s[0] = new RoundRectangle2D.Double(renderPosition.x,(renderPosition.y-radius),
					length,2*radius,arc,arc);
		
		return RocketComponentShapes.toArray(addSymbol(s), component);
	}


	@Override
	public RocketComponentShapes[] getShapesBack(final RocketComponent component, final Transformation transformation) {
		final ShockCord massObj = (ShockCord)component;
		
		double or = massObj.getRadius();
		final double radialDistance = massObj.getRadialPosition();
		final double radialAngleRadians = massObj.getRadialDirection();

		final Coordinate localPosition = new Coordinate(0,
				radialDistance * Math.cos(radialAngleRadians),
				radialDistance * Math.sin(radialAngleRadians));
		final Coordinate renderPosition = transformation.transform(localPosition);
		
		Shape[] s = new Shape[1];
		
		s[0] = new Ellipse2D.Double((renderPosition.z-or),(renderPosition.y-or),2*or,2*or);
		
//		Coordinate[] start = transformation.transform(tube.toAbsolute(instanceOffset));
//
//		Shape[] s = new Shape[start.length];
//		for (int i=0; i < start.length; i++) {
//			s[i] = new Ellipse2D.Double((start[i].z-or),(start[i].y-or),2*or,2*or);
//		}
		return RocketComponentShapes.toArray( s, component);
	}
	
	private static Shape[] addSymbol(Shape[] baseShape){
		int offset=baseShape.length;
		Shape[] newShape = new Shape[baseShape.length+1];
		System.arraycopy(baseShape, 0, newShape, 0, baseShape.length);
			
		Rectangle2D bounds = baseShape[0].getBounds2D();

		Double left=bounds.getX()+bounds.getWidth()/4;
		Double cordWidth=bounds.getWidth()/2;
		Double top=bounds.getCenterY();
		Double flutterHeight=bounds.getHeight()/4;
		Double flutterWidth=cordWidth/4;
		
		Path2D.Double streamer= new Path2D.Double();
		streamer.moveTo(left, bounds.getCenterY()); 

		for(int i=0; i<4; i++){
			streamer.curveTo(left+(4*i+1)*flutterWidth/4, top+flutterHeight, left+(4*i+1)*flutterWidth/4, top+flutterHeight, left+(4*i+2)*flutterWidth/4, top);
			streamer.curveTo(left+(4*i+3)*flutterWidth/4, top-flutterHeight, left+(4*i+3)*flutterWidth/4, top-flutterHeight, left+(4*i+4)*flutterWidth/4, top);
		}
		
		newShape[offset]=streamer;
		return newShape;
	}
}
