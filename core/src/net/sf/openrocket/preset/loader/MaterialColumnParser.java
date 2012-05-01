package net.sf.openrocket.preset.loader;

import java.util.Collections;
import java.util.Map;

import net.sf.openrocket.material.Material;
import net.sf.openrocket.preset.ComponentPreset;
import net.sf.openrocket.preset.TypedKey;
import net.sf.openrocket.preset.TypedPropertyMap;

public class MaterialColumnParser extends BaseColumnParser {

	private Map<String,Material> materialMap = Collections.<String,Material>emptyMap();

	private final TypedKey<Material> param;
	
	public MaterialColumnParser(Map<String,Material> materialMap, String columnName, TypedKey<Material> param) {
		super(columnName);
		this.param = param;
		this.materialMap = materialMap;
	}
	
	public MaterialColumnParser(Map<String,Material> materialMap) {
		this(materialMap, "Material", ComponentPreset.MATERIAL);
	}
	

	@Override
	protected void doParse(String columnData, String[] data, TypedPropertyMap props) {

		if ( columnData == null || "".equals(columnData.trim())) {
			return;
		}
		
		Material m = materialMap.get(columnData);
		if ( m == null ) {
			m = new Material.Bulk(columnData, 0.0, true);
		}
		props.put(param, m);
		
	}

}
