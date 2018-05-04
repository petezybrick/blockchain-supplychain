package com.petezybrick.bcsc.service.test.orc;

import java.util.ArrayList;
import java.util.List;

public class PersonOrcDao {
	
	public static List<List<Object>> createRowsCols( List<PersonOrcVo> vos ) throws Exception {
		List<List<Object>> personRowsCols = new ArrayList<List<Object>>();
		for( PersonOrcVo personOrcVo : vos ) {
			personRowsCols.add( personOrcVo.toObjectList());
		}
		return personRowsCols;
	}

}
