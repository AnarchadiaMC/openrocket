package net.sf.openrocket.preset.loader;

import java.util.Collections;
import java.util.Map;

import net.sf.openrocket.material.Material;
import net.sf.openrocket.preset.TypedKey;
import net.sf.openrocket.preset.TypedPropertyMap;

public class SurfaceMaterialColumnParser extends BaseColumnParser {

	private Map<String,Material> materialMap = Collections.<String,Material>emptyMap();

	private final TypedKey<Material> param;
	
	public SurfaceMaterialColumnParser(Map<String,Material> materialMap, String columnName, TypedKey<Material> param) {
		super(columnName);
		this.param = param;
		this.materialMap = materialMap;
	}
	

	@Override
	protected void doParse(String columnData, String[] data, TypedPropertyMap props) {

		if ( columnData == null || "".equals(columnData.trim())) {
			return;
		}
		
		Material.Surface myMaterial;

		Material m = materialMap.get(columnData);
		
		if ( m == null || m.getType() != Material.Type.SURFACE ) {
			myMaterial = new Material.Surface(columnData, 0.0, true);
		} else {
			myMaterial =(Material.Surface) m;
		}
		
		props.put(param, myMaterial);

	}

}
